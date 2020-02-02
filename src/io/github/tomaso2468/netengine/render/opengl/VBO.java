package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import io.github.tomaso2468.netengine.render.ArrayVertexObject;
import io.github.tomaso2468.netengine.render.Renderer;

public class VBO extends ArrayVertexObject {
	final int vbo;
	
	VBO(float[] vertices, GL15Renderer renderer) {
		super(vertices);
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
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
		glDrawArrays(GL_TRIANGLES, 0, getVertices().length);
	}
	
	@Override
	public void bind() {
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
	}
	
	@Override
	public void unbind() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
}
