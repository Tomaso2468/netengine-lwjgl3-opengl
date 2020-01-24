package io.github.tomaso2468.netengine.render.opengl;

public class GL33Renderer extends GL32Renderer {

	public GL33Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getShaderFileVersion() {
		return "330";
	}
	
	@Override
	public String getOpenGLVersion() {
		return "3.3";
	}

}
