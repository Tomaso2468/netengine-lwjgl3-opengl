package io.github.tomaso2468.netengine.render.opengl;

public class GL30Renderer extends GL21Renderer {

	public GL30Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getShaderFileVersion() {
		return "130";
	}
	
	@Override
	public String getOpenGLVersion() {
		return "3.0";
	}

}
