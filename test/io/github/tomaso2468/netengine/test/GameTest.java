package io.github.tomaso2468.netengine.test;

import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.render.Renderer;

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
			-0.5f, -0.5f, 0.0f,
			0.5f, -0.5f, 0.0f,
			0.0f, 0.5f, 0.0f
	};

	@Override
	protected void render(Renderer renderer) {
		// TODO Auto-generated method stub
		super.render(renderer);
	}
}
