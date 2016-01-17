package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.semperhilaris.biosphere.components.AsteroidFlag;
import com.semperhilaris.biosphere.components.AsteroidSpawn;
import com.semperhilaris.biosphere.components.Energy;

/**
 *
 */
public class AsteroidDepletedSystem extends IntervalIteratingSystem {

	ComponentMapper<Energy> energyComponentMapper;
	ComponentMapper<AsteroidSpawn> asteroidSpawnComponentMapper;

	public AsteroidDepletedSystem(float interval) {
		super(Family.all(AsteroidFlag.class, Energy.class).get(), interval);

		energyComponentMapper = ComponentMapper.getFor(Energy.class);
		asteroidSpawnComponentMapper = ComponentMapper.getFor(AsteroidSpawn.class);
	}

	@Override
	protected void processEntity(Entity entity) {
		ImmutableArray<Entity> spawnEntities = getEngine().getEntitiesFor(Family.all(AsteroidSpawn.class).get());

		Energy energy = energyComponentMapper.get(entity);
		if (energy.value <= 0) {
			for (Entity spawnEntity : spawnEntities) {
				AsteroidSpawn asteroidSpawn = asteroidSpawnComponentMapper.get(spawnEntity);
				if (asteroidSpawn.asteroid == entity) {
					asteroidSpawn.asteroid = null;
				}
			}
			getEngine().removeEntity(entity);
		}
	}

}
