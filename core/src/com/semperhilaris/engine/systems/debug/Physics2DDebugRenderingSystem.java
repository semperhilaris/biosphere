package com.semperhilaris.engine.systems.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.semperhilaris.engine.GameConfig;

/**
 *
 */
public class Physics2DDebugRenderingSystem extends EntitySystem {

	private World physicsWorld;
	private Box2DDebugRenderer debugRenderer;
	private Camera camera;

	public Physics2DDebugRenderingSystem(World physicsWorld, Camera camera) {
		this.physicsWorld = physicsWorld;
		debugRenderer = new Box2DDebugRenderer();
		this.camera = camera;
	}

	@Override
	public void update(float deltaTime) {
		debugRenderer.render(physicsWorld, camera.combined.cpy().scale(
				GameConfig.getFloat("METERS_TO_PIXELS"),
				GameConfig.getFloat("METERS_TO_PIXELS"),
				1
		));
	}

	@Override
	public boolean checkProcessing() {
		return super.checkProcessing() && GameConfig.getBoolean("DEBUG");
	}

}
