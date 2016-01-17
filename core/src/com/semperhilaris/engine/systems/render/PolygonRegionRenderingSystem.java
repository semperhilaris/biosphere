package com.semperhilaris.engine.systems.render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.semperhilaris.engine.components.Position;
import com.semperhilaris.engine.components.Renderable;
import com.semperhilaris.engine.util.RenderOrderComparator;

/**
 *
 */
public class PolygonRegionRenderingSystem extends SortedIteratingSystem {

	private Camera camera;
	private PolygonSpriteBatch spriteBatch;

	ComponentMapper<Renderable> renderableComponentMapper;
	ComponentMapper<Position> positionComponentMapper;

	public PolygonRegionRenderingSystem(Camera camera, PolygonSpriteBatch spriteBatch) {
		super(Family.all(Renderable.class, Position.class).get(), new RenderOrderComparator());

		renderableComponentMapper = ComponentMapper.getFor(Renderable.class);
		positionComponentMapper = ComponentMapper.getFor(Position.class);

		this.camera = camera;
		this.spriteBatch = spriteBatch;
	}

	@Override
	public void update(float deltaTime) {
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		super.update(deltaTime);
		spriteBatch.end();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Renderable renderable = renderableComponentMapper.get(entity);
		Position position = positionComponentMapper.get(entity);
		renderable.polygonSprite.setPosition(position.x, position.y);
		renderable.polygonSprite.draw(spriteBatch);
	}

}
