package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
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
public class SettlementEvolutionSystem extends IntervalIteratingSystem {

	ComponentMapper<EvolutionStage> evolutionStageComponentMapper;
	ComponentMapper<SpineBone> spineBoneComponentMapper;
	ComponentMapper<SpineSlot> spineSlotComponentMapper;
	ComponentMapper<Energy> energyComponentMapper;
	ComponentMapper<EnergyRequirement> energyRequirementComponentMapper;
	ComponentMapper<HumansCapacity> humansCapacityComponentMapper;
	ComponentMapper<HumansSpawnRate> humansSpawnRateComponentMapper;
	ComponentMapper<Health> healthComponentMapper;

	Sound sound;

	public SettlementEvolutionSystem(float interval) {
		super(Family.all(
				EvolutionStage.class, SpineBone.class, SpineSlot.class, Energy.class, EnergyRequirement.class)
				.get(), interval);

		evolutionStageComponentMapper = ComponentMapper.getFor(EvolutionStage.class);
		spineBoneComponentMapper = ComponentMapper.getFor(SpineBone.class);
		spineSlotComponentMapper = ComponentMapper.getFor(SpineSlot.class);
		energyComponentMapper = ComponentMapper.getFor(Energy.class);
		energyRequirementComponentMapper = ComponentMapper.getFor(EnergyRequirement.class);
		humansCapacityComponentMapper = ComponentMapper.getFor(HumansCapacity.class);
		humansSpawnRateComponentMapper = ComponentMapper.getFor(HumansSpawnRate.class);
		healthComponentMapper = ComponentMapper.getFor(Health.class);
	}

	@Override
	protected void processEntity(Entity entity) {
		EvolutionStage evolutionStage = evolutionStageComponentMapper.get(entity);
		SpineBone spineBone = spineBoneComponentMapper.get(entity);
		SpineSlot spineSlot = spineSlotComponentMapper.get(entity);
		Energy energy = energyComponentMapper.get(entity);
		EnergyRequirement energyRequirement = energyRequirementComponentMapper.get(entity);
		HumansCapacity humansCapacity = humansCapacityComponentMapper.get(entity);
		HumansSpawnRate humansSpawnRate = humansSpawnRateComponentMapper.get(entity);
		Health health = healthComponentMapper.get(entity);

		if (energy.value >= energyRequirement.value && evolutionStage.stage < 4) {
			energy.value = energy.value - energyRequirement.value;
			evolutionStage.stage = evolutionStage.stage + 1;
			Skeleton skeleton = spineBone.bone.getSkeleton();
			Attachment attachment = skeleton.getAttachment(spineSlot.slot.getData().getName(), "settlement_"+evolutionStage.stage);
			spineSlot.slot.setAttachment(attachment);

			// Increase EnergyRequirement
			energyRequirement.value += (int)Math.ceil(energyRequirement.value * 0.5);

			// Increase HumansCapacity and HumansSpawnRate
			if (humansCapacity != null) {
				humansCapacity.value += (int)Math.ceil(humansCapacity.value * 0.5);
			}
			if (humansSpawnRate != null) {
				humansSpawnRate.value += (int)Math.ceil(humansSpawnRate.value * 0.5);
			}

			// Increase Health
			if (health != null) {
				health.max += (int)Math.ceil(health.max * 0.5);
				health.value = health.max;
			}

			// For stage 4 make the settlement hostile
			if (evolutionStage.stage >= 4) {
				entity.add(new Aggressor(400));
			}

			// Play the sfx
			//TODO AssetManager + Audio is broken on OSX El Capitan: https://github.com/libgdx/libgdx/issues/3572
			/*
			if (Assets.assetManager.getProgress() == 1 && sound == null) {
				sound = Assets.getSound("settlement_levelup.mp3");
			}
			*/
			if (sound == null) {
				sound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/settlement_levelup.mp3"));
			}
			sound.play();
		}
	}

}
