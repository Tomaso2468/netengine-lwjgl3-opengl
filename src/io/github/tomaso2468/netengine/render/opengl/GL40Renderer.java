package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

public class GL40Renderer extends GL33Renderer {

	public GL40Renderer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getGLSLVersionMax() {
		return 400;
	}
	
	@Override
	protected void setupGLWindowHints() {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
	}
	
	@Override
	public int getOpenGLVersionMax() {
		return 40;
	}
}
