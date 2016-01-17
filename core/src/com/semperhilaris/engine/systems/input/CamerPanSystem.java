package com.semperhilaris.engine.systems.input;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * CameraPan System
 */
public class CamerPanSystem extends EntitySystem implements GestureDetector.GestureListener {

	private Camera camera;
	private final Vector3 tmp = new Vector3();

	public CamerPanSystem(Camera camera) {
		this.camera = camera;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
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
		if (checkProcessing()) {
			float zoom;
			if (camera instanceof OrthographicCamera) {
				zoom = ((OrthographicCamera) camera).zoom;
				camera.position.add(-deltaX * zoom, deltaY * zoom, 0);
			} else if (camera instanceof PerspectiveCamera) {
				tmp.set(camera.direction).crs(camera.up).nor().scl(deltaX);
				camera.position.sub(tmp);
				tmp.set(deltaX, 0, deltaY);
				camera.position.sub(tmp);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		if (checkProcessing()) {
			float ratio = initialDistance / distance;
			if (camera instanceof OrthographicCamera) {
				((OrthographicCamera) camera).zoom = distance * ratio;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

}
