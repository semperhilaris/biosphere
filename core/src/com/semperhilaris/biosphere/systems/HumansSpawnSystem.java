package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.semperhilaris.biosphere.components.Humans;
import com.semperhilaris.biosphere.components.HumansCapacity;
import com.semperhilaris.biosphere.components.HumansSpawnRate;

/**
 * HumansSpawn System
 */
public class HumansSpawnSystem extends IntervalIteratingSystem {

	ComponentMapper<Humans> humansComponentMapper;
	ComponentMapper<HumansCapacity> humansCapacityComponentMapper;
	ComponentMapper<HumansSpawnRate> humansSpawnRateComponentMapper;

	public HumansSpawnSystem(float interval) {
		super(Family.all(Humans.class, HumansCapacity.class, HumansSpawnRate.class).get(), interval);

		humansComponentMapper = ComponentMapper.getFor(Humans.class);
		humansCapacityComponentMapper = ComponentMapper.getFor(HumansCapacity.class);
		humansSpawnRateComponentMapper = ComponentMapper.getFor(HumansSpawnRate.class);
	}

	@Override
	protected void processEntity(Entity entity) {
		Humans humans = humansComponentMapper.get(entity);
		HumansCapacity humansCapacity = humansCapacityComponentMapper.get(entity);
		HumansSpawnRate humansSpawnRate = humansSpawnRateComponentMapper.get(entity);

		int remainingCapacity = humansCapacity.value - humans.value;
		if (remainingCapacity > humansSpawnRate.value) {
			humans.value += humansSpawnRate.value;
		} else {
			humans.value = humansCapacity.value;
		}
	}

}
