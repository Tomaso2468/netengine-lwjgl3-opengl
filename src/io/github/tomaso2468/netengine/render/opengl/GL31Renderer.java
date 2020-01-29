package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

public class GL31Renderer extends GL30Renderer {

	public GL31Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getGLSLVersionMax() {
		return 140;
	}
	
	@Override
	protected void setupGLWindowHints() {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
	}
	
	@Override
	public int getOpenGLVersionMax() {
		return 31;
	}

}
