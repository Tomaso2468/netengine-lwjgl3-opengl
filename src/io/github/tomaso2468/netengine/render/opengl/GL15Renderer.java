package io.github.tomaso2468.netengine.render.opengl;

import io.github.tomaso2468.netengine.render.VertexObject;

public class GL15Renderer extends GL14Renderer {
	
	public GL15Renderer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public VertexObject createStaticVO(float[] vertices) {
		return new VBO(vertices, this);
	}
	
	@Override
	public String getOpenGLVersion() {
		return "1.5";
	}
}
