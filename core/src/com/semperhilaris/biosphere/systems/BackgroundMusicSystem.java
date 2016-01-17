package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.semperhilaris.engine.Assets;

/**
 *
 */
public class BackgroundMusicSystem extends EntitySystem {

	private Music backgroundMusic;

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		//TODO AssetManager + Audio is broken on OSX El Capitan: https://github.com/libgdx/libgdx/issues/3572
		/*
		if (Assets.assetManager.getProgress() == 1 && backgroundMusic == null) {
			backgroundMusic = Assets.getMusic("Dystopic-Mayhem.mp3");
			backgroundMusic.setLooping(true);
		}
		*/
		if (backgroundMusic == null) {
			backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/Dystopic-Mayhem.mp3"));
		}
	}

	@Override
	public boolean checkProcessing() {
		boolean processing = super.checkProcessing();
		if (!processing && backgroundMusic != null && backgroundMusic.isPlaying()) {
			backgroundMusic.pause();
		} else if (processing && backgroundMusic != null && !backgroundMusic.isPlaying()) {
			backgroundMusic.play();
		}
		return processing;
	}

}
