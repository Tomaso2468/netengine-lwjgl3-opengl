package io.github.tomaso2468.netengine.test;

import java.io.IOException;

import org.joml.Vector3f;

import io.github.tomaso2468.netengine.EngineException;
import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.log.Log;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.Texture;
import io.github.tomaso2468.netengine.scene3d.BasicObject3D;
import io.github.tomaso2468.netengine.scene3d.Material;
import io.github.tomaso2468.netengine.scene3d.Scene3D;
import io.github.tomaso2468.netengine.scene3d.TextureMaterial;

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
	     0.5f,  0.5f, 0.0f, 	1f,  1f, 0,  // top right
	     0.5f, -0.5f, 0.0f, 	1f,  0f, 0,  // bottom right
	    -0.5f, -0.5f, 0.0f, 	0f,  0f, 0,  // bottom left
	    -0.5f,  0.5f, 0.0f,		0f,  1f, 0,   // top left 
	    0, 0, 0, 0.5f, 0.5f, 1, // Center
	};
	int indices[] = {  // note that we start from 0!
	    0, 4, 1,
	    0, 4, 3,
	    1, 4, 2,
	    3, 4, 2,
	};  
	
	@Override
	protected void initGame() {
		super.initGame();
	}
	
	private Scene3D scene;
	private Shader shader;
	private Texture texture;
	private Texture texture2;

	@Override
	protected void renderInit(Renderer renderer) {
		try {
			Log.debug("Compiling shader");
			shader = renderer.createShader(GameTest.class.getResource("/vertex.vs"), GameTest.class.getResource("/fragment.fs"));
			
			shader.startUse();
			shader.setUniform1i("texture1", 0);
			shader.setUniform1i("texture2", 1);
			shader.endUse();
			
			Log.debug("Texture");
			texture = renderer.loadTexture(GameTest.class.getResourceAsStream("/texture.png"), "png");
			texture2 = renderer.loadTexture(GameTest.class.getResourceAsStream("/texture2.png"), "png");
		} catch (IOException e) {
			throw new EngineException(e);
		}
		
		scene = new Scene3D();
		
		Material m = new TextureMaterial(shader, new Texture[] {texture, texture2});
		
		BasicObject3D object = new BasicObject3D(renderer, vertices, indices, m);
		object.setRotation(new Vector3f(0, 2, 0));
		
		scene.add(object);
		
		scene.getCamera().setPosition(new Vector3f(-1, 0, 0));
		
		renderer.setDepthTest(true);
		renderer.setCaptureMouse(true);
	}
	
	@Override
	protected void render(Renderer renderer) {
		scene.update(this, renderer.getInput(), 1 / 60f);
		
		scene.draw(this, renderer);
	}
}
