package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.semperhilaris.biosphere.components.MissileFlag;
import com.semperhilaris.engine.components.Physics2D;

/**
 *
 */
public class MissileAccelerationSystem extends IteratingSystem {

	private static float FORCE = 2.5f;
	private static Vector2 tmpVector = new Vector2(0, 1);

	ComponentMapper<Physics2D> physics2DComponentMapper;

	public MissileAccelerationSystem() {
		super(Family.all(MissileFlag.class, Physics2D.class).get());

		physics2DComponentMapper = ComponentMapper.getFor(Physics2D.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Physics2D physics2D = physics2DComponentMapper.get(entity);
		Vector2 direction = physics2D.body.getWorldVector(tmpVector);
		physics2D.body.applyLinearImpulse(direction.setLength(FORCE * deltaTime), physics2D.body.getWorldCenter(), true);
	}

}
