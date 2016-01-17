package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.semperhilaris.biosphere.components.Humans;
import com.semperhilaris.engine.Assets;
import com.semperhilaris.engine.Events;
import com.semperhilaris.engine.GameConfig;
import com.semperhilaris.engine.LazyLoader;
import com.semperhilaris.engine.components.GameState;
import com.semperhilaris.engine.events.ScreenChangeEvent;
import com.semperhilaris.engine.systems.StageSystem;

/**
 * Score Screen System
 */
public class ScoreScreenSystem extends StageSystem implements LazyLoader {

	private boolean loaded;

	private BitmapFont font;
	private Label scoreLabel;

	ComponentMapper<Humans> humansComponentMapper;

	public ScoreScreenSystem(Viewport viewport, Batch batch) {
		super(viewport, batch);

		humansComponentMapper = ComponentMapper.getFor(Humans.class);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenvector_future.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.size = 50;
		font = generator.generateFont(parameter);
	}

	@Override
	public void update(float deltaTime) {
		if (Assets.assetManager.getProgress() == 1 && !loaded) {
			loaded = true;
			afterLoad();
		}
		super.update(deltaTime);


		if (loaded && scoreLabel != null) {
			ImmutableArray<Entity> entities = getEngine().getEntitiesFor(
					Family.all(GameState.class, Humans.class).get());
			if (entities.size() > 0) {
				Humans humans = humansComponentMapper.get(entities.first());

				if (humans != null) {
					scoreLabel.setText(humans.value + "");
				}
			}
		}
	}

	@Override
	public void afterLoad() {
		Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
		scoreLabel = new Label("0", labelStyle);

		Image background = new Image(Assets.getTextureRegion("menu/background"));
		background.setWidth(stage.getWidth());
		background.setHeight(stage.getHeight());

		Image thanks_for_playing = new Image(Assets.getTextureRegion("menu/thanks_for_playing"));

		ImageButton restartButton = new ImageButton(Assets.getDrawable("menu/btn_restart"), Assets.getDrawable("menu/btn_restart_down"));
		restartButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Events.post(new ScreenChangeEvent("GAME"));
			}
		});

		VerticalGroup buttonGroup = new VerticalGroup();
		buttonGroup.addActor(restartButton);
		buttonGroup.space(10);
		buttonGroup.pad(20);

		Table table = new Table();
		table.setDebug(GameConfig.getBoolean("DEBUG"));
		table.setFillParent(true);

		table.padTop(100);
		table.add(thanks_for_playing);
		table.row();
		table.add(scoreLabel);
		table.row();
		table.add(buttonGroup).padBottom(150);

		stage.addActor(background);
		stage.addActor(table);
	}

}
