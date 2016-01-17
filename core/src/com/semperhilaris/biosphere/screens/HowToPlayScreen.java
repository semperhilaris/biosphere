package com.semperhilaris.biosphere.screens;

import com.semperhilaris.biosphere.systems.BackgroundMusicSystem;
import com.semperhilaris.biosphere.systems.HowToPlayScreenSystem;
import com.semperhilaris.engine.AbstractGame;
import com.semperhilaris.engine.AbstractScreen;

/**
 *
 */
public class HowToPlayScreen extends AbstractScreen {

	public HowToPlayScreen(AbstractGame game) {
		super(game);

		enableSystems(
				HowToPlayScreenSystem.class,
				BackgroundMusicSystem.class);
	}

}
