package com.semperhilaris.biosphere.entities;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.semperhilaris.engine.GameConfig;
import com.semperhilaris.engine.components.Physics2D;
import com.semperhilaris.engine.components.Position;
import com.semperhilaris.engine.components.Scale;
import com.semperhilaris.engine.components.Spine;

/**
 * Planet Entity
 */
public class Planet extends Entity {

	public Planet(float x, float y) {
		super();

		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		fixtureDef.shape = new CircleShape();
		fixtureDef.shape.setRadius(550 * GameConfig.getFloat("PIXELS_TO_METERS"));
		fixtureDef.filter.groupIndex = 1;
		Physics2D physics2D = new Physics2D(bodyDef, fixtureDef);

		add(new Position(x, y));
		add(new Scale(1f));
		add(physics2D);
		add(new Spine("planet"));
	}

}
