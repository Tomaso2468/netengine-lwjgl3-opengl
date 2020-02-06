package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import io.github.tomaso2468.netengine.render.ArrayIndexedVertexObject;
import io.github.tomaso2468.netengine.render.Renderer;

public class IndexedVBO extends ArrayIndexedVertexObject {
	final int ebo;
	final int vbo;
	
	IndexedVBO(float[] vertices, int[] indices, GL15Renderer renderer) {
		super(vertices, indices);
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		
		ebo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
	}
	
	public int getVBO() {
		return vbo;
	}
	
	@Override
	public void configureVO(int location) {
		glVertexAttribPointer(location, 3, GL_FLOAT, false, 3 * 4, 0);
		glEnableVertexAttribArray(location);
	}
	
	@Override
	public void draw(Renderer renderer) {
		glDrawElements(GL_TRIANGLES, getVertices().length, GL_UNSIGNED_INT, 0);
	}
	
	@Override
	public void bind() {
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
	}
	
	@Override
	public void unbind() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		glDeleteBuffers(vbo);
		glDeleteBuffers(ebo);
	}
}
