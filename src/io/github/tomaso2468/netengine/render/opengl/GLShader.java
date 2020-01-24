package io.github.tomaso2468.netengine.render.opengl;

import io.github.tomaso2468.netengine.render.Shader;

public class GLShader implements Shader {
	int program;
	
	public GLShader(int program) {
		this.program = program;
	}

}
