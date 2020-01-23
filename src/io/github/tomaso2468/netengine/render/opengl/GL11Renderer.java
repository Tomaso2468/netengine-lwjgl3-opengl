package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.opengl.GL11.*;

import java.nio.IntBuffer;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.MemoryStack;

import io.github.tomaso2468.netengine.Color;
import io.github.tomaso2468.netengine.log.Log;
import io.github.tomaso2468.netengine.render.Renderer;

public class GL11Renderer implements Renderer {
	private long window;
	private boolean vsync = true;

	public GL11Renderer() {
		// An empty constructor must exist.
	}

	@Override
	public void preInitWindow() {
		Log.info("Initialising GLFW");
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		glfwSetErrorCallback(GLFWErrorCallback.create(new GLFWErrorCallback() {
			private Map<Integer, String> ERROR_CODES = APIUtil
					.apiClassTokens((field, value) -> 0x10000 < value && value < 0x20000, null, GLFW.class);

			@Override
			public void invoke(int error, long description) {
				String msg = getDescription(description);

				String output = "";

				output += String.format("[LWJGL] %s error\n", ERROR_CODES.get(error));
				output += "\tDescription : " + msg;
				output += "\tStacktrace  :";

				StackTraceElement[] stack = Thread.currentThread().getStackTrace();
				for (int i = 4; i < stack.length; i++) {
					output += "\t\t";
					output += stack[i].toString();
				}

				Log.error(output);
			}
		}));

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit()) {
			throw new GLFWException("Unable to initialize GLFW");
		}
	}

	@Override
	public void setVSync(boolean vsync) {
		this.vsync = vsync;

		if (window != NULL) {
			glfwSwapInterval(vsync ? 1 : 0);
		}
	}

	@Override
	public void createWindow(int width, int height, String title, boolean fullscreen, boolean resizable) {
		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE); // the window will be resizable.

		// Create the window
		window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);
		if (window == NULL) {
			throw new GLFWException("Failed to create the GLFW window");
		}

		// Setup a key callback. It will be called every time a key is pressed, repeated
		// or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);

		glfwSwapInterval(vsync ? 1 : 0);
	}

	@Override
	public void showWindow() {
		// Make the window visible
		glfwShowWindow(window);
	}
	
	@Override
	public void init() {
		GL.createCapabilities();
	}

	@Override
	public boolean windowClosePressed() {
		return glfwWindowShouldClose(window);
	}

	@Override
	public void startFrame() {
		
	}

	@Override
	public void clearScreen(Color color) {
		glClearColor(color.r, color.g, color.b, color.a);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public void sync() {
		glfwSwapBuffers(window);

	}

	@Override
	public void update() {
		glfwPollEvents();
	}

	@Override
	public void destroy() {
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

}
