package io.github.tomaso2468.netengine.render.opengl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.stb.STBImage.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import io.github.tomaso2468.netengine.render.RenderState;
import io.github.tomaso2468.netengine.render.Texture;
import io.github.tomaso2468.netengine.render.TextureLoadException;

public class GL30Renderer extends GL21Renderer {

	public GL30Renderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getGLSLVersionMax() {
		return 130;
	}
	
	@Override
	protected void setupGLWindowHints() {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
	}
	
	@Override
	public int getOpenGLVersionMax() {
		return 30;
	}
	
	@Override
	public RenderState createRenderState() {
		return new VAO();
	}
	
	@Override
	public void init() {
		super.init();
		
		stbi_set_flip_vertically_on_load(true);
	}
	
	protected ByteBuffer readToBuffer(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		while (in.available() > 0) {
			out.write(in.read());
		}
		
		in.close();
		out.close();
		
		byte[] arr = out.toByteArray();
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(arr.length);
		
		buffer.put(arr);
		buffer.flip();
		
		return buffer;
	}
	
	@Override
	public Texture loadTexture(InputStream in, String format) throws IOException {
		ByteBuffer image;
        int width, height;
        int channels;
        
        ByteBuffer imageBuffer = readToBuffer(in);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            /* Prepare image buffers */
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            /* Load image */
            image = stbi_load_from_memory(imageBuffer, w, h, comp, 0);
            if (image == null) {
                throw new TextureLoadException("Failed to load a texture file: " + stbi_failure_reason());
            }

            /* Get width and height of image */
            width = w.get();
            height = h.get();
            channels = comp.get();
        }
        
        return new GLTexture(image, width, height, channels);
	}

}
