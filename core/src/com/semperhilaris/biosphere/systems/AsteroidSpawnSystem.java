package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.semperhilaris.biosphere.components.AsteroidSpawn;
import com.semperhilaris.biosphere.entities.Asteroid;
import com.semperhilaris.engine.components.Position;

/**
 *
 */
public class AsteroidSpawnSystem extends IntervalIteratingSystem {

	private static int maxAsteroids = 5;
	private static float positionVariance = 100;
	private static float sizeVariance = 0.5f;

	private int asteroidsCount;

	ComponentMapper<AsteroidSpawn> asteroidSpawnComponentMapper;
	ComponentMapper<Position> positionComponentMapper;

	public AsteroidSpawnSystem(float interval) {
		super(Family.all(AsteroidSpawn.class, Position.class).get(), interval);

		asteroidSpawnComponentMapper = ComponentMapper.getFor(AsteroidSpawn.class);
		positionComponentMapper = ComponentMapper.getFor(Position.class);
	}

	@Override
	protected void updateInterval () {
		asteroidsCount = 0;
		for (int i = 0; i < getEntities().size(); ++i) {
			Entity entity = getEntities().get(i);
			AsteroidSpawn asteroidSpawn = asteroidSpawnComponentMapper.get(entity);
			if (asteroidSpawn.asteroid != null) {
				asteroidsCount++;
			}
		}
		super.updateInterval();
	}

	@Override
	protected void processEntity(Entity entity) {
		if (asteroidsCount >= maxAsteroids) {
			return;
		}

		if (MathUtils.random() > 0.2f) {
			return;
		}

		AsteroidSpawn asteroidSpawn = asteroidSpawnComponentMapper.get(entity);
		Position position = positionComponentMapper.get(entity);

		float size = MathUtils.random(1 - sizeVariance, 1 + sizeVariance);
		float positionX = MathUtils.random(position.x - positionVariance, position.x + positionVariance);
		float positionY = MathUtils.random(position.y - positionVariance, position.y + positionVariance);

		Asteroid asteroid = new Asteroid(positionX, positionY, size);
		asteroidSpawn.asteroid = asteroid;
		getEngine().addEntity(asteroid);
		asteroidsCount++;
	}

}
