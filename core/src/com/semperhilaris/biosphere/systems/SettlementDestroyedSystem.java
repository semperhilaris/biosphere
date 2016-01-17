package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.attachments.Attachment;
import com.semperhilaris.biosphere.components.*;
import com.semperhilaris.engine.Assets;
import com.semperhilaris.engine.components.SpineBone;
import com.semperhilaris.engine.components.SpineSlot;

/**
 *
 */
public class SettlementDestroyedSystem extends IteratingSystem {

	ComponentMapper<Health> healthComponentMapper;
	ComponentMapper<SpineBone> spineBoneComponentMapper;
	ComponentMapper<SpineSlot> spineSlotComponentMapper;

	Sound sound;

	public SettlementDestroyedSystem() {
		super(Family.all(Health.class, EvolutionStage.class, SpineBone.class, SpineSlot.class).get());

		healthComponentMapper = ComponentMapper.getFor(Health.class);
		spineBoneComponentMapper = ComponentMapper.getFor(SpineBone.class);
		spineSlotComponentMapper = ComponentMapper.getFor(SpineSlot.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Health health = healthComponentMapper.get(entity);
		if (health.value > 0) {
			return;
		}

		SpineBone spineBone = spineBoneComponentMapper.get(entity);
		SpineSlot spineSlot = spineSlotComponentMapper.get(entity);

		Skeleton skeleton = spineBone.bone.getSkeleton();
		Attachment attachment = skeleton.getAttachment(spineSlot.slot.getData().getName(), "ruins");
		spineSlot.slot.setAttachment(attachment);

		entity.remove(Health.class);
		entity.remove(Energy.class);
		entity.remove(Humans.class);
		entity.remove(AbductHumansTarget.class);
		entity.remove(GiveEnergyTarget.class);
		entity.remove(Threat.class);

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
