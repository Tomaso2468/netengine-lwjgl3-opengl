package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

public class GL14Renderer extends GL13Renderer {

	public GL14Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setupGLWindowHints() {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 1);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 4);
	}
	
	@Override
	public int getOpenGLVersionMax() {
		return 14;
	}

}
