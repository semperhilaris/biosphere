package com.semperhilaris.engine.systems.input;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.semperhilaris.engine.Log;
import com.semperhilaris.engine.components.MovementSpeed;
import com.semperhilaris.engine.components.Physics2D;
import com.semperhilaris.engine.components.PlayerControl;
import com.semperhilaris.engine.components.Position;

/**
 * PlayerControl System
 */
public class PlayerControlSystem extends IteratingSystem implements InputProcessor {

	ComponentMapper<Position> positionComponentMapper;
	ComponentMapper<MovementSpeed> movementSpeedComponentMapper;
	ComponentMapper<Physics2D> physics2DComponentMapper;

	public PlayerControlSystem() {
		super(Family.all(PlayerControl.class, MovementSpeed.class, Position.class).get());

		positionComponentMapper = ComponentMapper.getFor(Position.class);
		movementSpeedComponentMapper = ComponentMapper.getFor(MovementSpeed.class);
		physics2DComponentMapper = ComponentMapper.getFor(Physics2D.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		MovementSpeed movementSpeed = movementSpeedComponentMapper.get(entity);
		Position position = positionComponentMapper.get(entity);
		Physics2D physics2D = physics2DComponentMapper.get(entity);

		if (physics2D != null) {
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				physics2D.body.applyLinearImpulse(0, movementSpeed.value * deltaTime, physics2D.body.getWorldCenter().x, physics2D.body.getWorldCenter().y, true);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				physics2D.body.applyLinearImpulse(movementSpeed.value * deltaTime, 0, physics2D.body.getWorldCenter().x, physics2D.body.getWorldCenter().y, true);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				physics2D.body.applyLinearImpulse(0, -movementSpeed.value * deltaTime, physics2D.body.getWorldCenter().x, physics2D.body.getWorldCenter().y, true);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				physics2D.body.applyLinearImpulse(-movementSpeed.value * deltaTime, 0, physics2D.body.getWorldCenter().x, physics2D.body.getWorldCenter().y, true);
			}
		} else {
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				position.y = position.y + movementSpeed.value * deltaTime;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				position.x = position.x + movementSpeed.value * deltaTime;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				position.y = position.y - movementSpeed.value * deltaTime;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				position.x = position.x - movementSpeed.value * deltaTime;
			}
		}

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}