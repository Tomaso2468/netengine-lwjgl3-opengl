package io.github.tomaso2468.netengine.render.opengl;

import io.github.tomaso2468.netengine.render.Framebuffer;
import io.github.tomaso2468.netengine.render.RenderException;
import io.github.tomaso2468.netengine.render.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

public class FBO implements Framebuffer, Texture {
	private final int width;
	private final int height;
	final int fbo;
	final int texture;
	final int depth;
	
	public FBO(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		
		fbo = glGenFramebuffers();
		
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		
		// Create and bind texture
		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);
		
		// Create and bind depth
		depth = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, depth);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT32F, width, height, 0, GL_DEPTH, GL_FLOAT, 0);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depth, 0);
		
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			throw new RenderException("Framebuffer is not complete.");
		}
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void bind() {
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
	}

	@Override
	public void unbind() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	@Override
	public int getWidthPixels() {
		return width;
	}

	@Override
	public int getHeightPixels() {
		return height;
	}

	@Override
	public void bind(int unit) {
		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(GL_TEXTURE_2D, texture);
	}

	@Override
	public void unbind(int unit) {
		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

}
