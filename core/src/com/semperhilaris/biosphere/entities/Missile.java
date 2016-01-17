package com.semperhilaris.biosphere.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.Face;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.semperhilaris.biosphere.components.MissileFlag;
import com.semperhilaris.biosphere.components.MissileState;
import com.semperhilaris.engine.components.*;

/**
 *
 */
public class Missile extends Entity {

	public Missile(float x, float y, float rotation, SteeringLocation launchTarget, Location seekingTarget) {
		super();

		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.linearDamping = 0.5f;
		bodyDef.angularDamping = 0.5f;
		bodyDef.fixedRotation = false;
		fixtureDef.shape = new CircleShape();
		fixtureDef.shape.setRadius(0.1f);
		fixtureDef.density = 1;
		fixtureDef.friction = 4f;
		fixtureDef.filter.maskBits = 0x0;

		Physics2D physics2D = new Physics2D(bodyDef, fixtureDef);
		physics2D.maxLinearAcceleration = 0.01f;
		physics2D.maxLinearSpeed = 1f;
		physics2D.maxAngularAcceleration = 0.01f;
		physics2D.maxAngularSpeed = 1f;
		physics2D.independentFacing = true;

		add(new MissileFlag());
		add(new MissileState(seekingTarget));
		add(new Position(x, y));
		add(new Layer(-2));
		add(new Rotation(rotation - 90));
		add(new Spine("missile"));
		add(physics2D);
		add(new Steering(new Face(physics2D, launchTarget.box2dLocation)
				.setTimeToTarget(0.1f)
		));
	}

}
