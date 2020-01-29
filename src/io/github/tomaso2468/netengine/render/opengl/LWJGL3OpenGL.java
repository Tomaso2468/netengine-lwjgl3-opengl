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
		game.registerRenderer("opengl-3.3", GL33Renderer.class);
		game.registerRenderer("opengl-4.0", GL40Renderer.class);
		game.registerRenderer("opengl-4.1", GL41Renderer.class);
		game.registerRenderer("opengl-4.2", GL42Renderer.class);
		game.registerRenderer("opengl-4.3", GL43Renderer.class);
		game.registerRenderer("opengl-4.4", GL44Renderer.class);
		game.registerRenderer("opengl-4.5", GL45Renderer.class);
		game.registerRenderer("opengl-4.6", GL46Renderer.class);
		
		game.registerRendererBackwardsCompatible("opengl-1", GL11Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-1.1", GL11Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-1.2", GL12Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-1.3", GL13Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-1.4", GL14Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-1.5", GL15Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-2", GL20Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-2.0", GL20Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-2.1", GL21Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-3", GL30Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-3.0", GL30Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-3.1", GL31Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-3.2", GL32Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-3.3", GL33Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-4", GL40Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-4.0", GL40Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-4.1", GL41Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-4.2", GL42Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-4.3", GL43Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-4.4", GL44Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-4.5", GL45Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-4.6", GL46Renderer.class);
		game.registerRendererBackwardsCompatible("opengl-latest", GL46Renderer.class);
		game.registerRendererBackwardsCompatible("opengl", GLRenderer.class);
	}

	@Override
	public void init(Game game) {
		
	}

	@Override
	public void postInit(Game game) {
		
	}

}
