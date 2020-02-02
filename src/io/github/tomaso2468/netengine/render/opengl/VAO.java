package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import io.github.tomaso2468.netengine.render.RenderState;

public class VAO implements RenderState {
	final int vao;
	
	public VAO() {
		vao = glGenVertexArrays();
	}

	@Override
	public void enterState() {
		glBindVertexArray(vao);
	}

	@Override
	public void leaveState() {
		glBindVertexArray(0);
	}
}
