package io.github.tomaso2468.netengine.render.opengl;

public class GL31Renderer extends GL30Renderer {

	public GL31Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getShaderFileVersion() {
		return "140";
	}
	
	@Override
	public String getOpenGLVersion() {
		return "3.1";
	}

}
