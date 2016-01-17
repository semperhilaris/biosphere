package com.semperhilaris.engine.events;

/**
 * Event that kicks off a Screen change.
 */
public class ScreenChangeEvent extends Event {

	public String screenName;

	public ScreenChangeEvent(String screenName) {
		this.screenName = screenName;
	}

}
