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
		game.registerRenderer("opengl-1.2", GL12Renderer.class);
		game.registerRenderer("opengl-1.3", GL13Renderer.class);
		game.registerRenderer("opengl-1.4", GL14Renderer.class);
		game.registerRenderer("opengl-1.5", GL15Renderer.class);
		game.registerRenderer("opengl-2.0", GL20Renderer.class);
		game.registerRenderer("opengl-2.1", GL21Renderer.class);
		game.registerRenderer("opengl-3.0", GL30Renderer.class);
		game.registerRenderer("opengl-3.1", GL31Renderer.class);
		game.registerRenderer("opengl-3.2", GL32Renderer.class);
		
		Class<?> backwards = GL32Renderer.class;
		game.registerRendererBackwardsCompatible("opengl", backwards);
		game.registerRendererBackwardsCompatible("opengl-1", backwards);
		game.registerRendererBackwardsCompatible("opengl-1.1", backwards);
		game.registerRendererBackwardsCompatible("opengl-1.2", backwards);
		game.registerRendererBackwardsCompatible("opengl-1.3", backwards);
		game.registerRendererBackwardsCompatible("opengl-1.4", backwards);
		game.registerRendererBackwardsCompatible("opengl-1.5", backwards);
		game.registerRendererBackwardsCompatible("opengl-2", backwards);
		game.registerRendererBackwardsCompatible("opengl-2.0", backwards);
		game.registerRendererBackwardsCompatible("opengl-2.1", backwards);
		game.registerRendererBackwardsCompatible("opengl-3", backwards);
		game.registerRendererBackwardsCompatible("opengl-3.0", backwards);
		game.registerRendererBackwardsCompatible("opengl-3.1", backwards);
		game.registerRendererBackwardsCompatible("opengl-3.2", backwards);
		game.registerRendererBackwardsCompatible("opengl", backwards);
	}

	@Override
	public void init(Game game) {
		
	}

	@Override
	public void postInit(Game game) {
		
	}

}
