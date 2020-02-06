package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;

import io.github.tomaso2468.netengine.render.Framebuffer;
import io.github.tomaso2468.netengine.render.RenderException;

public class FBO implements Framebuffer {
	private final int width;
	private final int height;
	final int fbo;
	final int texture;
	final int depth;
	final GLFWRenderer renderer;
	
	public FBO(int width, int height, GLFWRenderer renderer) {
		super();
		this.width = width;
		this.height = height;
		this.renderer = renderer;
		
		fbo = glGenFramebuffers();
		
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		
		// Create and bind texture
		if (renderer.msaa) {
			texture = glGenTextures();
			glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, texture);
			
			glTexImage2DMultisample(GL_TEXTURE_2D_MULTISAMPLE, renderer.msaaSamples, GL_RGB, width, height, true);

			glTexParameteri(GL_TEXTURE_2D_MULTISAMPLE, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D_MULTISAMPLE, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			
			glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D_MULTISAMPLE, texture, 0);
		} else {
			texture = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, texture);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			
			glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);
		}
		
		// Create and bind depth
		depth = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depth);
		
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);

		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depth);  
		
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			throw new RenderException("Framebuffer is not complete: " + getFrameBufferError(glCheckFramebufferStatus(GL_FRAMEBUFFER)));
		}
	}
	
	private String getFrameBufferError(int error) {
		switch (error) {
		case GL_FRAMEBUFFER_UNDEFINED:
			return "GL_FRAMEBUFFER_UNDEFINED";
		case GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
			return "GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT";
		case GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
			return "GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT";
		case GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
			return "GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER";
		case GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
			return "GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER";
		case GL_FRAMEBUFFER_UNSUPPORTED:
			return "GL_FRAMEBUFFER_UNSUPPORTED";
		case GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE:
			return "GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE";
		case GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS:
			return "GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS";
		default:
			return "Unknown (" + Integer.toHexString(error) + ")";
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
		if (renderer.msaa) {
			glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, texture);
		} else {
			glBindTexture(GL_TEXTURE_2D, texture);
		}
		
	}

	@Override
	public void unbind(int unit) {
		glActiveTexture(GL_TEXTURE0 + unit);
		if (renderer.msaa) {
			glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, 0);
		} else {
			glBindTexture(GL_TEXTURE_2D, 0);
		}
	}
	
	@Override
	public void dispose() {
		glDeleteFramebuffers(fbo);
	}

}
