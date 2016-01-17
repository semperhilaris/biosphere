package com.semperhilaris.engine.events.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.semperhilaris.engine.events.Event;

/**
 *
 */
public class BeginContactEvent extends Event {

	public Contact contact;

	public BeginContactEvent(Contact contact) {
		this.contact = contact;
	}

}
