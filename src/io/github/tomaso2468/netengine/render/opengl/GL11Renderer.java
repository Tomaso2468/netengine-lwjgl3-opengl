package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.opengl.GL;

import io.github.tomaso2468.netengine.Color;
import io.github.tomaso2468.netengine.log.Log;
import io.github.tomaso2468.netengine.render.ArrayIndexedVertexObject;
import io.github.tomaso2468.netengine.render.ArrayMultiTextureVertexObject;
import io.github.tomaso2468.netengine.render.ArrayTexturedVertexObject;
import io.github.tomaso2468.netengine.render.ArrayVertexObject;
import io.github.tomaso2468.netengine.render.BlendFactor;
import io.github.tomaso2468.netengine.render.FrameBuffer;
import io.github.tomaso2468.netengine.render.GBuffer;
import io.github.tomaso2468.netengine.render.IndexedVertexObject;
import io.github.tomaso2468.netengine.render.MultiTextureVertexObject;
import io.github.tomaso2468.netengine.render.RenderState;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.ShadowBuffer;
import io.github.tomaso2468.netengine.render.Texture;
import io.github.tomaso2468.netengine.render.TexturedVertexObject;
import io.github.tomaso2468.netengine.render.VertexObject;

public class GL11Renderer extends GLFWRenderer {
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
	
	@Override
	public MultiTextureVertexObject createStaticVOMultiTexture(float[] data, int[] indices) {
		return new ArrayMultiTextureVertexObject(data, indices);
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
	
	@Override
	public Texture loadTexture(InputStream in, String format) throws IOException {
		throw new UnsupportedOperationException("Textures require at least GL30 to correctly use");
	}
	
	@Override
	public void setDepthTest(boolean enabled) {
		if (enabled) {
			glEnable(GL_DEPTH_TEST);
		} else {
			glDisable(GL_DEPTH_TEST);
		}
	}
	
	public int convert(BlendFactor f) {
		int srcf;
		switch(f) {
		case DEST_ALPHA:
			srcf = GL_DST_ALPHA;
			break;
		case DEST_COLOR:
			srcf = GL_DST_COLOR;
			break;
		case ONE:
			srcf = GL_ONE;
			break;
		case ONE_MINUS_DEST_ALPHA:
			srcf = GL_ONE_MINUS_DST_ALPHA;
			break;
		case ONE_MINUS_DEST_COLOR:
			srcf = GL_ONE_MINUS_DST_COLOR;
			break;
		case ONE_MINUS_SRC_ALPHA:
			srcf = GL_ONE_MINUS_SRC_ALPHA;
			break;
		case ONE_MINUS_SRC_COLOR:
			srcf = GL_ONE_MINUS_SRC_COLOR;
			break;
		case SRC_ALPHA:
			srcf = GL_SRC_ALPHA;
			break;
		case SRC_COLOR:
			srcf = GL_SRC_COLOR;
			break;
		case ZERO:
			srcf = GL_ZERO;
			break;
		default:
			throw new IllegalArgumentException("Unsupported blend factor for color: " + f);
		}
		
		return srcf;
	}

	@Override
	public void setBlend(BlendFactor src, BlendFactor dest) {
		if (src == BlendFactor.DISABLE || dest == BlendFactor.DISABLE) {
			glDisable(GL_BLEND);
			return;
		} else {
			glEnable(GL_BLEND);
		}
		
		glBlendFunc(convert(src), convert(dest));
	}

	@Override
	public void setFaceCull(boolean enabled) {
		if (enabled) {
			glEnable(GL_CULL_FACE);
		} else {
			glDisable(GL_CULL_FACE);
		}
	}

	@Override
	public FrameBuffer createFrameBuffer(int width, int height) {
		throwUnsupported("FrameBuffer");
		return null;
	}
	
	@Override
	public ShadowBuffer createShadowBuffer(int width, int height) {
		throwUnsupported("ShadowBuffer");
		return null;
	}

	@Override
	public GBuffer createGBuffer(int width, int height, int bufferCount) {
		throwUnsupported("GBuffer");
		return null;
	}

	@Override
	public void drawQuadUV(float[] data) {
		glBegin(GL_QUADS);
		for (int i = 0; i < data.length; i+=5) {
			glVertex3f(data[i], data[i + 1], data[i + 2]);
			glTexCoord2f(data[i + 3], data[i + 4]);
		}
		glEnd();
	}
}
