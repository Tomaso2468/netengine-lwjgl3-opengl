package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL20.*;

import io.github.tomaso2468.netengine.render.IndexedVertexObject;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.ShaderCompileException;
import io.github.tomaso2468.netengine.render.ShaderLinkException;
import io.github.tomaso2468.netengine.render.TexturedVertexObject;
import io.github.tomaso2468.netengine.render.UnknownImplException;
import io.github.tomaso2468.netengine.render.VertexObject;

public class GL20Renderer extends GL15Renderer {

	public GL20Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setupGLWindowHints() {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
	}
	
	@Override
	public int getGLSLVersion() {
		return Integer.parseInt(glGetString(GL_SHADING_LANGUAGE_VERSION).replace(".", ""));
	}
	
	@Override
	public int getGLSLVersionMax() {
		return 120;
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
		
		return new GLShader(program, this);
	}
	
	@Override
	public void setShader(Shader shader) {
		if (shader == null) {
			glUseProgram(0);
		}
		if (!(shader instanceof GLShader)) {
			throw new UnknownImplException(shader);
		}
		glUseProgram(((GLShader) shader).program); 
	}

	@Override
	public int getOpenGLVersionMax() {
		return 20;
	}
	
	@Override
	public VertexObject createStaticVO(float[] vertices) {
		return new VBO(vertices, this);
	}
	
	@Override
	public IndexedVertexObject createStaticVO(float[] vertices, int[] indices) {
		return new IndexedVBO(vertices, indices, this);
	}
	
	@Override
	public TexturedVertexObject createStaticVOTextured(float[] data, int[] indices) {
		return new TexturedVBO(data, indices, this);
	}
}
