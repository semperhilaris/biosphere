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
import com.semperhilaris.engine.components.GameState;
import com.semperhilaris.engine.components.PlayerControl;
import com.semperhilaris.engine.components.Position;

/**
 * EnergyTarget System
 */
public class AbductHumansSystem extends IntervalIteratingSystem implements InputProcessor {

	private static int abductHumansAmount = 1;

	private boolean isTouching;
	private Entity touchedEntity;

	private Viewport viewport;
	private Vector3 tmpVector = new Vector3();

	ComponentMapper<AbductHumans> abductHumansComponentMapper;
	ComponentMapper<AbductHumansTarget> abductHumansTargetComponentMapper;
	ComponentMapper<Humans> humansComponentMapper;
	ComponentMapper<Position> positionComponentMapper;
	ComponentMapper<ResourceChange> resourceChangeComponentMapper;

	public AbductHumansSystem(Viewport viewport, float interval) {
		super(Family.all(AbductHumansTarget.class, Humans.class, Position.class).get(), interval);

		abductHumansComponentMapper = ComponentMapper.getFor(AbductHumans.class);
		abductHumansTargetComponentMapper = ComponentMapper.getFor(AbductHumansTarget.class);
		humansComponentMapper = ComponentMapper.getFor(Humans.class);
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
			ImmutableArray<Entity> playerEntities = getEngine().getEntitiesFor(Family.all(PlayerControl.class, AbductHumans.class).get());
			AbductHumans playerAbductHumans = null;
			if (playerEntities != null && playerEntities.size() > 0) {
				Entity playerEntity = playerEntities.first();
				Position playerPosition = positionComponentMapper.get(playerEntity);
				if (playerPosition != null) {
					double playerDistance = playerPosition.distance(touchX, touchY);
					if (playerDistance > 400) {
						return false;
					}
				}
				playerAbductHumans = abductHumansComponentMapper.get(playerEntity);
				if (playerAbductHumans != null) {
					playerAbductHumans.target = null;
				}
			} else {
				return false;
			}

			AbductHumansTarget abductHumansTarget = abductHumansTargetComponentMapper.get(entities.get(i));
			Position position = positionComponentMapper.get(entities.get(i));
			double distance = position.distance(touchX, touchY);
			if (distance < abductHumansTarget.radius) {
				if (playerAbductHumans != null) {
					playerAbductHumans.target = entities.get(i);
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
				ImmutableArray<Entity> entities = getEngine().getEntitiesFor(Family.all(GameState.class, Energy.class).get());
				if (entities.size() > 0) {
					Humans gameStateHumans = humansComponentMapper.get(entities.first());
					Humans humans = humansComponentMapper.get(entity);
					if (gameStateHumans != null && humans.value > 0) {
						int abductAmount = abductHumansAmount;
						if ((humans.value - abductAmount) < 0) {
							abductAmount = humans.value;
						}
						humans.value -= abductAmount;
						gameStateHumans.value += abductAmount;
						ResourceChange resourceChange = resourceChangeComponentMapper.get(entity);
						if (resourceChange != null) {
							resourceChange.add("-" + abductAmount, ColorScheme.HUMANS);
						}
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
