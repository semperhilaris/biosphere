package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.semperhilaris.biosphere.components.Health;
import com.semperhilaris.engine.Events;
import com.semperhilaris.engine.components.GameState;
import com.semperhilaris.engine.events.ScreenChangeEvent;

/**
 *
 */
public class DeathSystem extends IteratingSystem {

	ComponentMapper<Health> healthComponentMapper;

	public DeathSystem() {
		super(Family.all(GameState.class, Health.class).get());

		healthComponentMapper = ComponentMapper.getFor(Health.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Health health = healthComponentMapper.get(entity);
		if (health.value <= 0) {
			Events.post(new ScreenChangeEvent("SCORE"));
		}
	}
}
