package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.*;

import io.github.tomaso2468.netengine.render.Framebuffer;

public class DepthFBO implements Framebuffer {
	private final int width;
	private final int height;
	final int fbo;
	final int depth;
	final GL30Renderer renderer;
	
	public DepthFBO(int width, int height, GL30Renderer renderer) {
		super();
		this.width = width;
		this.height = height;
		this.renderer = renderer;
		
		fbo = glGenFramebuffers();
		
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		
		/// Create and bind depth
		depth = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depth);
		
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);

		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depth);  
		
		glDrawBuffer(GL_NONE);
		glReadBuffer(GL_NONE);
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
		glViewport(0, 0, width, height);
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		glClear(GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public void unbind() {
		glViewport(0, 0, renderer.getWindowWidth(), renderer.getWindowHeight());
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
		glBindTexture(GL_TEXTURE_2D, depth);
	}

	@Override
	public void unbind(int unit) {
		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	@Override
	public void dispose() {
		glDeleteFramebuffers(fbo);
	}
}
