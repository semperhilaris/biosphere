package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.semperhilaris.biosphere.components.Aggressor;
import com.semperhilaris.biosphere.components.MissileLaunchTargetFlag;
import com.semperhilaris.biosphere.components.SettlementReference;
import com.semperhilaris.biosphere.components.Threat;
import com.semperhilaris.biosphere.entities.Missile;
import com.semperhilaris.engine.Log;
import com.semperhilaris.engine.components.*;

/**
 *
 */
public class UngratefulBastardsSystem extends IteratingSystem {

	private static float FIRE_DELAY = 5f;

	private ImmutableArray<Entity> threatEntities;
	private ImmutableArray<Entity> launchTargetEntities;

	ComponentMapper<Aggressor> aggressorComponentMapper;
	ComponentMapper<Position> positionComponentMapper;
	ComponentMapper<Rotation> rotationComponentMapper;
	ComponentMapper<Physics2D> physics2DComponentMapper;
	ComponentMapper<SettlementReference> settlementReferenceComponentMapper;
	ComponentMapper<SteeringLocation> steeringLocationComponentMapper;

	public UngratefulBastardsSystem() {
		super(Family.all(Aggressor.class, Position.class).get());

		aggressorComponentMapper = ComponentMapper.getFor(Aggressor.class);
		positionComponentMapper = ComponentMapper.getFor(Position.class);
		rotationComponentMapper = ComponentMapper.getFor(Rotation.class);
		physics2DComponentMapper = ComponentMapper.getFor(Physics2D.class);
		settlementReferenceComponentMapper = ComponentMapper.getFor(SettlementReference.class);
		steeringLocationComponentMapper = ComponentMapper.getFor(SteeringLocation.class);
	}

	@Override
	public void update(float deltaTime) {
		threatEntities = getEngine().getEntitiesFor(Family.all(Threat.class, Position.class, Physics2D.class).get());
		launchTargetEntities = getEngine().getEntitiesFor(Family.all(MissileLaunchTargetFlag.class, Position.class).get());
		//TODO threats should be sorted by distance, closest first
		super.update(deltaTime);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Aggressor aggressor = aggressorComponentMapper.get(entity);
		aggressor.timeSinceLastFired += deltaTime;
		if (threatEntities == null || threatEntities.size() == 0 || launchTargetEntities == null || launchTargetEntities.size() == 0) {
			return;
		}
		for (Entity threatEntity : threatEntities) {
			if (aggressor.timeSinceLastFired < FIRE_DELAY || threatEntity == entity) {
				continue;
			}
			Position position = positionComponentMapper.get(entity);
			Position threatPosition = positionComponentMapper.get(threatEntity);
			if (position.distance(threatPosition) <= aggressor.radius) {
				for (Entity launchTargetEntity : launchTargetEntities) {
					SettlementReference settlementReference = settlementReferenceComponentMapper.get(launchTargetEntity);
					if (settlementReference.settlement == entity) {
						Rotation launchTargetRotation = rotationComponentMapper.get(launchTargetEntity);
						Physics2D threatPhysics2D = physics2DComponentMapper.get(threatEntity);
						SteeringLocation launchTargetLocation = steeringLocationComponentMapper.get(launchTargetEntity);
						Missile missile = new Missile(position.x, position.y, launchTargetRotation.x, launchTargetLocation, threatPhysics2D);
						getEngine().addEntity(missile);
						aggressor.timeSinceLastFired = 0;
						Log.msg("Ungrateful bastards", "Missile launched");
					}
				}

			}
		}
	}

}
