package com.semperhilaris.biosphere.events;

import com.semperhilaris.engine.events.Event;

/**
 *
 */
public class AlienSwitchEvent extends Event {

	public String name;

	public AlienSwitchEvent(String name) {
		this.name = name;
	}

}
