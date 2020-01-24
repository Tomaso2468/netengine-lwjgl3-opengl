package io.github.tomaso2468.netengine.render.opengl;

public class GL32Renderer extends GL31Renderer {

	public GL32Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getShaderFileVersion() {
		return "150";
	}
	
	@Override
	public String getOpenGLVersion() {
		return "3.2";
	}

}
