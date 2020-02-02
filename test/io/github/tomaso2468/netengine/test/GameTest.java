package io.github.tomaso2468.netengine.test;

import java.io.IOException;

import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Color;
import io.github.tomaso2468.netengine.EngineException;
import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.log.Log;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.Texture;
import io.github.tomaso2468.netengine.scene3d.BasicObject3D;
import io.github.tomaso2468.netengine.scene3d.Material;
import io.github.tomaso2468.netengine.scene3d.phong.PhongDirectionalLight;
import io.github.tomaso2468.netengine.scene3d.phong.PhongMaterial;
import io.github.tomaso2468.netengine.scene3d.phong.PhongScene;
import io.github.tomaso2468.netengine.scene3d.phong.PhongSpotLight;

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

	float vertices2[] = {
			-30, -1, -30,  0, 0,  0, 1, 0, 1,
			30, -1, -30,   1, 0,  0, 1, 0, 1,
			-30, -1, 30,  0, 1,  0, 1, 0, 1,
			30, -1, 30,   1, 1,  0, 1, 0, 1,
	};
	int indices2[] = {  // note that we start from 0!
		    0, 1, 3,
		    0, 2, 3,
		};  
	
	float vertices[] = {
			-0.5f, -0.5f, -0.5f,  0, 0, 0.0f,  0.0f, -1.0f, 0,
		     0.5f, -0.5f, -0.5f,  1, 0, 0.0f,  0.0f, -1.0f, 0, 
		     0.5f,  0.5f, -0.5f,  1, 1, 0.0f,  0.0f, -1.0f, 0,
		     0.5f,  0.5f, -0.5f,  1, 1, 0.0f,  0.0f, -1.0f, 0,
		    -0.5f,  0.5f, -0.5f,  0, 1, 0.0f,  0.0f, -1.0f, 0,
		    -0.5f, -0.5f, -0.5f,  0, 0, 0.0f,  0.0f, -1.0f, 0,

		    -0.5f, -0.5f,  0.5f,  0, 0, 0.0f,  0.0f, 1.0f, 0,
		     0.5f, -0.5f,  0.5f,  1, 0, 0.0f,  0.0f, 1.0f, 0,
		     0.5f,  0.5f,  0.5f,  1, 1, 0.0f,  0.0f, 1.0f, 0,
		     0.5f,  0.5f,  0.5f,  1, 1, 0.0f,  0.0f, 1.0f, 0,
		    -0.5f,  0.5f,  0.5f,  0, 1, 0.0f,  0.0f, 1.0f, 0,
		    -0.5f, -0.5f,  0.5f,  0, 0, 0.0f, 0.0f, 1.0f, 0,

		    -0.5f,  0.5f,  0.5f, 0, 0, -1.0f,  0.0f,  0.0f, 0,
		    -0.5f,  0.5f, -0.5f, 1, 0, -1.0f,  0.0f,  0.0f, 0,
		    -0.5f, -0.5f, -0.5f, 1, 1, -1.0f,  0.0f,  0.0f, 0,
		    -0.5f, -0.5f, -0.5f, 1, 1, -1.0f,  0.0f,  0.0f, 0,
		    -0.5f, -0.5f,  0.5f, 0, 1, -1.0f,  0.0f,  0.0f, 0,
		    -0.5f,  0.5f,  0.5f, 0, 0, -1.0f,  0.0f,  0.0f, 0,

		     0.5f,  0.5f,  0.5f, 0, 0, 1.0f,  0.0f,  0.0f, 0,
		     0.5f,  0.5f, -0.5f, 1, 0, 1.0f,  0.0f,  0.0f, 0,
		     0.5f, -0.5f, -0.5f, 1, 1, 1.0f,  0.0f,  0.0f, 0,
		     0.5f, -0.5f, -0.5f, 1, 1, 1.0f,  0.0f,  0.0f, 0,
		     0.5f, -0.5f,  0.5f, 0, 1, 1.0f,  0.0f,  0.0f, 0,
		     0.5f,  0.5f,  0.5f, 0, 0, 1.0f,  0.0f,  0.0f, 0,

		    -0.5f, -0.5f, -0.5f, 0, 0, 0.0f, -1.0f,  0.0f, 0,
		     0.5f, -0.5f, -0.5f, 1, 0, 0.0f, -1.0f,  0.0f, 0,
		     0.5f, -0.5f,  0.5f, 1, 1, 0.0f, -1.0f,  0.0f, 0,
		     0.5f, -0.5f,  0.5f, 1, 1, 0.0f, -1.0f,  0.0f, 0,
		    -0.5f, -0.5f,  0.5f, 0, 1, 0.0f, -1.0f,  0.0f, 0,
		    -0.5f, -0.5f, -0.5f, 0, 0, 0.0f, -1.0f,  0.0f, 0,

		    -0.5f,  0.5f, -0.5f, 0, 0, 0.0f,  1.0f,  0.0f, 0,
		     0.5f,  0.5f, -0.5f, 1, 0, 0.0f,  1.0f,  0.0f, 0,
		     0.5f,  0.5f,  0.5f, 1, 1, 0.0f,  1.0f,  0.0f, 0,
		     0.5f,  0.5f,  0.5f, 1, 1, 0.0f,  1.0f,  0.0f, 0,
		    -0.5f,  0.5f,  0.5f, 0, 1, 0.0f,  1.0f,  0.0f, 0,
		    -0.5f,  0.5f, -0.5f, 0, 0,  0.0f,  1.0f,  0.0f, 0,
		};
	int indices[] = {  // note that we start from 0!
	    0, 1, 2,
	    3, 4, 5,
	    
	    6, 7, 8,
	    9, 10, 11,
	    
	    12, 13, 14,
	    15, 16, 17,
	    
	    18, 19, 20,
	    21, 22, 23,
	    
	    24, 25, 26,
	    27, 28, 29,
	    
	    30, 31, 32, 
	    33, 34, 35,
	};  
	
	@Override
	protected void initGame() {
		super.initGame();
	}
	
	private PhongScene scene;
	private Shader shader;

	@Override
	protected void renderInit(Renderer renderer) {
		Texture texture, diffuse, specular, ambient, shiny, texture2;
		try {
			Log.debug("Compiling shader");
			shader = renderer.createShader(GameTest.class.getResource("/vertex.vs"), GameTest.class.getResource("/fragment.fs"));
			
			Log.debug("Texture");
			texture = renderer.loadTexture(GameTest.class.getResourceAsStream("/texture.png"), "png");
			texture2 = renderer.loadTexture(GameTest.class.getResourceAsStream("/texture2.png"), "png");
			diffuse = renderer.loadTexture(GameTest.class.getResourceAsStream("/diffuse.png"), "png");
			ambient = renderer.loadTexture(GameTest.class.getResourceAsStream("/ambient.png"), "png");
			specular = renderer.loadTexture(GameTest.class.getResourceAsStream("/specular.png"), "png");
			shiny = renderer.loadTexture(GameTest.class.getResourceAsStream("/shiny.png"), "png");
		} catch (IOException e) {
			throw new EngineException(e);
		}
		
		scene = new PhongScene();
		
		scene.add(new PhongSpotLight(new Vector3f(0, 50, 0), new Vector3f(0, -1, 0), Color.white, 0.05f, 0.1f));
		scene.add(new PhongDirectionalLight(new Vector3f(-0.5f, -0.5f, 0), Color.white));
		
		Material m = new PhongMaterial(shader, texture, ambient, diffuse, specular, shiny);
		Material m2 = new PhongMaterial(shader, texture2, ambient, diffuse, specular, shiny);
		
		BasicObject3D object = new BasicObject3D(renderer, vertices, indices, m, true);
		
		BasicObject3D object2 = new BasicObject3D(renderer, vertices2, indices2, m2, false);
		
		scene.add(object);
		scene.add(object2);
		
		scene.getCamera().setPosition(new Vector3f(-3, 0, 0));
		
		scene.init(this, renderer);
		
		renderer.setDepthTest(true);
		renderer.setCaptureMouse(true);
	}
	
	@Override
	protected void render(Renderer renderer) {
		scene.update(this, renderer.getInput(), 1 / 60f);
		
		scene.draw(this, renderer);
	}
}
