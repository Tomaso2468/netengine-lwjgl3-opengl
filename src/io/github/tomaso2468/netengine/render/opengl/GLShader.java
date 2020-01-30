package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.glUniformMatrix3x2fv;
import static org.lwjgl.opengl.GL21.glUniformMatrix4x3fv;
import static org.lwjgl.opengl.GL30.glUniform1ui;
import static org.lwjgl.opengl.GL30.glUniform2ui;
import static org.lwjgl.opengl.GL30.glUniform3ui;
import static org.lwjgl.opengl.GL30.glUniform4ui;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix3x2f;
import org.joml.Matrix4f;
import org.joml.Matrix4x3f;
import org.lwjgl.system.MemoryStack;

import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.ShaderVariableException;

public class GLShader implements Shader {
	int program;
	GL20Renderer renderer;
	
	public GLShader(int program, GL20Renderer renderer) {
		this.program = program;
		this.renderer = renderer;
	}
	
	private Map<String, Integer> locations = new HashMap<>();
	
	public int getLocation(String name) {
		if (locations.containsKey(name)) {
			return locations.get(name);
		}
		
		int location = glGetUniformLocation(program, name);
		if (location == -1) {
			throw new ShaderVariableException(name);
		}
		
		System.out.println(location);
		
		locations.put(name, location);
		
		return location;
	}

	@Override
	public void setUniform1f(String name, float x) {
		glUniform1f(getLocation(name), x);
	}

	@Override
	public void setUniform2f(String name, float x, float y) {
		glUniform2f(getLocation(name), x, y);
	}

	@Override
	public void setUniform3f(String name, float x, float y, float z) {
		glUniform3f(getLocation(name), x, y, z);
	}

	@Override
	public void setUniform4f(String name, float x, float y, float z, float w) {
		glUniform4f(getLocation(name), x, y, z, w);
	}

	@Override
	public void setUniform1i(String name, int x) {
		glUniform1i(getLocation(name), x);
	}

	@Override
	public void setUniform2i(String name, int x, int y) {
		glUniform2i(getLocation(name), x, y);
	}

	@Override
	public void setUniform3i(String name, int x, int y, int z) {
		glUniform3i(getLocation(name), x, y, z);
	}

	@Override
	public void setUniform4i(String name, int x, int y, int z, int w) {
		glUniform4i(getLocation(name), x, y, z, w);
	}

	@Override
	public void setUniform1ui(String name, int x) {
		if (renderer.getOpenGLVersionInt() >= 30) {
			glUniform1ui(getLocation(name), x);
		} else {
			renderer.throwUnsupported("Unsigned Int Uniforms");
		}
	}

	@Override
	public void setUniform2ui(String name, int x, int y) {
		if (renderer.getOpenGLVersionInt() >= 30) {
			glUniform2ui(getLocation(name), x, y);
		} else {
			renderer.throwUnsupported("Unsigned Int Uniforms");
		}
	}

	@Override
	public void setUniform3ui(String name, int x, int y, int z) {
		if (renderer.getOpenGLVersionInt() >= 30) {
			glUniform3ui(getLocation(name), x, y, z);
		} else {
			renderer.throwUnsupported("Unsigned Int Uniforms");
		}
	}

	@Override
	public void setUniform4ui(String name, int x, int y, int z, int w) {
		if (renderer.getOpenGLVersionInt() >= 30) {
			glUniform4ui(getLocation(name), x, y, z, w);
		} else {
			renderer.throwUnsupported("Unsigned Int Uniforms");
		}
	}

	@Override
	public void setUniformMatrix2(String name, Matrix2f m) {
		try (MemoryStack stack = stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(2 * 2);
			m.get(buffer);
			glUniformMatrix2fv(getLocation(name), false, buffer);
		}
	}

	@Override
	public void setUniformMatrix3(String name, Matrix3f m) {
		try (MemoryStack stack = stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(3 * 3);
			m.get(buffer);
			glUniformMatrix3fv(getLocation(name), false, buffer);
		}
	}

	@Override
	public void setUniformMatrix4(String name, Matrix4f m) {
		try (MemoryStack stack = stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(4 * 4);
			m.get(buffer);
			glUniformMatrix4fv(getLocation(name), false, buffer);
		}
	}

	@Override
	public void setUniformMatrix3x2(String name, Matrix3x2f m) {
		if (renderer.getOpenGLVersionInt() < 21) {
			renderer.throwUnsupported("Non-Square Matrix");
		}
		try (MemoryStack stack = stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(3 * 2);
			m.get(buffer);
			glUniformMatrix3x2fv(getLocation(name), false, buffer);
		}
	}

	@Override
	public void setUniformMatrix4x3(String name, Matrix4x3f m) {
		if (renderer.getOpenGLVersionInt() < 21) {
			renderer.throwUnsupported("Non-Square Matrix");
		}
		try (MemoryStack stack = stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(4 * 3);
			m.get(buffer);
			glUniformMatrix4x3fv(getLocation(name), false, buffer);
		}
	}

	@Override
	public void startUse() {
		glUseProgram(program);
	}

	@Override
	public void endUse() {
		glUseProgram(0);
	}

}
