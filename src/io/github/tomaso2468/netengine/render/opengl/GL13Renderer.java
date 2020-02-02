package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

public class GL13Renderer extends GL12Renderer {
	
	@Override
	public void init() {
		super.init();
		if (msaa) {
			glEnable(GL_MULTISAMPLE);
		}
	}
	
	@Override
	protected void setupGLWindowHints() {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 1);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	}
	
	@Override
	public int getOpenGLVersionMax() {
		return 13;
	}

}
