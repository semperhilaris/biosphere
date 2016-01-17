package com.semperhilaris.engine.events.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.semperhilaris.engine.events.Event;

/**
 *
 */
public class PreSolveEvent extends Event {

	public Contact contact;
	public Manifold oldManifold;

	public PreSolveEvent(Contact contact, Manifold oldManifold) {
		this.contact = contact;
		this.oldManifold = oldManifold;
	}

}
