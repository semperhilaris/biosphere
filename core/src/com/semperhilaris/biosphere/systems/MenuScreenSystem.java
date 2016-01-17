package com.semperhilaris.biosphere.systems;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.semperhilaris.engine.events.ScreenChangeEvent;
import com.semperhilaris.engine.systems.StageSystem;

/**
 * Menu Screen System
 */
public class MenuScreenSystem extends StageSystem implements LazyLoader {

	private boolean loaded;

	public MenuScreenSystem(Viewport viewport, Batch batch) {
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
		Image background = new Image(Assets.getTextureRegion("menu/background"));
		background.setWidth(stage.getWidth());
		background.setHeight(stage.getHeight());

		Image logo = new Image(Assets.getTextureRegion("menu/logo"));
		Image version = new Image(Assets.getTextureRegion("menu/version"));
		Image credits = new Image(Assets.getTextureRegion("menu/credits"));

		ImageButton playButton = new ImageButton(Assets.getDrawable("menu/btn_play"), Assets.getDrawable("menu/btn_play_down"));
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Events.post(new ScreenChangeEvent("GAME"));
			}
		});

		ImageButton howToPlayButton = new ImageButton(Assets.getDrawable("menu/btn_howtoplay"), Assets.getDrawable("menu/btn_howtoplay_down"));
		howToPlayButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Events.post(new ScreenChangeEvent("TUTORIAL"));
			}
		});

		VerticalGroup buttonGroup = new VerticalGroup();
		buttonGroup.addActor(playButton);
		buttonGroup.addActor(howToPlayButton);
		buttonGroup.space(10);
		buttonGroup.pad(20);

		Table table = new Table();
		table.setDebug(GameConfig.getBoolean("DEBUG"));
		table.setFillParent(true);

		table.add(version).expand().top().right().padRight(15).padTop(10);
		table.row();
		table.add(logo);
		table.row();
		table.add(buttonGroup).padBottom(50);
		table.row();
		table.add(credits).padBottom(30);

		stage.addActor(background);
		stage.addActor(table);
	}
}
