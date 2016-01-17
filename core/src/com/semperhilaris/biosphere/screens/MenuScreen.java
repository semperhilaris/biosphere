package com.semperhilaris.biosphere.screens;

import com.semperhilaris.biosphere.systems.BackgroundMusicSystem;
import com.semperhilaris.biosphere.systems.MenuScreenSystem;
import com.semperhilaris.engine.AbstractGame;
import com.semperhilaris.engine.AbstractScreen;

/**
 * Menu screen
 */
public class MenuScreen extends AbstractScreen {

	public MenuScreen(AbstractGame game) {
		super(game);
		enableSystems(
				MenuScreenSystem.class,
				BackgroundMusicSystem.class);
	}

}
