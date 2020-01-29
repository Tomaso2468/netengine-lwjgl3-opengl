package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.Toolkit;
import java.nio.IntBuffer;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.MemoryStack;

import io.github.tomaso2468.netengine.log.Log;
import io.github.tomaso2468.netengine.render.OpenGLRenderer;

public abstract class GLFWRenderer implements OpenGLRenderer {
	private long window;
	private boolean vsync = true;
	private int width;
	private int height;
	private boolean resizable;
	private boolean fullscreen;
	private String title;

	public GLFWRenderer() {
		// An empty constructor must exist.
	}

	@Override
	public void preInitWindow() {
		Log.debug("Initialising GLFW");
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
	
	protected void setupGLWindowHints() {
		
	}

	@Override
	public void createWindow(int width, int height, String title, boolean fullscreen, boolean resizable) {
		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE); // the window will be resizable.
		setupGLWindowHints();
		
		this.resizable = resizable;
		this.fullscreen = fullscreen;
		this.title = title;

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

		centerWindow();
		
		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			this.width = pWidth.get(0);
			this.height = pHeight.get(0);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);

		glfwSwapInterval(vsync ? 1 : 0);
		
		configureWindowSize(width, height);
	}
	
	protected abstract void configureWindowSize(int width, int height);

	private void centerWindow() {
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
	}
	
	private void fetchWindowSize() {
		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			this.width = pWidth.get(0);
			this.height = pHeight.get(0);
		} // the stack frame is popped automatically
	}

	@Override
	public void showWindow() {
		// Make the window visible
		glfwShowWindow(window);
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public boolean windowClosePressed() {
		return glfwWindowShouldClose(window);
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

	@Override
	public boolean isVSync() {
		return vsync;
	}

	@Override
	public void setFullscreen(boolean fullscreen) {
		setDisplay(width, height, fullscreen);
	}

	@Override
	public boolean isFullscreen() {
		return fullscreen;
	}

	@Override
	public void setResizable(boolean resizable) {
		this.resizable = resizable;
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
	}

	@Override
	public boolean isResizable() {
		return resizable;
	}

	@Override
	public void setWindowSize(int width, int height) {
		setDisplay(width, height, fullscreen);
	}

	@Override
	public int getWindowWidth() {
		return width;
	}

	@Override
	public int getWindowHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return getWindowWidth();
	}

	@Override
	public int getHeight() {
		return getWindowHeight();
	}
	
	@Override
	public int getScreenWidth() {
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}
	
	@Override
	public int getScreenHeight() {
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}

	@Override
	public void setDisplay(int width, int height, boolean fullscreen) {
		if (fullscreen == this.fullscreen) {
			glfwSetWindowSize(window, width, height);
			configureWindowSize(width, height);
		} else {
			long oldWindow = window;
			
			createWindow(width, height, title, fullscreen, fullscreen);
			
			glfwFreeCallbacks(oldWindow);
			glfwDestroyWindow(oldWindow);
		}
		this.fullscreen = fullscreen;
		fetchWindowSize();
	}
}
