package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.semperhilaris.biosphere.components.*;
import com.semperhilaris.biosphere.util.ColorScheme;
import com.semperhilaris.engine.components.PlayerControl;
import com.semperhilaris.engine.components.Position;

/**
 * EnergyTarget System
 */
public class DestroySystem extends IntervalIteratingSystem implements InputProcessor {

	private static int damageAmount = 10;

	private boolean isTouching;
	private Entity touchedEntity;

	private Viewport viewport;
	private Vector3 tmpVector = new Vector3();

	ComponentMapper<Destroy> destroyComponentMapper;
	ComponentMapper<DestroyTarget> destroyTargetComponentMapper;
	ComponentMapper<Health> healthComponentMapper;
	ComponentMapper<Position> positionComponentMapper;
	ComponentMapper<ResourceChange> resourceChangeComponentMapper;

	public DestroySystem(Viewport viewport, float interval) {
		super(Family.all(DestroyTarget.class, Health.class, Position.class).get(), interval);

		destroyComponentMapper = ComponentMapper.getFor(Destroy.class);
		destroyTargetComponentMapper = ComponentMapper.getFor(DestroyTarget.class);
		healthComponentMapper = ComponentMapper.getFor(Health.class);
		positionComponentMapper = ComponentMapper.getFor(Position.class);
		resourceChangeComponentMapper = ComponentMapper.getFor(ResourceChange.class);

		this.viewport = viewport;
	}

	private boolean checkTouch(int screenX, int screenY) {
		tmpVector.set(screenX, screenY, 0);
		Vector3 worldPosition = viewport.unproject(tmpVector);
		float touchX = worldPosition.x;
		float touchY = worldPosition.y;
		touchedEntity = null;

		ImmutableArray<Entity> entities = getEntities();
		for (int i = 0; i < entities.size(); ++i) {
			ImmutableArray<Entity> playerEntities = getEngine().getEntitiesFor(Family.all(PlayerControl.class, Destroy.class).get());
			Destroy playerDestroy = null;
			if (playerEntities != null && playerEntities.size() > 0) {
				Entity playerEntity = playerEntities.first();
				Position playerPosition = positionComponentMapper.get(playerEntity);
				if (playerPosition != null) {
					double playerDistance = playerPosition.distance(touchX, touchY);
					if (playerDistance > 600) {
						return false;
					}
				}
				playerDestroy = destroyComponentMapper.get(playerEntity);
				if (playerDestroy != null) {
					playerDestroy.target = null;
				}
			} else {
				return false;
			}

			DestroyTarget destroyTarget = destroyTargetComponentMapper.get(entities.get(i));
			Position position = positionComponentMapper.get(entities.get(i));
			double distance = position.distance(touchX, touchY);
			if (distance < destroyTarget.radius) {
				if (playerDestroy != null) {
					playerDestroy.target = entities.get(i);
				}
				touchedEntity = entities.get(i);
				return true;
			}
		}

		return false;
	}

	@Override
	protected void processEntity(Entity entity) {
		if (touchedEntity != null) {
			if (entity == touchedEntity) {
				Health health = healthComponentMapper.get(entity);
				if (health.value > 0) {
					int damage = damageAmount;
					if ((health.value - damage) < 0) {
						damage = health.value;
					}
					health.value -= damage;
					ResourceChange resourceChange = resourceChangeComponentMapper.get(entity);
					if (resourceChange != null) {
						resourceChange.add("-" + damage, ColorScheme.HEALTH);
					}
				}
			}
		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (checkProcessing()) {
			isTouching = true;
			return checkTouch(screenX, screenY);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (checkProcessing()) {
			isTouching = true;
			return checkTouch(screenX, screenY);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (checkProcessing()) {
			isTouching = false;
		}
		return false;
	}

	// Stubs

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
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
