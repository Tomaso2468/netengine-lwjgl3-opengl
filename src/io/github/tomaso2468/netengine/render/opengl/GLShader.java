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
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;
import org.joml.Vector4i;
import org.lwjgl.system.MemoryStack;

import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.ShaderVariableException;

public class GLShader implements Shader {
	final int program;
	final GL20Renderer renderer;
	
	public GLShader(int program, GL20Renderer renderer) {
		this.program = program;
		this.renderer = renderer;
	}
	
	private Map<String, Integer> locations = new HashMap<>();
	
	private Map<String, Float> values1f = new HashMap<>();
	private Map<String, Vector2f> values2f = new HashMap<>();
	private Map<String, Vector3f> values3f = new HashMap<>();
	private Map<String, Vector4f> values4f = new HashMap<>();
	
	private Map<String, Integer> values1i = new HashMap<>();
	private Map<String, Vector2i> values2i = new HashMap<>();
	private Map<String, Vector3i> values3i = new HashMap<>();
	private Map<String, Vector4i> values4i = new HashMap<>();
	
	public int getLocation(String name) {
		if (locations.containsKey(name)) {
			return locations.get(name);
		}
		
		int location = glGetUniformLocation(program, name);
		if (location == -1) {
			throw new ShaderVariableException(name);
		}
		
		locations.put(name, location);
		
		return location;
	}

	@Override
	public void setUniform1f(String name, float x) {
		if (values1f.containsKey(name) && values1f.get(name) == x) {
			return;
		}
		values1f.put(name, x);
		glUniform1f(getLocation(name), x);
	}

	@Override
	public void setUniform2f(String name, float x, float y) {
		if (values2f.containsKey(name) && values2f.get(name).equals(new Vector2f(x, y))) {
			return;
		}
		values2f.put(name, new Vector2f(x, y));
		glUniform2f(getLocation(name), x, y);
	}

	@Override
	public void setUniform3f(String name, float x, float y, float z) {
		if (values3f.containsKey(name) && values3f.get(name).equals(new Vector3f(x, y, z))) {
			return;
		}
		values3f.put(name, new Vector3f(x, y, z));
		glUniform3f(getLocation(name), x, y, z);
	}

	@Override
	public void setUniform4f(String name, float x, float y, float z, float w) {
		if (values4f.containsKey(name) && values4f.get(name).equals(new Vector4f(x, y, z, w))) {
			return;
		}
		values4f.put(name, new Vector4f(x, y, z, w));
		glUniform4f(getLocation(name), x, y, z, w);
	}

	@Override
	public void setUniform1i(String name, int x) {
		if (values1i.containsKey(name) && values1i.get(name) == x) {
			return;
		}
		values1i.put(name, x);
		glUniform1i(getLocation(name), x);
	}

	@Override
	public void setUniform2i(String name, int x, int y) {
		if (values2i.containsKey(name) && values2i.get(name).equals(new Vector2i(x, y))) {
			return;
		}
		values2i.put(name, new Vector2i(x, y));
		glUniform2i(getLocation(name), x, y);
	}

	@Override
	public void setUniform3i(String name, int x, int y, int z) {
		if (values3i.containsKey(name) && values3i.get(name).equals(new Vector3i(x, y, z))) {
			return;
		}
		values3i.put(name, new Vector3i(x, y, z));
		glUniform3i(getLocation(name), x, y, z);
	}

	@Override
	public void setUniform4i(String name, int x, int y, int z, int w) {
		if (values4i.containsKey(name) && values4i.get(name).equals(new Vector4i(x, y, z, w))) {
			return;
		}
		values4i.put(name, new Vector4i(x, y,z , w));
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
