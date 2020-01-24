package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

import io.github.tomaso2468.netengine.Color;
import io.github.tomaso2468.netengine.log.Log;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.VertexObject;

public class GL11Renderer extends GLFWRenderer {
	public GL11Renderer() {
		// An empty constructor must exist.
	}
	
	@Override
	protected void setupGLWindowHints() {
		// Defaults should be fine.
	}

	@Override
	public void init() {
		super.init();
		Log.debug("Initialising OpenGL");
		GL.createCapabilities();
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
		throwUnsupported("VertexObject");
		return null;
	}
	
	public String getOpenGLVersion() {
		return "1.1";
	}
	
	protected void throwUnsupported(String feature) {
		throw new UnsupportedOperationException("The " + feature + " feature is not supported by OpenGL " + getOpenGLVersion());
	}
	
	@Override
	public String getShaderFileVersion() {
		throwUnsupported("Shader");
		return null;
	}

	@Override
	public Shader createShader(String[] vertexShaders, String[] fragmentShaders) {
		throwUnsupported("Shader");
		return null;
	}

}
