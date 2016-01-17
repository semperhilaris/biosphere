package com.semperhilaris.biosphere.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.semperhilaris.biosphere.BiosphereGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Biosphere";
		config.width = 800;
		config.height = 480;
		config.resizable = true;
		new LwjglApplication(new BiosphereGame(), config);
	}
}
