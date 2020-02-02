package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import io.github.tomaso2468.netengine.render.ArrayMultiTextureVertexObject;
import io.github.tomaso2468.netengine.render.Renderer;

public class MultiTextureVBO extends ArrayMultiTextureVertexObject {
	final int ebo;
	final int vbo;
	
	MultiTextureVBO(float[] data, int[] indices, GL15Renderer renderer) {
		super(data, indices);
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		
		ebo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
	}
	
	public int getVBO() {
		return vbo;
	}
	
	@Override
	public void configureVO(int location) {
		glVertexAttribPointer(location, 3, GL_FLOAT, false, 9 * 4, 0);
		glEnableVertexAttribArray(location);
	}
	
	@Override
	public void configureVOTexture(int location) {
		glVertexAttribPointer(location, 2, GL_FLOAT, false, 9 * 4, 3 * 4);
		glEnableVertexAttribArray(location);
	}
	
	@Override
	public void configureVONormal(int location) {
		glVertexAttribPointer(location, 3, GL_FLOAT, false, 9 * 4, 5 * 4);
		glEnableVertexAttribArray(location);
	}
	
	@Override
	public void configureVOSelect(int location) {
		glVertexAttribPointer(location, 1, GL_FLOAT, false, 9 * 4, 8 * 4);
		glEnableVertexAttribArray(location);
	}
	
	@Override
	public void draw(Renderer renderer) {
		glDrawElements(GL_TRIANGLES, getData().length, GL_UNSIGNED_INT, 0);
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
