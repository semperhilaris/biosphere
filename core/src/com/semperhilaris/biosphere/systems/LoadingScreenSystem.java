package com.semperhilaris.biosphere.systems;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.semperhilaris.engine.Assets;
import com.semperhilaris.engine.Events;
import com.semperhilaris.engine.GameConfig;
import com.semperhilaris.engine.Log;
import com.semperhilaris.engine.events.ScreenChangeEvent;
import com.semperhilaris.engine.systems.StageSystem;

/**
 * Loading Screen System
 */
public class LoadingScreenSystem extends StageSystem {

	private ProgressBar progressBar;

	public LoadingScreenSystem(Viewport viewport, Batch batch) {
		super(viewport, batch);

		Image logo = new Image(Assets.getDrawable("logo", "bootstrap"));

		ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
		progressBarStyle.background = Assets.getDrawable("loading_empty", "bootstrap");
		progressBarStyle.knobBefore = Assets.getNinePatchDrawable("loading_full_patch", "bootstrap");

		progressBar = new ProgressBar(0, 100, 1, false, progressBarStyle);

		Table table = new Table();
		table.setDebug(GameConfig.getBoolean("DEBUG"));
		table.background(Assets.getDrawable("background", "bootstrap"));
		table.setFillParent(true);

		table.add(logo);
		table.row().padTop(20);
		table.add(progressBar).fillX();

		stage.addActor(table);
	}

	@Override
	public void update(float deltaTime) {
		float progress = Assets.assetManager.getProgress() * 100 - progressBar.getValue();
		float maxProgress = 1f;
		if (progress > maxProgress && !GameConfig.getBoolean("DEBUG")) {
			progress = maxProgress;
		}
		Log.msg("Loading Progress", progressBar.getValue() + "");
		progressBar.setValue(progressBar.getValue() + progress);
		if (progressBar.getValue() >= 100) {
			Events.post(new ScreenChangeEvent("MENU"));
		}
		super.update(deltaTime);
	}

}
