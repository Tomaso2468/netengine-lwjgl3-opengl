package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

import io.github.tomaso2468.netengine.render.FrameBuffer;
import io.github.tomaso2468.netengine.render.GBuffer;
import io.github.tomaso2468.netengine.render.ShadowBuffer;

public class GL32Renderer extends GL31Renderer {

	public GL32Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getGLSLVersionMax() {
		return 150;
	}
	
	@Override
	protected void setupGLWindowHints() {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
	}
	
	@Override
	public int getOpenGLVersionMax() {
		return 32;
	}
	
	@Override
	public FrameBuffer createFrameBuffer(int width, int height) {
		return new FBO(width, height, this);
	}
	
	@Override
	public ShadowBuffer createShadowBuffer(int width, int height) {
		return new FBO(width, height, this);
	}
	
	@Override
	public GBuffer createGBuffer(int width, int height, int bufferCount) {
		return new GBufferFBO(width, height, this, bufferCount);
	}

}
