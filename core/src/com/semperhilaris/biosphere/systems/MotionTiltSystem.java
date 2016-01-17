package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.semperhilaris.biosphere.components.AlienFlag;
import com.semperhilaris.engine.components.Physics2D;
import com.semperhilaris.engine.components.Rotation;

/**
 *
 */
public class MotionTiltSystem extends IteratingSystem {

	private static final float tiltMax = 10;

	ComponentMapper<Rotation> rotationComponentMapper;
	ComponentMapper<Physics2D> physics2DComponentMapper;

	public MotionTiltSystem() {
		super(Family.all(AlienFlag.class, Rotation.class, Physics2D.class).get());

		rotationComponentMapper = ComponentMapper.getFor(Rotation.class);
		physics2DComponentMapper = ComponentMapper.getFor(Physics2D.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Rotation rotation = rotationComponentMapper.get(entity);
		Physics2D physics2D = physics2DComponentMapper.get(entity);

		float velocityX = physics2D.body.getLinearVelocity().x * 10;
		if (velocityX > tiltMax) {
			velocityX = tiltMax;
		}
		if (velocityX < -tiltMax) {
			velocityX = -tiltMax;
		}
		rotation.x = -velocityX;
	}

}
