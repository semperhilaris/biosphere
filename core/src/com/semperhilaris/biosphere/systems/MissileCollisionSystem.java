package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Body;
import com.semperhilaris.biosphere.components.*;
import com.semperhilaris.engine.Assets;
import com.semperhilaris.engine.Events;
import com.semperhilaris.engine.components.GameState;
import com.semperhilaris.engine.components.Physics2D;
import com.semperhilaris.engine.components.Spine;
import com.semperhilaris.engine.events.physics.PreSolveEvent;
import net.engio.mbassy.listener.Handler;

/**
 *
 */
public class MissileCollisionSystem extends IteratingSystem {

	private static int DAMAGE = 20;
	private ImmutableArray<Entity> alienEntities;
	private ImmutableArray<Entity> destroyableEntities;

	Sound sound;

	ComponentMapper<Health> healthComponentMapper;
	ComponentMapper<Physics2D> physics2DComponentMapper;

	public MissileCollisionSystem() {
		super(Family.all(MissileFlag.class, Physics2D.class).get());
		Events.subscribe(this);

		healthComponentMapper = ComponentMapper.getFor(Health.class);
		physics2DComponentMapper = ComponentMapper.getFor(Physics2D.class);
	}

	@Override
	public void update(float deltaTime) {
		alienEntities = getEngine().getEntitiesFor(Family.all(AlienFlag.class).get());
		destroyableEntities = getEngine().getEntitiesFor(Family.all(Physics2D.class, Health.class).exclude(AlienFlag.class).get());
		super.update(deltaTime);
	}

	@Handler
	public void handleContact(PreSolveEvent preSolveEvent) {
		Entity gameStateEntity = getEngine().getEntitiesFor(Family.all(GameState.class, Health.class).get()).first();
		Health gameStateHealth = healthComponentMapper.get(gameStateEntity);

		Body contactBodyA = preSolveEvent.contact.getFixtureA().getBody();
		Body contactBodyB = preSolveEvent.contact.getFixtureB().getBody();

		for (int i = 0; i < getEntities().size(); i++) {
			Entity entity = getEntities().get(i);

			Physics2D physics2D = physics2DComponentMapper.get(entity);
			Body body = physics2D.body;

			if (body == contactBodyA || body == contactBodyB) {
				if (bodyIsAlien(contactBodyA) || bodyIsAlien(contactBodyB)) {
					if (gameStateHealth.value - DAMAGE < 0) {
						gameStateHealth.value = 0;
					} else {
						gameStateHealth.value -= DAMAGE;
					}
				} else if (destroyableEntities != null && destroyableEntities.size() > 0) {
					for (int j = 0; j < destroyableEntities.size(); j++) {
						Entity destroyableEntity = destroyableEntities.get(j);
						Physics2D destroyablePhysics2D = physics2DComponentMapper.get(destroyableEntity);
						Body destroyableBody = destroyablePhysics2D.body;
						if (destroyableBody == contactBodyA || destroyableBody == contactBodyB) {
							Health destroyableHealth = healthComponentMapper.get(destroyableEntity);
							if (destroyableHealth.value - DAMAGE < 0) {
								destroyableHealth.value = 0;
							} else {
								destroyableHealth.value -= DAMAGE;
							}
						}
					}
				}
				physics2D.destroyBody = true;
				getEngine().removeEntity(entity);
				//TODO AssetManager + Audio is broken on OSX El Capitan: https://github.com/libgdx/libgdx/issues/3572
				/*
				if (Assets.assetManager.getProgress() == 1 && sound == null) {
					sound = Assets.getSound("boom.mp3");
				}
				*/
				if (sound == null) {
					sound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/boom.mp3"));
				}
				sound.play();
			}
		}
	}

	public boolean bodyIsAlien(Body checkBody) {
		if (alienEntities != null && alienEntities.size() > 0) {
			for (int i = 0; i < alienEntities.size(); i++) {
				Physics2D physics2D = physics2DComponentMapper.get(alienEntities.get(i));
				if (checkBody == physics2D.body) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

	}

}
