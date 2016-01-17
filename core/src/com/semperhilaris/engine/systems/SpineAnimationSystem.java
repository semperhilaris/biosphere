package com.semperhilaris.engine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.semperhilaris.engine.components.*;

/**
 *
 */
public class SpineAnimationSystem extends IteratingSystem {

	ComponentMapper<Spine> spineComponentMapper;
	ComponentMapper<Position> positionComponentMapper;
	ComponentMapper<Rotation> rotationComponentMapper;
	ComponentMapper<Scale> scaleComponentMapper;
	ComponentMapper<Flip> flipComponentMapper;

	public SpineAnimationSystem() {
		super(Family.all(Spine.class).get());

		spineComponentMapper = ComponentMapper.getFor(Spine.class);
		positionComponentMapper = ComponentMapper.getFor(Position.class);
		rotationComponentMapper = ComponentMapper.getFor(Rotation.class);
		scaleComponentMapper = ComponentMapper.getFor(Scale.class);
		flipComponentMapper = ComponentMapper.getFor(Flip.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Spine spine = spineComponentMapper.get(entity);
		Position position = positionComponentMapper.get(entity);
		Rotation rotation = rotationComponentMapper.get(entity);
		Scale scale = scaleComponentMapper.get(entity);
		Flip flip = flipComponentMapper.get(entity);
		if (position != null) {
			spine.skeleton.setX(position.x);
			spine.skeleton.setY(position.y);
		}
		if (rotation != null) {
			spine.skeleton.getRootBone().setRotation(rotation.x);
		}
		if (scale != null) {
			spine.skeleton.getRootBone().setScaleX(scale.x);
			spine.skeleton.getRootBone().setScaleY(scale.y);
		}
		if (flip != null) {
			spine.skeleton.setFlipX(flip.x);
			spine.skeleton.setFlipY(flip.y);
		}
		spine.animationState.update(deltaTime);
		spine.animationState.apply(spine.skeleton);
		spine.skeleton.updateWorldTransform();
	}

}
