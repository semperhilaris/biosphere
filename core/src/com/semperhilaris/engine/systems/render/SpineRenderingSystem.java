package com.semperhilaris.engine.systems.render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.semperhilaris.engine.components.*;
import com.semperhilaris.engine.util.RenderOrderComparator;

/**
 * Rendering system for Spine
 */
public class SpineRenderingSystem extends SortedIteratingSystem {

	ComponentMapper<Spine> spineComponentMapper;

	private Camera camera;
	private PolygonSpriteBatch batch;
	private SkeletonRenderer skeletonRenderer;

	public SpineRenderingSystem(Camera camera, PolygonSpriteBatch batch) {
		super(Family.all(Spine.class, Position.class).get(), new RenderOrderComparator());

		spineComponentMapper = ComponentMapper.getFor(Spine.class);

		this.camera = camera;
		this.batch = batch;
		skeletonRenderer = new SkeletonRenderer();
	}

	@Override
	public void update (float deltaTime) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		super.update(deltaTime);
		batch.end();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Spine spine = spineComponentMapper.get(entity);
		skeletonRenderer.draw(batch, spine.skeleton);
	}

}
