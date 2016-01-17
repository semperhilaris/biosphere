package com.semperhilaris.engine.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.physics.box2d.*;
import com.semperhilaris.engine.Events;
import com.semperhilaris.engine.events.physics.BeginContactEvent;
import com.semperhilaris.engine.events.physics.EndContactEvent;
import com.semperhilaris.engine.events.physics.PostSolveEvent;
import com.semperhilaris.engine.events.physics.PreSolveEvent;

/**
 * System for catching physics collisions and sending them to the event bus.
 */
public class ContactListenerSystem extends EntitySystem {

	public ContactListenerSystem(World world) {

		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				if (checkProcessing()) {
					Events.post(new BeginContactEvent(contact));
				}
			}

			@Override
			public void endContact(Contact contact) {
				if (checkProcessing()) {
					Events.post(new EndContactEvent(contact));
				}
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				if (checkProcessing()) {
					Events.post(new PreSolveEvent(contact, oldManifold));
				}
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				if (checkProcessing()) {
					Events.post(new PostSolveEvent(contact, impulse));
				}
			}
		});
	}
}
