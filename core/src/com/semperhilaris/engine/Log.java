package com.semperhilaris.engine;

import com.badlogic.gdx.Gdx;

/**
 * Global Log.
 */
public class Log {

	public static final String INFO = "INFO";
	public static final String ERROR = "ERROR";

	public static void msg(String tag, String message, String level) {
		if (GameConfig.getBoolean("DEBUG")) {
			Gdx.app.log("[" + level + "] " + tag, message);
		}
	}

	public static void msg(String tag, String message) {
		msg(tag, message, INFO);
	}

}
