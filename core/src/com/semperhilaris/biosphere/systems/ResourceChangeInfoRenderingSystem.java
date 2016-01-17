package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.semperhilaris.biosphere.components.ResourceChange;
import com.semperhilaris.biosphere.util.ResourceChangeEntry;
import com.semperhilaris.engine.components.Position;

/**
 *
 */
public class ResourceChangeInfoRenderingSystem extends IteratingSystem {

	private static final float lifeTime = 2f;
	private static final float moveX = 50f;
	private static final float moveY = 50f;

	private Camera camera;
	private Batch batch;
	private BitmapFont font;

	ComponentMapper<ResourceChange> resourceChangeComponentMapper;
	ComponentMapper<Position> positionComponentMapper;

	public ResourceChangeInfoRenderingSystem(Camera camera, Batch batch) {
		super(Family.all(ResourceChange.class, Position.class).get());

		resourceChangeComponentMapper = ComponentMapper.getFor(ResourceChange.class);
		positionComponentMapper = ComponentMapper.getFor(Position.class);

		this.camera = camera;
		this.batch = batch;

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenvector_future.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 20;

		font = generator.generateFont(parameter);
	}

	@Override
	public void update(float deltaTime) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		super.update(deltaTime);
		batch.end();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		ResourceChange resourceChange = resourceChangeComponentMapper.get(entity);
		Position position = positionComponentMapper.get(entity);

		for (int i = 0; i < resourceChange.entries.size; i++) {
			ResourceChangeEntry resourceChangeEntry = resourceChange.entries.get(i);

			resourceChangeEntry.age += deltaTime;
			float progress = resourceChangeEntry.age / lifeTime;

			if (progress >= 1) {
				resourceChange.entries.removeIndex(i);
			} else {
				font.setColor(resourceChangeEntry.color);
				font.getColor().a = 1 - progress;

				font.getData().setScale(1 + 2 * progress);

				font.draw(batch,
						resourceChangeEntry.label,
						position.x + moveX * progress,
						position.y + moveY * progress,
						50, Align.center, false);
			}
		}

	}

}
