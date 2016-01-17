package com.semperhilaris.biosphere.systems;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.semperhilaris.engine.Assets;
import com.semperhilaris.engine.Events;
import com.semperhilaris.engine.GameConfig;
import com.semperhilaris.engine.LazyLoader;
import com.semperhilaris.engine.events.CursorChangeEvent;
import com.semperhilaris.engine.events.ScreenChangeEvent;
import com.semperhilaris.engine.systems.StageSystem;

/**
 * Space Background System
 */
public class SpaceBackgroundSystem extends StageSystem implements LazyLoader {

	private InputListener hoverCursorListener;
	private boolean loaded;

	public SpaceBackgroundSystem(Viewport viewport, Batch batch) {
		super(viewport, batch);
	}

	@Override
	public void update(float deltaTime) {
		if (Assets.assetManager.getProgress() == 1 && !loaded) {
			loaded = true;
			afterLoad();
		}
		super.update(deltaTime);
	}

	@Override
	public void afterLoad() {
		Image background = new Image(Assets.getTextureRegion("space"));
		background.setWidth(stage.getWidth());
		background.setHeight(stage.getHeight());
		stage.addActor(background);
	}
}
