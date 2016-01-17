package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.steer.behaviors.Face;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.semperhilaris.biosphere.components.MissileState;
import com.semperhilaris.engine.components.Physics2D;
import com.semperhilaris.engine.components.Steering;

/**
 *
 */
public class MissileStateSystem extends IteratingSystem {

	private static float MAX_LIFE_TIME = 20f;
	private static float TIME_UNTIL_STEERING = 2f;

	ComponentMapper<MissileState> missileStateComponentMapper;
	ComponentMapper<Physics2D> physics2DComponentMapper;
	ComponentMapper<Steering> steeringComponentMapper;

	public MissileStateSystem() {
		super(Family.all(MissileState.class, Physics2D.class).get());

		missileStateComponentMapper = ComponentMapper.getFor(MissileState.class);
		physics2DComponentMapper = ComponentMapper.getFor(Physics2D.class);
		steeringComponentMapper = ComponentMapper.getFor(Steering.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		MissileState missileState = missileStateComponentMapper.get(entity);
		Physics2D physics2D = physics2DComponentMapper.get(entity);

		missileState.lifeTime += deltaTime;
		if (missileState.lifeTime >= TIME_UNTIL_STEERING && !missileState.isSeekingTarget) {
			entity.remove(Steering.class);
			entity.add(new Steering(new Face(physics2D, missileState.seekingTarget)
					.setTimeToTarget(0.1f)
			));
			missileState.isSeekingTarget = true;
			Array<Fixture> fixtures = physics2D.body.getFixtureList();
			for (Fixture fixture : fixtures) {
				Filter filter = fixture.getFilterData();
				filter.maskBits = 0x1;
				fixture.setFilterData(filter);
			}
		}

		if (missileState.lifeTime >= MAX_LIFE_TIME) {
			physics2D.destroyBody = true;
			getEngine().removeEntity(entity);
		}
	}
}
