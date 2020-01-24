package io.github.tomaso2468.netengine.render.opengl;

public class GL42Renderer extends GL41Renderer {

	public GL42Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getShaderFileVersion() {
		return "420";
	}
	
	@Override
	public String getOpenGLVersion() {
		return "4.2";
	}

}
