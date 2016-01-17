package com.semperhilaris.biosphere.screens;

import com.semperhilaris.biosphere.systems.LoadingScreenSystem;
import com.semperhilaris.engine.AbstractGame;
import com.semperhilaris.engine.AbstractScreen;

/**
 * Loading screen
 */
public class LoadingScreen extends AbstractScreen {

	public LoadingScreen(AbstractGame game) {
		super(game);
		enableSystem(LoadingScreenSystem.class);
	}

}
