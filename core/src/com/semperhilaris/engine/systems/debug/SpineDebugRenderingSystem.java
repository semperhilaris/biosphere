package com.semperhilaris.engine.systems.debug;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.esotericsoftware.spine.SkeletonRendererDebug;
import com.semperhilaris.engine.GameConfig;
import com.semperhilaris.engine.components.Spine;
import com.semperhilaris.engine.util.RenderOrderComparator;

/**
 * Render debug lines for spine skeletons.
 */
public class SpineDebugRenderingSystem extends SortedIteratingSystem {

	ComponentMapper<Spine> spineComponentMapper;

	private SkeletonRendererDebug skeletonRendererDebug;
	private Camera camera;

	public SpineDebugRenderingSystem(Camera camera) {
		super(Family.all(Spine.class).get(), new RenderOrderComparator());
		skeletonRendererDebug = new SkeletonRendererDebug();
		this.camera = camera;

		spineComponentMapper = ComponentMapper.getFor(Spine.class);
	}

	@Override
	public void update(float deltaTime) {
		skeletonRendererDebug.getShapeRenderer().setProjectionMatrix(camera.combined);
		super.update(deltaTime);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Spine spine = spineComponentMapper.get(entity);
		skeletonRendererDebug.draw(spine.skeleton);
	}

	@Override
	public boolean checkProcessing() {
		return super.checkProcessing() && GameConfig.getBoolean("DEBUG");
	}
}
