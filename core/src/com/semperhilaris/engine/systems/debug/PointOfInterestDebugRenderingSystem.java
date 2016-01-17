package com.semperhilaris.engine.systems.debug;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semperhilaris.engine.GameConfig;
import com.semperhilaris.engine.components.PointOfInterest;
import com.semperhilaris.engine.components.Position;

/**
 * System for debug rendering of PointOfInterest entities.
 */
public class PointOfInterestDebugRenderingSystem extends IteratingSystem {

	private Camera camera;
	private ShapeRenderer renderer = new ShapeRenderer();

	ComponentMapper<PointOfInterest> pointOfInterestComponentMapper;
	ComponentMapper<Position> positionComponentMapper;

	public PointOfInterestDebugRenderingSystem(Camera camera) {
		super(Family.all(PointOfInterest.class, Position.class).get());

		pointOfInterestComponentMapper = ComponentMapper.getFor(PointOfInterest.class);
		positionComponentMapper = ComponentMapper.getFor(Position.class);

		this.camera = camera;
	}

	@Override
	public void update(float deltaTime) {
		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeRenderer.ShapeType.Line);
		super.update(deltaTime);
		renderer.end();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PointOfInterest pointOfInterest = pointOfInterestComponentMapper.get(entity);
		Position position = positionComponentMapper.get(entity);

		renderer.setColor(0, 1, 0, 1);
		renderer.circle(position.x, position.y, pointOfInterest.innerRadius);

		renderer.setColor(0, 0, 1, 1);
		renderer.circle(position.x, position.y, pointOfInterest.outerRadius);
	}

	@Override
	public boolean checkProcessing() {
		return super.checkProcessing() && GameConfig.getBoolean("DEBUG");
	}

}
