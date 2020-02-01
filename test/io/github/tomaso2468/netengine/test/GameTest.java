package io.github.tomaso2468.netengine.test;

import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.EngineException;
import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.camera.SimpleCamera3D;
import io.github.tomaso2468.netengine.log.Log;
import io.github.tomaso2468.netengine.render.MultiTextureVertexObject;
import io.github.tomaso2468.netengine.render.RenderState;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.Texture;

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
	
	private RenderState state;
	private MultiTextureVertexObject object;
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
		
		Log.debug("State");
		state = renderer.createRenderState();
		state.enterState();
		
		object = renderer.createStaticVOMultiTexture(vertices, indices);
		object.configureVO(0);
		object.configureVOTexture(1);
		object.configureVOSelect(2);
		
		object.unbind();
		state.leaveState();
		
		camera = new SimpleCamera3D();
		camera.setPosition(new Vector3f(0, 0, 3));
		
		renderer.setDepthTest(true);
		renderer.setCaptureMouse(true);
	}
	
//	private Vector3f cameraPos = new Vector3f(0, 0, 3);
//	private Vector3f cameraFront = new Vector3f(0, 0, -1);
//	private Vector3f cameraUp = new Vector3f(0, 1, 0);
//	private float yaw = (float) Math.toRadians(-80);
//	private float pitch = 0;
	
	private SimpleCamera3D camera;
	
	@Override
	protected void render(Renderer renderer) {
//		if (renderer.getInput().isKeyDown(GLFW_KEY_LEFT)) {
//			yaw -= 0.05f;
//		}
//		if (renderer.getInput().isKeyDown(GLFW_KEY_RIGHT)) {
//			yaw += 0.05f;
//		}
//		if (renderer.getInput().isKeyDown(GLFW_KEY_UP)) {
//			pitch += 0.05f;
//		}
//		if (renderer.getInput().isKeyDown(GLFW_KEY_DOWN)) {
//			pitch -= 0.05f;
//		}
//		cameraFront = new Vector3f(
//				(float) (Math.cos(yaw) * Math.cos(pitch)),
//				(float) (Math.sin(pitch)),
//				(float) (Math.sin(yaw) * Math.cos(pitch)));
//		if (renderer.getInput().isKeyDown(GLFW_KEY_W)) {
//			cameraPos = cameraPos.add(new Vector3f(cameraFront).mul(0.05f));
//		}
//		if (renderer.getInput().isKeyDown(GLFW_KEY_S)) {
//			cameraPos = cameraPos.add(new Vector3f(cameraFront).mul(-0.05f));
//		}
//		if (renderer.getInput().isKeyDown(GLFW_KEY_A)) {
//			cameraPos = cameraPos.add(new Vector3f(cameraFront).cross(cameraUp).mul(-0.05f));
//		}
//		if (renderer.getInput().isKeyDown(GLFW_KEY_D)) {
//			cameraPos = cameraPos.add(new Vector3f(cameraFront).cross(cameraUp).mul(0.05f));
//		}
//		if (renderer.getInput().isKeyDown(GLFW_KEY_SPACE)) {
//			cameraPos = cameraPos.add(new Vector3f(0, 0.05f, 0));
//		}
//		if (renderer.getInput().isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
//			cameraPos = cameraPos.add(new Vector3f(0, -0.05f, 0));
//		}
		
		camera.update(renderer.getInput(), 1f / 60);
		
		texture.bind(0);
		texture2.bind(1);
		
		shader.startUse();
		state.enterState();
		
		shader.setUniformMatrix4("view", camera.getView(renderer));
		shader.setUniformMatrix4("projection", camera.getProjection(renderer));
//		shader.setUniformMatrix4("view", new Matrix4f().lookAt(cameraPos, new Vector3f(cameraPos).add(cameraFront), cameraUp));
//		shader.setUniformMatrix4("projection", new Matrix4f().setPerspective((float) Math.toRadians(45), 800 / 600, 0.1f, 100));
		
		shader.setUniformMatrix4("model", new Matrix4f().rotate(-1, new Vector3f(1, 0, 0)));
		object.draw(renderer);
		
//		shader.setUniformMatrix4("model", new Matrix4f().translate(0, 0, -1).rotate(-1, new Vector3f(1, 0, 0)));
//		object.draw(renderer);
		
		state.leaveState();
	}
}
