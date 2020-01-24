package io.github.tomaso2468.netengine.render.opengl;

public class GL46Renderer extends GL45Renderer {

	public GL46Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getShaderFileVersion() {
		return "460";
	}
	
	@Override
	public String getOpenGLVersion() {
		return "4.6";
	}

}
