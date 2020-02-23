package io.github.tomaso2468.netengine.test;

import java.io.IOException;

import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Color;
import io.github.tomaso2468.netengine.EngineException;
import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.input.Input;
import io.github.tomaso2468.netengine.log.Log;
import io.github.tomaso2468.netengine.material.Material;
import io.github.tomaso2468.netengine.render.AntialiasingType;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.ShaderLoader;
import io.github.tomaso2468.netengine.render.Texture;
import io.github.tomaso2468.netengine.scene3d.BasicObject3D;
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
		    3, 1, 0,
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
	    2, 1, 0,
	    5, 4, 3,
	    
	    6, 7, 8,
	    9, 10, 11,
	    
	    12, 13, 14,
	    15, 16, 17,
	    
	    20, 19, 18,
	    23, 22, 21,
	    
	    24, 25, 26,
	    27, 28, 29,
	    
	    32, 31, 30, 
	    35, 34, 33,
	};  
	
	@Override
	protected void initGame() {
		super.initGame();
	}
	
	@Override
	protected void configure(Renderer renderer) {
		super.configure(renderer);
		renderer.setAntialiasing(AntialiasingType.DISABLED, 0);
	}
	
	private PhongScene scene;
	private Shader shader;

	@Override
	protected void renderInit(Renderer renderer) {
		Texture diffuse, specular;
		try {
			Log.debug("Compiling shader");
//			shader = renderer.createShader(GameTest.class.getResource("/vertex.vs"), GameTest.class.getResource("/fragment.fs"));
			shader = ShaderLoader.createDefaultDeferredShader(renderer);
			
			diffuse = renderer.loadTexture(GameTest.class.getResourceAsStream("/ambient.png"), "png");
			specular = renderer.loadTexture(GameTest.class.getResourceAsStream("/specular.png"), "png");
		} catch (IOException e) {
			throw new EngineException(e);
		}
		
		scene = new PhongScene();
		
		scene.add(new PhongSpotLight(new Vector3f(0, 20, 0), new Vector3f(0, -1, 0), Color.white.multiply(1.1f), 0.25f, 0.1f));
//		//scene.add(new PhongDirectionalLight(new Vector3f(-0.5f, -0.5f, 0), Color.white));
		
		Material m = new PhongMaterial(shader, diffuse, specular, PhongMaterial.getDefaultDepthShader(renderer));
		
		BasicObject3D object = new BasicObject3D(renderer, vertices, indices, m, false) {
			@Override
			public void update(Game game, Input input, float delta) {
				setRotation(getRotation().add(new Vector3f(delta, delta / 2, delta / 3)));
			}
		};
		object.setCull(false);
		
		BasicObject3D object2 = new BasicObject3D(renderer, vertices2, indices2, m, false);
		object2.setCull(false);
		
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
