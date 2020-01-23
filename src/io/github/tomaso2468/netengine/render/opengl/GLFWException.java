package io.github.tomaso2468.netengine.render.opengl;

import io.github.tomaso2468.netengine.render.RenderException;

public class GLFWException extends RenderException {
	private static final long serialVersionUID = -7567738491517874264L;

	public GLFWException() {
	}

	public GLFWException(String message) {
		super(message);
	}

	public GLFWException(Throwable cause) {
		super(cause);
	}

	public GLFWException(String message, Throwable cause) {
		super(message, cause);
	}

	public GLFWException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
