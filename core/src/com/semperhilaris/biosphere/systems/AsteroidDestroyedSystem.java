package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.semperhilaris.biosphere.components.AbsorbEnergyTarget;
import com.semperhilaris.biosphere.components.AsteroidFlag;
import com.semperhilaris.biosphere.components.Energy;
import com.semperhilaris.biosphere.components.Health;
import com.semperhilaris.engine.Assets;
import com.semperhilaris.engine.components.Spine;

/**
 *
 */
public class AsteroidDestroyedSystem extends IteratingSystem {

	ComponentMapper<Health> healthComponentMapper;
	ComponentMapper<Spine> spineComponentMapper;

	Sound sound;

	public AsteroidDestroyedSystem() {
		super(Family.all(AsteroidFlag.class, Health.class).get());

		healthComponentMapper = ComponentMapper.getFor(Health.class);
		spineComponentMapper = ComponentMapper.getFor(Spine.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Health health = healthComponentMapper.get(entity);
		if (health.value > 0) {
			return;
		}

		Spine spine = spineComponentMapper.get(entity);
		if (spine != null) {
			spine.skeleton.setSkin("destroyed");
			spine.skeleton.setToSetupPose();
		}

		entity.remove(Health.class);
		entity.add(new Energy(100));
		entity.add(new AbsorbEnergyTarget(400));

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
