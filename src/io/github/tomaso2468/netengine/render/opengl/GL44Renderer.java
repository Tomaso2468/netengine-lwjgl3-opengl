package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

public class GL44Renderer extends GL43Renderer {

	public GL44Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getGLSLVersionMax() {
		return 440;
	}
	
	@Override
	protected void setupGLWindowHints() {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 4);
	}
	
	@Override
	public int getOpenGLVersionMax() {
		return 44;
	}

}
