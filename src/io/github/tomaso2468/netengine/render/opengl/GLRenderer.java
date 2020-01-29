package io.github.tomaso2468.netengine.render.opengl;

public class GLRenderer extends GL46Renderer {
	public GLRenderer() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void setupGLWindowHints() {
		// Revert to defaults to allow any version.
	}
	
	@Override
	public int getGLSLVersionMax() {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public int getOpenGLVersionMax() {
		return Integer.MAX_VALUE;
	}

}
