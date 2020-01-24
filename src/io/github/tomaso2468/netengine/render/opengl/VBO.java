package io.github.tomaso2468.netengine.render.opengl;

import io.github.tomaso2468.netengine.render.ArrayVertexObject;

import static org.lwjgl.opengl.GL15.*;

public class VBO extends ArrayVertexObject {
	int vbo;
	
	VBO(float[] vertices, GL15Renderer renderer) {
		super(vertices);
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
	}
	
	public int getVBO() {
		return vbo;
	}
}
