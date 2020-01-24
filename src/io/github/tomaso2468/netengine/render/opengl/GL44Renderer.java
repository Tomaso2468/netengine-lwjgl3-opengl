package io.github.tomaso2468.netengine.render.opengl;

public class GL44Renderer extends GL43Renderer {

	public GL44Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getShaderFileVersion() {
		return "440";
	}
	
	@Override
	public String getOpenGLVersion() {
		return "4.4";
	}

}
