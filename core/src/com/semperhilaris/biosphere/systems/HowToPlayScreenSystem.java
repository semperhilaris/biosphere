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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
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
 * HowToPlay Screen System
 */
public class HowToPlayScreenSystem extends StageSystem implements LazyLoader {

	private boolean loaded;

	private Table table;
	private Array<Image> slides = new Array<Image>();
	private int slideIndex;

	public HowToPlayScreenSystem(Viewport viewport, Batch batch) {
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

		table = new Table();

		Image background = new Image(Assets.getTextureRegion("space"));
		background.setWidth(stage.getWidth());
		background.setHeight(stage.getHeight());

		for (int i = 1; i < 5; i++) {
			Image image = new Image(Assets.getTextureRegion("menu/tutorial_" +  i));
			slides.add(image);
		}

		ImageButton arrowLeft = new ImageButton(Assets.getDrawable("menu/arrow_left"));
		arrowLeft.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (slideIndex == 0) {
					Events.post(new ScreenChangeEvent("MENU"));
				} else {
					Image oldSlide = slides.get(slideIndex);
					Image newSlide = slides.get(slideIndex - 1);
					table.getCell(oldSlide).setActor(newSlide);
					slideIndex--;
				}
			}
		});

		ImageButton arrowRight = new ImageButton(Assets.getDrawable("menu/arrow_right"));
		arrowRight.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (slideIndex == slides.size - 1) {
					Events.post(new ScreenChangeEvent("MENU"));
				} else {
					Image oldSlide = slides.get(slideIndex);
					Image newSlide = slides.get(slideIndex + 1);
					table.getCell(oldSlide).setActor(newSlide);
					slideIndex++;
				}
			}
		});

		table.setDebug(GameConfig.getBoolean("DEBUG"));
		table.setFillParent(true);

		table.add(arrowLeft).pad(10);
		table.add(slides.first()).expand();
		table.add(arrowRight).pad(10);

		stage.addActor(background);
		stage.addActor(table);
	}

}
