package com.semperhilaris.biosphere.screens;

import com.semperhilaris.biosphere.systems.BackgroundMusicSystem;
import com.semperhilaris.biosphere.systems.ScoreScreenSystem;
import com.semperhilaris.engine.AbstractGame;
import com.semperhilaris.engine.AbstractScreen;

/**
 * Score Screen
 */
public class ScoreScreen extends AbstractScreen {

	public ScoreScreen(AbstractGame game) {
		super(game);

		enableSystems(
				ScoreScreenSystem.class,
				BackgroundMusicSystem.class);
	}

}
