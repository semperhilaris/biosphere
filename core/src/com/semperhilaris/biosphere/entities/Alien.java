package com.semperhilaris.biosphere.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.semperhilaris.biosphere.components.AlienFlag;
import com.semperhilaris.biosphere.components.Threat;
import com.semperhilaris.engine.components.*;

/**
 * Alien Entity
 */
public class Alien extends Entity {

	public Alien(String name, float x, float y) {
		super();

		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.linearDamping = 3f;
		bodyDef.angularDamping = 2f;
		bodyDef.fixedRotation = true;
		fixtureDef.shape = new CircleShape();
		fixtureDef.shape.setRadius(1f);
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 1f;
		fixtureDef.filter.groupIndex = -1;

		Physics2D physics2D = new Physics2D(bodyDef, fixtureDef);
		physics2D.maxLinearAcceleration = 50f;
		physics2D.maxLinearSpeed = 50f;
		physics2D.boundingRadius = 2f;
		physics2D.independentFacing = true;

		add(new AlienFlag());
		add(new Position(x, y));
		add(new Layer());
		add(new Scale());
		add(new Rotation());
		add(physics2D);
		add(new Spine(name));
		add(new MovementSpeed(10f));
		add(new Threat());
	}
}
