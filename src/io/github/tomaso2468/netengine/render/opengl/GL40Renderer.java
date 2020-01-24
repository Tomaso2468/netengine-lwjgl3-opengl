package io.github.tomaso2468.netengine.render.opengl;

public class GL40Renderer extends GL33Renderer {

	public GL40Renderer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getShaderFileVersion() {
		return "400";
	}
	
	@Override
	public String getOpenGLVersion() {
		return "4.0";
	}
}
