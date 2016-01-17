package com.semperhilaris.engine.events.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.semperhilaris.engine.events.Event;

/**
 *
 */
public class EndContactEvent extends Event {

	public Contact contact;

	public EndContactEvent(Contact contact) {
		this.contact = contact;
	}

}
