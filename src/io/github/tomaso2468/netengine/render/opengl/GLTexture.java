package io.github.tomaso2468.netengine.render.opengl;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

import io.github.tomaso2468.netengine.render.Texture;

public class GLTexture implements Texture {
	final int texture;
	final int width;
	final int height;
	
	public GLTexture(ByteBuffer image, int width, int height, int channels) {
		glActiveTexture(GL_TEXTURE0);
		texture = glGenTextures();
		this.width = width;
		this.height = height;
		
		glBindTexture(GL_TEXTURE_2D, texture);
		
		glTexImage2D(GL_TEXTURE_2D, 0, channels == 3 ? GL_RGB : GL_RGBA, width, height, 0, channels == 3 ? GL_RGB : GL_RGBA, GL_UNSIGNED_BYTE, image);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);	
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		glGenerateMipmap(GL_TEXTURE_2D);
		
		stbi_image_free(image);
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	@Override
	public int getWidthPixels() {
		return width;
	}

	@Override
	public int getHeightPixels() {
		return height;
	}

	@Override
	public void bind(int unit) {
		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(GL_TEXTURE_2D, texture);
	}

	@Override
	public void unbind(int unit) {
		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

}
