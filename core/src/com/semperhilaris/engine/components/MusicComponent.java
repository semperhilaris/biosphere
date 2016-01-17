package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Music;

/**
 *
 */
public class MusicComponent implements Component {

	public Music file;

	public MusicComponent(Music file) {
		this.file = file;
	}

}
