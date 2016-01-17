package com.semperhilaris.engine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.semperhilaris.engine.GameConfig;
import com.semperhilaris.engine.components.Physics2D;
import com.semperhilaris.engine.components.Position;
import com.semperhilaris.engine.components.Rotation;

/**
 * System for 2D physics simulation using Box2D.
 */
public class Physics2DSystem extends IteratingSystem {

	private World physicsWorld;

	ComponentMapper<Physics2D> physics2DComponentMapper;
	ComponentMapper<Position> positionComponentMapper;
	ComponentMapper<Rotation> rotationComponentMapper;

	public Physics2DSystem(World physicsWorld) {
		super(Family.all(Physics2D.class, Position.class).get());
		this.physicsWorld = physicsWorld;

		physics2DComponentMapper = ComponentMapper.getFor(Physics2D.class);
		positionComponentMapper = ComponentMapper.getFor(Position.class);
		rotationComponentMapper = ComponentMapper.getFor(Rotation.class);
	}

	@Override
	public void update(float deltaTime) {
		physicsWorld.step(
				GameConfig.getFloat("PHYSICS_TIME_STEP"),
				GameConfig.getInt("VELOCITY_ITERATIONS"),
				GameConfig.getInt("POSITION_ITERATIONS"));
		super.update(deltaTime);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Physics2D physics2D = physics2DComponentMapper.get(entity);
		Position position = positionComponentMapper.get(entity);
		Rotation rotation = rotationComponentMapper.get(entity);
		if (physics2D.body == null && !physics2D.destroyBody) {
			physics2D.bodyDef.position.set(
					position.x * GameConfig.getFloat("PIXELS_TO_METERS"),
					position.y * GameConfig.getFloat("PIXELS_TO_METERS"));
			Body body = physicsWorld.createBody(physics2D.bodyDef);
			body.createFixture(physics2D.fixtureDef);
			if (rotation != null) {
				body.setTransform(body.getPosition(), rotation.x * MathUtils.degreesToRadians);
			}
			physics2D.body = body;
		} else if (physics2D.body != null && physics2D.destroyBody) {
			for (Fixture fixture : physics2D.body.getFixtureList()) {
				physics2D.body.destroyFixture(fixture);
			}
			physicsWorld.destroyBody(physics2D.body);
			physics2D.body = null;
		} else if (physics2D.body != null) {
			position.x = physics2D.body.getPosition().x * GameConfig.getFloat("METERS_TO_PIXELS");
			position.y = physics2D.body.getPosition().y * GameConfig.getFloat("METERS_TO_PIXELS");
			if (rotation != null) {
				rotation.x = physics2D.body.getAngle() * MathUtils.radiansToDegrees;
			}
		}
	}

}
