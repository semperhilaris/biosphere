package com.semperhilaris.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Global Config.
 */
public class GameConfig {

	public static final JsonValue json = new JsonReader().parse(Gdx.files.internal("config.json"));

	public static String getString(String name) { return json.getString(name); }

	public static int getInt(String name) {
		return json.getInt(name);
	}

	public static float getFloat(String name) {
		return json.getFloat(name);
	}

	public static boolean getBoolean(String name) {
		return json.getBoolean(name);
	}

}
