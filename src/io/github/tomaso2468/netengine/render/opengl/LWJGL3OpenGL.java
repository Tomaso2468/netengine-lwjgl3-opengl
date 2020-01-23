package io.github.tomaso2468.netengine.render.opengl;

import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.plugin.Plugin;

public class LWJGL3OpenGL implements Plugin {
	public LWJGL3OpenGL() {
	}

	@Override
	public String getID() {
		return "renderer-lwjgl3-opengl";
	}

	@Override
	public String getName() {
		return "LWJGL3 OpenGL Renderer";
	}

	@Override
	public String getVersion() {
		return "0.0.0";
	}

	@Override
	public void preInit(Game game) {
		game.registerRenderer("opengl-1.1", GL11Renderer.class);
		
		game.registerRendererBackwardsCompatible("opengl", GL11Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-1", GL11Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-1.1", GL11Renderer.class);
	}

	@Override
	public void init(Game game) {
		
	}

	@Override
	public void postInit(Game game) {
		
	}

}
