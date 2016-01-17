package com.semperhilaris.biosphere.entities;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.semperhilaris.engine.components.Physics2D;
import com.semperhilaris.engine.components.Position;

/**
 * WorldBound Entity
 */
public class WorldBounds extends Entity {

	public WorldBounds(float x, float y, float width, float height) {
		super();

		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		PolygonShape bodyShape = new PolygonShape();
		bodyShape.setAsBox(width, height);
		fixtureDef.shape = bodyShape;
		fixtureDef.filter.groupIndex = 1;

		add(new Position(x, y));
		add(new Physics2D(bodyDef, fixtureDef));
	}

}
