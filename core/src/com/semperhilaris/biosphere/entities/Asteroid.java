package com.semperhilaris.biosphere.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.semperhilaris.biosphere.components.*;
import com.semperhilaris.engine.GameConfig;
import com.semperhilaris.engine.components.*;

/**
 * Asteroid Entity
 */
public class Asteroid extends Entity {

	public Asteroid(float x, float y, float size) {
		super();

		Spine spine = new Spine("asteroid", "destroyable");
		spine.animationState.setAnimation(0, "idle", true);
		spine.animationState.setTimeScale(0.2f);

		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		fixtureDef.shape = new CircleShape();
		fixtureDef.shape.setRadius(80 * GameConfig.getFloat("PIXELS_TO_METERS"));
		fixtureDef.filter.groupIndex = 1;
		Physics2D physics2D = new Physics2D(bodyDef, fixtureDef);

		add(new AsteroidFlag());
		add(new Position(x, y));
		add(new Scale(1f * size));
		add(spine);
		add(physics2D);
		add(new Health((int)(40 + 10 * size)));
		add(new Energy((int)(10 * size)));
		//add(new PointOfInterest(600, 300, -3));
		add(new DestroyTarget(100));
	}

	public Asteroid(float x, float y) {
		this(x, y, 1f);
	}

}
