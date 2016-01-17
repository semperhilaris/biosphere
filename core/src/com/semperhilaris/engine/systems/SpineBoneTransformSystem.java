package com.semperhilaris.engine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.spine.Skeleton;
import com.semperhilaris.engine.components.Position;
import com.semperhilaris.engine.components.Rotation;
import com.semperhilaris.engine.components.Scale;
import com.semperhilaris.engine.components.SpineBone;

/**
 * SpineBoneTransform System
 *
 * Applies transformation of a Spine Bone to Position, Rotation and Scale components of the same entity.
 */
public class SpineBoneTransformSystem extends IteratingSystem {

	ComponentMapper<SpineBone> spineBoneComponentMapper;
	ComponentMapper<Position> positionComponentMapper;
	ComponentMapper<Rotation> rotationComponentMapper;
	ComponentMapper<Scale> scaleComponentMapper;

	public SpineBoneTransformSystem() {
		super(Family.all(SpineBone.class).get());

		spineBoneComponentMapper = ComponentMapper.getFor(SpineBone.class);
		positionComponentMapper = ComponentMapper.getFor(Position.class);
		rotationComponentMapper = ComponentMapper.getFor(Rotation.class);
		scaleComponentMapper = ComponentMapper.getFor(Scale.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		SpineBone spineBone = spineBoneComponentMapper.get(entity);
		Position position = positionComponentMapper.get(entity);
		Rotation rotation = rotationComponentMapper.get(entity);
		Scale scale = scaleComponentMapper.get(entity);

		Skeleton skeleton = spineBone.bone.getSkeleton();

		if (position != null) {
			position.x = skeleton.getX() + spineBone.bone.getWorldX();
			position.y = skeleton.getY() + spineBone.bone.getWorldY();
		}

		if (rotation != null) {
			rotation.x = spineBone.bone.getWorldRotation();
		}

		if (scale != null) {
			scale.x = spineBone.bone.getWorldScaleX();
			scale.y = spineBone.bone.getWorldScaleY();
		}

	}
}
