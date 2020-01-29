package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

import io.github.tomaso2468.netengine.Color;
import io.github.tomaso2468.netengine.log.Log;
import io.github.tomaso2468.netengine.render.ArrayIndexedVertexObject;
import io.github.tomaso2468.netengine.render.ArrayTexturedVertexObject;
import io.github.tomaso2468.netengine.render.ArrayVertexObject;
import io.github.tomaso2468.netengine.render.IndexedVertexObject;
import io.github.tomaso2468.netengine.render.RenderState;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.TexturedVertexObject;
import io.github.tomaso2468.netengine.render.VertexObject;

public class GL11Renderer extends GLFWRenderer {
	public GL11Renderer() {
		// An empty constructor must exist.
	}
	
	private boolean capInit = false;
	
	@Override
	protected void setupGLWindowHints() {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 1);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
	}

	@Override
	public void init() {
		super.init();
		Log.debug("Initialising OpenGL");
		GL.createCapabilities();
		capInit = true;
		configureWindowSize(getWidth(), getHeight());
	}
	
	@Override
	protected void configureWindowSize(int width, int height) {
		if (capInit) {
			glViewport(0, 0, getWidth(), getHeight());
		}
	}

	@Override
	public void startFrame() {
		
	}

	@Override
	public void clearScreen(Color color) {
		glClearColor(color.r, color.g, color.b, color.a);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public VertexObject createStaticVO(float[] vertices) {
		return new ArrayVertexObject(vertices);
	}
	
	@Override
	public IndexedVertexObject createStaticVO(float[] vertices, int[] indices) {
		return new ArrayIndexedVertexObject(vertices, indices);
	}
	
	@Override
	public TexturedVertexObject createStaticVOTextured(float[] data, int[] indices) {
		return new ArrayTexturedVertexObject(data, indices);
	}
	
	public String getOpenGLVersion() {
		return glGetString(GL_VERSION);
	}
	
	protected void throwUnsupported(String feature) {
		throw new UnsupportedOperationException("The " + feature + " feature is not supported by OpenGL " + getOpenGLVersion());
	}
	
	@Override
	public int getGLSLVersion() {
		throwUnsupported("Shader");
		return 0;
	}
	
	@Override
	public int getOpenGLVersionInt() {
		String version = getOpenGLVersion();
		version = version.substring(0, version.indexOf(' ')).substring(0, version.indexOf(".", version.indexOf(".") + 1));
		
		int v = (int) (Double.parseDouble(version) * 10);
		
		if (v > getOpenGLVersionMax()) {
			v = getOpenGLVersionMax();
		}
		
		return v;
	}
	
	@Override
	public int getOpenGLVersionMax() {
		return 11;
	}

	@Override
	public Shader createShader(String[] vertexShaders, String[] fragmentShaders) {
		throwUnsupported("Shader");
		return null;
	}
	
	@Override
	public void setShader(Shader shader) {
		throwUnsupported("Shader");
	}

	public int getGLSLVersionMax() {
		throwUnsupported("Shader");
		return 0;
	}
	
	@Override
	public boolean isES() {
		return false;
	}

	@Override
	public RenderState createRenderState() {
		throwUnsupported("Render State");
		return null;
	}
	
	@Override
	public void drawTriangles(float[] vertices) {
		glBegin(GL_TRIANGLES);
		for (int i = 0; i < vertices.length; i+=3) {
			glVertex3f(vertices[i + 0], vertices[i + 1], vertices[i + 2]);
		}
		glEnd();
	}
	
	@Override
	public void drawTriangles(float[] vertices, int[] indices) {
		glBegin(GL_TRIANGLES);
		for (int i = 0; i < indices.length; i+=3) {
			glVertex3f(vertices[indices[i] + 0], vertices[indices[i] + 1], vertices[indices[i] + 2]);
		}
		glEnd();
	}
	
	@Override
	public void drawTrianglesTextured(float[] data, int[] indices) {
		glBegin(GL_TRIANGLES);
		for (int i = 0; i < indices.length; i += 5) {
			glVertex3f(data[indices[i] + 0], data[indices[i] + 1], data[indices[i] + 2]);
			glTexCoord2f(data[indices[i] + 3], data[indices[i] + 4]);
		}
		glEnd();
	}

}
