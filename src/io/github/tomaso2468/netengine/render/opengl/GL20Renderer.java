package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.opengl.GL20.*;

import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.ShaderCompileException;
import io.github.tomaso2468.netengine.render.ShaderLinkException;

public class GL20Renderer extends GL15Renderer {

	public GL20Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getShaderFileVersion() {
		return "110";
	}
	
	@Override
	public String getOpenGLVersion() {
		return "2.0";
	}
	
	protected int compileShader(String source, int type) {
		int shader = glCreateShader(type);
		
		glShaderSource(shader, source);
		
		glCompileShader(shader);
		
		int success = glGetShaderi(shader, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			String log = glGetShaderInfoLog(shader, Short.MAX_VALUE);
			throw new ShaderCompileException("Failed to compile shader:\n" + log);
		}
		
		return shader;
	}
	
	protected int createShaderProgram(int[] vertexShaders, int[] fragmentShaders) {
		int program = glCreateProgram();
		
		for (int i = 0; i < vertexShaders.length; i++) {
			glAttachShader(program, vertexShaders[i]);
		}
		for (int i = 0; i < fragmentShaders.length; i++) {
			glAttachShader(program, fragmentShaders[i]);
		}
		
		glLinkProgram(program);
		
		int success = glGetProgrami(program, GL_LINK_STATUS);
		if (success == GL_FALSE) {
			String log = glGetProgramInfoLog(program, Short.MAX_VALUE);
			throw new ShaderLinkException("Failed to compile shader:\n" + log);
		}
		
		for (int i = 0; i < vertexShaders.length; i++) {
			glDeleteShader(vertexShaders[i]);
		}
		for (int i = 0; i < fragmentShaders.length; i++) {
			glDeleteShader(fragmentShaders[i]);
		}
		
		return program;
	}
	
	@Override
	public Shader createShader(String[] vertexShaders, String[] fragmentShaders) {
		int[] vertexShaders2 = new int[vertexShaders.length];
		int[] fragmentShaders2 = new int[fragmentShaders.length];
		
		for (int i = 0; i < vertexShaders.length; i++) {
			vertexShaders2[i] = compileShader(vertexShaders[i], GL_VERTEX_SHADER);
		}
		for (int i = 0; i < fragmentShaders.length; i++) {
			fragmentShaders2[i] = compileShader(fragmentShaders[i], GL_FRAGMENT_SHADER);
		}
		
		int program = createShaderProgram(vertexShaders2, fragmentShaders2);
		
		return new GLShader(program);
	}

}
