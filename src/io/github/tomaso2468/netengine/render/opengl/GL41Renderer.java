package io.github.tomaso2468.netengine.render.opengl;

public class GL41Renderer extends GL40Renderer {

	public GL41Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getShaderFileVersion() {
		return "410";
	}
	
	@Override
	public String getOpenGLVersion() {
		return "4.1";
	}

}
