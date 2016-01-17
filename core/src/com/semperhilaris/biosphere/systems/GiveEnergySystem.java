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
public class GiveEnergySystem extends IntervalIteratingSystem implements InputProcessor {

	private static int giveEnergyAmount = 10;

	private boolean isTouching;
	private Entity touchedEntity;

	private Viewport viewport;
	private Vector3 tmpVector = new Vector3();

	ComponentMapper<GiveEnergy> giveEnergyComponentMapper;
	ComponentMapper<GiveEnergyTarget> energyTargetComponentMapper;
	ComponentMapper<Energy> energyComponentMapper;
	ComponentMapper<EnergyRequirement> energyRequirementComponentMapper;
	ComponentMapper<Position> positionComponentMapper;
	ComponentMapper<ResourceChange> resourceChangeComponentMapper;

	public GiveEnergySystem(Viewport viewport, float interval) {
		super(Family.all(GiveEnergyTarget.class, Energy.class, Position.class).get(), interval);

		giveEnergyComponentMapper = ComponentMapper.getFor(GiveEnergy.class);
		energyTargetComponentMapper = ComponentMapper.getFor(GiveEnergyTarget.class);
		energyComponentMapper = ComponentMapper.getFor(Energy.class);
		energyRequirementComponentMapper = ComponentMapper.getFor(EnergyRequirement.class);
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
			ImmutableArray<Entity> playerEntities = getEngine().getEntitiesFor(Family.all(PlayerControl.class, GiveEnergy.class).get());
			GiveEnergy playerGiveEnergy = null;
			if (playerEntities != null && playerEntities.size() > 0) {
				Entity playerEntity = playerEntities.first();
				Position playerPosition = positionComponentMapper.get(playerEntity);
				if (playerPosition != null) {
					double playerDistance = playerPosition.distance(touchX, touchY);
					if (playerDistance > 400) {
						return false;
					}
				}
				playerGiveEnergy = giveEnergyComponentMapper.get(playerEntity);
				if (playerGiveEnergy != null) {
					playerGiveEnergy.target = null;
				}
			} else {
				return false;
			}

			GiveEnergyTarget giveEnergyTarget = energyTargetComponentMapper.get(entities.get(i));
			Position position = positionComponentMapper.get(entities.get(i));
			double distance = position.distance(touchX, touchY);
			if (distance < giveEnergyTarget.radius) {
				if (playerGiveEnergy != null) {
					playerGiveEnergy.target = entities.get(i);
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
					Energy gameStateEnergy = energyComponentMapper.get(entities.first());
					Energy energy = energyComponentMapper.get(entity);
					EnergyRequirement energyRequirement = energyRequirementComponentMapper.get(entity);
					ResourceChange resourceChange = resourceChangeComponentMapper.get(entity);
					if (gameStateEnergy != null && gameStateEnergy.value >= giveEnergyAmount && energy.value < energyRequirement.value) {
						gameStateEnergy.value -= giveEnergyAmount;
						energy.value += giveEnergyAmount;
						if (resourceChange != null) {
							resourceChange.add("+" + giveEnergyAmount, ColorScheme.ENERGY);
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
