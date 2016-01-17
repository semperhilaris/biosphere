package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Body;
import com.semperhilaris.biosphere.components.AbsorbEnergyTarget;
import com.semperhilaris.biosphere.components.AsteroidFlag;
import com.semperhilaris.biosphere.components.Energy;
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
public class AsteroidEnergyCollectionSystem extends IteratingSystem {

	ComponentMapper<Energy> energyComponentMapper;
	ComponentMapper<Physics2D> physics2DComponentMapper;
	ComponentMapper<Spine> spineComponentMapper;

	Sound sound;

	public AsteroidEnergyCollectionSystem() {
		super(Family.all(AsteroidFlag.class, AbsorbEnergyTarget.class, Energy.class, Physics2D.class).get());
		Events.subscribe(this);

		energyComponentMapper = ComponentMapper.getFor(Energy.class);
		physics2DComponentMapper = ComponentMapper.getFor(Physics2D.class);
		spineComponentMapper = ComponentMapper.getFor(Spine.class);
	}

	@Handler
	public void handleContact(PreSolveEvent preSolveEvent) {
		Entity gameStateEntity = getEngine().getEntitiesFor(Family.all(GameState.class, Energy.class).get()).first();
		Energy gameStateEnergy = energyComponentMapper.get(gameStateEntity);

		Body contactBodyA = preSolveEvent.contact.getFixtureA().getBody();
		Body contactBodyB = preSolveEvent.contact.getFixtureB().getBody();

		for (int i=0; i<getEntities().size(); i++) {
			Entity entity = getEntities().get(i);

			Physics2D physics2D = physics2DComponentMapper.get(entity);
			Body body = physics2D.body;

			if (body == contactBodyA || body == contactBodyB) {
				Spine spine = spineComponentMapper.get(entity);
				if (spine != null) {
					spine.skeleton.setSkin("depleted");
					spine.skeleton.setToSetupPose();
				}

				Energy energy = energyComponentMapper.get(entity);
				gameStateEnergy.value += energy.value;
				energy.value = 0;

				physics2D.destroyBody = true;

				//TODO AssetManager + Audio is broken on OSX El Capitan: https://github.com/libgdx/libgdx/issues/3572
				/*
				if (Assets.assetManager.getProgress() == 1 && sound == null) {
					sound = Assets.getSound("boom.mp3");
				}
				*/
				if (sound == null) {
					sound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/energy.mp3"));
				}
				sound.play();
			}
		}
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

	}

}
