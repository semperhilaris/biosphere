package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;

/**
 *
 */
public class SoundComponent implements Component {

	public Sound file;

	public SoundComponent(Sound file) {
		this.file = file;
	}

}
