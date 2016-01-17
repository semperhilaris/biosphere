package com.semperhilaris.engine.systems.camera;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.semperhilaris.engine.components.CameraFocus;
import com.semperhilaris.engine.components.Position;

/**
 * CameraFocus System
 *
 * Makes a Camera always look at the entity with the CameraFocus component.
 */
public class CameraFocusSystem extends IteratingSystem {

	ComponentMapper<Position> positionComponentMapper;

	private Camera camera;

	public CameraFocusSystem(Camera camera) {
		super(Family.all(CameraFocus.class, Position.class).get());

		this.camera = camera;

		positionComponentMapper = ComponentMapper.getFor(Position.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Position position = positionComponentMapper.get(entity);
		camera.position.set(position.x, position.y, camera.position.z);
	}
}
