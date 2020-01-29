package io.github.tomaso2468.netengine.test;

import java.io.IOException;

import io.github.tomaso2468.netengine.EngineException;
import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.render.RenderState;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.Texture;
import io.github.tomaso2468.netengine.render.TexturedVertexObject;

public class GameTest extends Game {

	public static void main(String[] args) {
		new GameTest().start();
	}

	@Override
	public void preInitGame() {
		setRendererID("opengl");
	}

	@Override
	public String getName() {
		return "Test Game";
	}

	float vertices[] = {
	     0.5f,  0.5f, 0.0f, 	0.5f,  0.5f,  // top right
	     0.5f, -0.5f, 0.0f, 	0.5f, -0.5f,  // bottom right
	    -0.5f, -0.5f, 0.0f, 	-0.5f, -0.5f,  // bottom left
	    -0.5f,  0.5f, 0.0f,		-0.5f,  0.5f,   // top left 
	};
	int indices[] = {  // note that we start from 0!
	    0, 1, 3,   // first triangle
	    1, 2, 3    // second triangle
	};  
	
	@Override
	protected void initGame() {
		super.initGame();
	}
	
	private RenderState state;
	private TexturedVertexObject object;
	private Shader shader;
	private Texture texture;

	@Override
	protected void renderInit(Renderer renderer) {
		try {
			shader = renderer.createShader(GameTest.class.getResource("/vertex.vs"), GameTest.class.getResource("/fragment.fs"));
		} catch (IOException e) {
			throw new EngineException(e);
		}
		
		state = renderer.createRenderState();
		state.enterState();
		
		object = renderer.createStaticVOTextured(vertices, indices);
		object.configureVO(0);
		object.configureVOTexture(1);
		
		object.unbind();
		state.leaveState();
	}
	
	@Override
	protected void render(Renderer renderer) {
		state.enterState();
		
		shader.startUse();
		object.draw(renderer);
		
		state.leaveState();
	}
}
