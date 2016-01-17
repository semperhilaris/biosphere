package com.semperhilaris.engine.events;

/**
 *
 */
public class CursorChangeEvent extends Event {

	public String alias;

	public CursorChangeEvent(String alias) {
		this.alias = alias;
	}

}
