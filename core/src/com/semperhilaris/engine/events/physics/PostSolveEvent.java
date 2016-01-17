package com.semperhilaris.engine.events.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.semperhilaris.engine.events.Event;

/**
 *
 */
public class PostSolveEvent extends Event {

	public Contact contact;
	public ContactImpulse impulse;

	public PostSolveEvent(Contact contact, ContactImpulse impulse) {
		this.contact = contact;
		this.impulse = impulse;
	}

}
