package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;

import io.github.tomaso2468.netengine.render.GBuffer;
import io.github.tomaso2468.netengine.render.RenderException;

public class GBufferFBO implements GBuffer {
	private final int width;
	private final int height;
	final int fbo;
	final int[] textures;
	final int depth;
	final GLFWRenderer renderer;
	
	private int createBuffer(int width, int height, GLFWRenderer renderer, int attach) {
		// Create and bind texture
		int texture;
		if (renderer.msaa) {
			throw new UnsupportedOperationException("MSAA is not supported in deferred rendering.");
		} else {
			texture = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, texture);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA32F, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			
			glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + attach, GL_TEXTURE_2D, texture, 0);
		}
		return texture;
	}
	
	public GBufferFBO(int width, int height, GLFWRenderer renderer, int count) {
		super();
		this.width = width;
		this.height = height;
		this.renderer = renderer;
		
		fbo = glGenFramebuffers();
		
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		
		textures = new int[count];
		int[] buffers = new int[count];
		
		for (int i = 0; i < textures.length; i++) {
			textures[i] = createBuffer(width, height, renderer, i);
			buffers[i] = GL_COLOR_ATTACHMENT0 + i;
		}
		
		// Create and bind depth
		depth = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depth);
		
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);

		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depth);  
		
		glDrawBuffers(buffers);
	
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
		glViewport(0, 0, width, height);
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

	public void bind(int unit, int texture) {
		glActiveTexture(GL_TEXTURE0 + unit);
		if (renderer.msaa) {
			glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, textures[texture]);
		} else {
			glBindTexture(GL_TEXTURE_2D, textures[texture]);
		}
	}

	public void unbind(int unit, int texture) {
		glActiveTexture(GL_TEXTURE0 + unit);
		if (renderer.msaa) {
			glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, 0);
		} else {
			glBindTexture(GL_TEXTURE_2D, 0);
		}
	}

	@Override
	public void bind(int unit) {
		for (int i = 0; i < textures.length; i++) {
			bind(unit + i, i);
		}
	}

	@Override
	public void unbind(int unit) {
		for (int i = 0; i < textures.length; i++) {
			unbind(unit + i, i);
		}
	}
	
	@Override
	public void dispose() {
		glDeleteFramebuffers(fbo);
	}

	@Override
	public void copyDepthToScreen() {
		glBindFramebuffer(GL_READ_FRAMEBUFFER, fbo);
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0); // write to default framebuffer
		glBlitFramebuffer(
		  0, 0, width, height, 0, 0, width, height, GL_DEPTH_BUFFER_BIT, GL_NEAREST
		);
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
}
