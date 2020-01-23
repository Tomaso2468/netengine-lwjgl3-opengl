package io.github.tomaso2468.netengine.test;

import io.github.tomaso2468.netengine.Game;

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
}
