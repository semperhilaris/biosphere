package com.semperhilaris.engine.systems.input;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.semperhilaris.engine.components.MoveToTarget;
import com.semperhilaris.engine.components.Position;
import com.semperhilaris.engine.components.SteeringLocation;

/**
 * MoveToSystem
 *
 * Sets SteeringLocation Component to the coordinates tapped on screen.
 */
public class MoveToSystem extends IteratingSystem implements GestureDetector.GestureListener {

	private Viewport viewport;
	private Vector3 tmpVector = new Vector3();

	ComponentMapper<Position> positionComponentMapper;

	public MoveToSystem(Viewport viewport) {
		super(Family.all(MoveToTarget.class, Position.class).get());

		positionComponentMapper = ComponentMapper.getFor(Position.class);

		this.viewport = viewport;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		if (checkProcessing()) {
			tmpVector.set(x, y, 0);
			Vector3 worldPosition = viewport.unproject(tmpVector);

			ImmutableArray<Entity> entities = getEntities();

			for (int i = 0; i < entities.size(); ++i) {
				Position position = positionComponentMapper.get(entities.get(i));
				position.set(worldPosition.x, worldPosition.y);
			}

			return true;
		}
		return false;
	}


	// Stubs

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

}
