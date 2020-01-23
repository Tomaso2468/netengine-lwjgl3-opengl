package io.github.tomaso2468.netengine.test;

import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.render.opengl.GL11Renderer;

public class GameTest extends Game {

	public static void main(String[] args) {
		new GameTest().start();
	}
	
	@Override
	public void preInitGame() {
		setRendererClass(GL11Renderer.class);
	}

	@Override
	public String getName() {
		return "Test Game";
	}
}
