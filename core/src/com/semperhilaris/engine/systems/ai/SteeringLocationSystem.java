package com.semperhilaris.engine.systems.ai;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.semperhilaris.engine.GameConfig;
import com.semperhilaris.engine.components.Position;
import com.semperhilaris.engine.components.SteeringLocation;

/**
 * SteeringLocation System
 *
 * Updates a SteeringLocation with the Position coordinates.
 */
public class SteeringLocationSystem extends IteratingSystem {

	ComponentMapper<SteeringLocation> steeringLocationComponentMapper;
	ComponentMapper<Position> positionComponentMapper;

	public SteeringLocationSystem() {
		super(Family.all(SteeringLocation.class, Position.class).get());

		steeringLocationComponentMapper = ComponentMapper.getFor(SteeringLocation.class);
		positionComponentMapper = ComponentMapper.getFor(Position.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		SteeringLocation steeringLocation = steeringLocationComponentMapper.get(entity);
		Position position = positionComponentMapper.get(entity);

		steeringLocation.box2dLocation.getPosition().set(
				position.x * GameConfig.getFloat("PIXELS_TO_METERS"),
				position.y * GameConfig.getFloat("PIXELS_TO_METERS")
		);
	}

}
