package com.semperhilaris.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 *
 */
public class Assets {

	public static AssetManager assetManager = new AssetManager();

	/**
	 * Load the assets required to display the loading screen.
	 */
	private static void preload() {
		assetManager.load("bootstrap.atlas", TextureAtlas.class);
		assetManager.finishLoading();
	}

	/**
	 * Load all game assets asynchronously.
	 */
	public static void load() {
		preload();
		assetManager.load("sprites.atlas", TextureAtlas.class);
		FileHandle[] musicFiles = Gdx.files.internal("audio/music").list();
		for (int i = 0; i < musicFiles.length; i++) {
			if (musicFiles[i].name().endsWith(".mp3")) {
				assetManager.load("audio/music/" + musicFiles[i].name(), Music.class);
			}
		}
		FileHandle[] soundFiles = Gdx.files.internal("audio/sounds").list();
		for (int i = 0; i < soundFiles.length; i++) {
			if (soundFiles[i].name().endsWith(".mp3")) {
				assetManager.load("audio/sounds/" + soundFiles[i].name(), Sound.class);
			}
		}
	}

	public static Sound getSound(String name) {
		return assetManager.get("audio/sounds/" + name, Sound.class);
	}

	public static Music getMusic(String name) {
		return assetManager.get("audio/music/" + name, Music.class);
	}

	public static TextureAtlas getTextureAtlas(String name) {
		return assetManager.get(name + ".atlas", TextureAtlas.class);
	}

	public static TextureAtlas getTextureAtlas() {
		return getTextureAtlas("sprites");
	}

	public static TextureRegion getTextureRegion(String name, String packfile) {
		return getTextureAtlas(packfile).findRegion(name);
	}

	public static TextureRegion getTextureRegion(String name){
		return getTextureRegion(name, "sprites");
	}

	public static Texture getTexture(String name, String packfile) {
		return getTextureRegion(name, packfile).getTexture();
	}

	public static Texture getTexture(String name) {
		return getTexture(name, "sprites");
	}

	public static Drawable getDrawable(String name, String packfile) {
		return new TextureRegionDrawable(getTextureRegion(name, packfile));
	}

	public static Drawable getDrawable(String name) {
		return getDrawable(name, "sprites");
	}

	public static NinePatchDrawable getNinePatchDrawable(String name, String packfile) {
		return new NinePatchDrawable(getTextureAtlas(packfile).createPatch(name));
	}

	public static NinePatchDrawable getNinePatchDrawable(String name) {
		return getNinePatchDrawable(name, "sprites");
	}

	public static Sprite getSprite(String name, String packfile) {
		return new Sprite(getTextureRegion(name, packfile));
	}

	public static Sprite getSprite(String name) {
		return getSprite(name, "sprites");
	}

}

