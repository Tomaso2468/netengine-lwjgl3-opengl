package io.github.tomaso2468.netengine.render.opengl;

public class GL21Renderer extends GL20Renderer {

	public GL21Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getShaderFileVersion() {
		return "120";
	}
	
	@Override
	public String getOpenGLVersion() {
		return "2.1";
	}

}
