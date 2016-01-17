package com.semperhilaris.engine.systems.camera;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.semperhilaris.engine.components.CameraFocus;
import com.semperhilaris.engine.components.PointOfInterest;
import com.semperhilaris.engine.components.Position;

/**
 * PointOfInterestCamera system
 */
public class PointOfInterestCameraSystem extends IteratingSystem {

	private OrthographicCamera camera;
	private float defaultZoom = 1f;
	private ImmutableArray<Entity> pointsOfInterestEntities;

	ComponentMapper<CameraFocus> cameraFocusComponentMapper;
	ComponentMapper<PointOfInterest> pointOfInterestComponentMapper;
	ComponentMapper<Position> positionComponentMapper;

	public PointOfInterestCameraSystem(OrthographicCamera camera, float defaultZoom) {
		super(Family.all(CameraFocus.class).get());

		cameraFocusComponentMapper = ComponentMapper.getFor(CameraFocus.class);
		pointOfInterestComponentMapper = ComponentMapper.getFor(PointOfInterest.class);
		positionComponentMapper = ComponentMapper.getFor(Position.class);

		this.camera = camera;
		this.defaultZoom = defaultZoom;
	}

	@Override
	public void update(float deltaTime) {
		pointsOfInterestEntities = getEngine().getEntitiesFor(Family.all(PointOfInterest.class, Position.class).get());
		super.update(deltaTime);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		float x = 0f;
		float y = 0f;
		float zoom = defaultZoom;
		float coeff = 1f;

		Position position = positionComponentMapper.get(entity);

		Entity nearestPointEntity = getNearestPoint(position);

		if (nearestPointEntity == null) {
			coeff = 1f;
			x = position.x;
			y = position.y;
		} else {
			PointOfInterest nearestPointOfInterest = pointOfInterestComponentMapper.get(nearestPointEntity);
			Position nearestPointPosition = positionComponentMapper.get(nearestPointEntity);
			double distance = position.distance(nearestPointPosition);

			if (distance > nearestPointOfInterest.outerRadius) {
				coeff = 1f;
				x = position.x;
				y = position.y;
			} else {
				coeff = ((float)distance - nearestPointOfInterest.innerRadius) / nearestPointOfInterest.getRadiusDistance();
				if (coeff < 0) {
					coeff = 0f;
				}
				float deltaX = position.x - nearestPointPosition.x;
				float deltaY = position.y - nearestPointPosition.y;
				x = nearestPointPosition.x + coeff * deltaX;
				y = nearestPointPosition.y + coeff * deltaY;
				zoom = defaultZoom + (nearestPointOfInterest.zoom - nearestPointOfInterest.zoom * coeff);
			}
		}

		camera.position.set(
				camera.position.x + (x - camera.position.x) * deltaTime,
				camera.position.y + (y - camera.position.y) * deltaTime,
				0);
		camera.zoom = zoom;
	}

	private Entity getNearestPoint(Position position) {
		Entity currentBest = null;
		double currentBestDistance = 0f;
		for (int i = 0; i < pointsOfInterestEntities.size(); ++i) {
			Entity poiEntity = pointsOfInterestEntities.get(i);
			Position poiPosition = positionComponentMapper.get(poiEntity);
			double distance = position.distance(poiPosition);
			if (i == 0 || distance < currentBestDistance) {
				currentBest = poiEntity;
				currentBestDistance = distance;
			}
		}
		return currentBest;
	}

}
