package io.github.tomaso2468.netengine.render.opengl;

public class GL45Renderer extends GL44Renderer {

	public GL45Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getShaderFileVersion() {
		return "450";
	}
	
	@Override
	public String getOpenGLVersion() {
		return "4.5";
	}

}
