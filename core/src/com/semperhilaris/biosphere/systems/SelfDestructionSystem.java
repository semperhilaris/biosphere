package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.semperhilaris.biosphere.components.Health;
import com.semperhilaris.biosphere.components.Humans;
import com.semperhilaris.biosphere.components.HumansCapacity;
import com.semperhilaris.biosphere.components.SelfDestruction;

/**
 *
 */
public class SelfDestructionSystem extends IntervalIteratingSystem {

	ComponentMapper<SelfDestruction> selfDestructionComponentMapper;
	ComponentMapper<Health> healthComponentMapper;
	ComponentMapper<Humans> humansComponentMapper;
	ComponentMapper<HumansCapacity> humansCapacityComponentMapper;

	public SelfDestructionSystem(float interval) {
		super(Family.all(SelfDestruction.class, Health.class, Humans.class, HumansCapacity.class).get(), interval);

		selfDestructionComponentMapper = ComponentMapper.getFor(SelfDestruction.class);
		healthComponentMapper = ComponentMapper.getFor(Health.class);
		humansComponentMapper = ComponentMapper.getFor(Humans.class);
		humansCapacityComponentMapper = ComponentMapper.getFor(HumansCapacity.class);
	}

	@Override
	protected void processEntity(Entity entity) {
		SelfDestruction selfDestruction = selfDestructionComponentMapper.get(entity);
		Health health = healthComponentMapper.get(entity);
		Humans humans = humansComponentMapper.get(entity);
		HumansCapacity humansCapacity = humansCapacityComponentMapper.get(entity);

		if (humans.value == humansCapacity.value) {
			int damage = MathUtils.random(selfDestruction.amountMin, selfDestruction.amountMax);

			if (health.value - damage < 0) {
				health.value = 0;
			} else {
				health.value -= damage;
			}
		}

	}

}
