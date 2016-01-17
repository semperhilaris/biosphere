package com.semperhilaris.engine.systems.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.semperhilaris.engine.GameConfig;
import com.semperhilaris.engine.systems.StageSystem;

/**
 * FPS Debug Info
 */
public class FPSCounterRenderingSystem extends StageSystem {

	private Camera camera;
	private Batch spriteBatch;
	private Label fpsLabel;

	public FPSCounterRenderingSystem(Camera camera, Viewport viewport, Batch spriteBatch) {
		super(viewport, spriteBatch);

		this.camera = camera;
		this.spriteBatch = spriteBatch;

		fpsLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		Table table = new Table();
		table.setDebug(GameConfig.getBoolean("DEBUG"));
		table.setFillParent(true);

		table.add(fpsLabel).expand().bottom().left();

		stage.addActor(table);
	}

	@Override
	public void update(float deltaTime) {
		camera.position.set(0,0,0);
		spriteBatch.setProjectionMatrix(camera.combined);
		fpsLabel.setText("FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()));
		super.update(deltaTime);
	}

	@Override
	public boolean checkProcessing() {
		return super.checkProcessing() && GameConfig.getBoolean("DEBUG");
	}

}
