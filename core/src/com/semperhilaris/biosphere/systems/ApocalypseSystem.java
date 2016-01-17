package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.semperhilaris.biosphere.components.Health;
import com.semperhilaris.biosphere.components.SettlementFlag;
import com.semperhilaris.engine.Events;
import com.semperhilaris.engine.events.ScreenChangeEvent;

/**
 *
 */
public class ApocalypseSystem extends IteratingSystem {

	private int aliveSettlements;

	ComponentMapper<Health> healthComponentMapper;

	public ApocalypseSystem() {
		super(Family.all(SettlementFlag.class, Health.class).get());

		healthComponentMapper = ComponentMapper.getFor(Health.class);
	}

	@Override
	public void update(float deltaTime) {
		aliveSettlements = 0;
		super.update(deltaTime);
		if (aliveSettlements == 0) {
			Events.post(new ScreenChangeEvent("SCORE"));
		}
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Health health = healthComponentMapper.get(entity);
		if (health.value > 0) {
			aliveSettlements++;
		}
	}

}
