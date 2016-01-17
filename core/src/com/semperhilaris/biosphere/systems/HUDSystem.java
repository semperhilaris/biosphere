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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.semperhilaris.biosphere.components.Energy;
import com.semperhilaris.biosphere.components.Health;
import com.semperhilaris.biosphere.components.Humans;
import com.semperhilaris.biosphere.components.Timer;
import com.semperhilaris.biosphere.events.AlienSwitchEvent;
import com.semperhilaris.biosphere.util.ColorScheme;
import com.semperhilaris.engine.Assets;
import com.semperhilaris.engine.Events;
import com.semperhilaris.engine.GameConfig;
import com.semperhilaris.engine.LazyLoader;
import com.semperhilaris.engine.components.GameState;
import com.semperhilaris.engine.systems.StageSystem;

/**
 * HUD System
 */
public class HUDSystem extends StageSystem implements LazyLoader {

	private boolean loaded;
	private BitmapFont giantFont;
	private BitmapFont largeFont;
	private BitmapFont smallFont;
	private Label energyValueLabel;
	private Label humansValueLabel;
	private Label healthValueLabel;
	private Label timeLabel;

	ComponentMapper<Energy> energyComponentMapper;
	ComponentMapper<Humans> humansComponentMapper;
	ComponentMapper<Health> healthComponentMapper;
	ComponentMapper<Timer> timerComponentMapper;

	public HUDSystem(Viewport viewport, Batch batch) {
		super(viewport, batch);

		energyComponentMapper = ComponentMapper.getFor(Energy.class);
		humansComponentMapper = ComponentMapper.getFor(Humans.class);
		healthComponentMapper = ComponentMapper.getFor(Health.class);
		timerComponentMapper = ComponentMapper.getFor(Timer.class);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenvector_future.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.size = 32;
		giantFont = generator.generateFont(parameter);

		parameter.size = 24;
		largeFont = generator.generateFont(parameter);

		parameter.size = 18;
		smallFont = generator.generateFont(parameter);
	}

	@Override
	public void update(float deltaTime) {
		if (Assets.assetManager.getProgress() == 1 && !loaded) {
			loaded = true;
			afterLoad();
		}
		super.update(deltaTime);

		if (loaded) {
			ImmutableArray<Entity> entities = getEngine().getEntitiesFor(
					Family.all(GameState.class).one(Energy.class, Humans.class, Health.class, Timer.class).get());
			if (entities.size() > 0) {
				Energy energy = energyComponentMapper.get(entities.first());
				Humans humans = humansComponentMapper.get(entities.first());
				Health health = healthComponentMapper.get(entities.first());
				Timer timer = timerComponentMapper.get(entities.first());

				if (energy != null) {
					energyValueLabel.setText(energy.value + "");
				}
				if (humans != null) {
					humansValueLabel.setText(humans.value + "");
				}
				if (health != null) {
					healthValueLabel.setText(health.value + "");
				}
				if (timer != null) {
					int [] timeComponents = splitToComponentTimes(timer.value);
					timeLabel.setText(String.format("%02d", timeComponents[1]) + ":" + String.format("%02d", timeComponents[2]));
				}
			}
		}
	}

	@Override
	public void afterLoad() {
		Label.LabelStyle giantLabelStyle = new Label.LabelStyle(giantFont, Color.WHITE);
		Label.LabelStyle largeLabelStyle = new Label.LabelStyle(largeFont, Color.WHITE);
		Label.LabelStyle smallLabelStyle = new Label.LabelStyle(smallFont, Color.WHITE);

		Label energyLabel = new Label("Energy: ", smallLabelStyle);
		energyValueLabel = new Label("0", largeLabelStyle);
		energyValueLabel.setColor(ColorScheme.ENERGY);

		Label humansLabel = new Label("Humans:", giantLabelStyle);
		humansValueLabel = new Label("0", giantLabelStyle);
		humansValueLabel.setColor(ColorScheme.HUMANS);

		Label healthLabel = new Label("Health:", smallLabelStyle);
		healthValueLabel = new Label("0", largeLabelStyle);
		healthValueLabel.setColor(ColorScheme.HEALTH);

		timeLabel = new Label("00:00", giantLabelStyle);

		Table scoreTable = new Table();
		scoreTable.add(humansLabel).left();
		scoreTable.add(humansValueLabel).right();
		scoreTable.row();
		scoreTable.add(energyLabel).left();
		scoreTable.add(energyValueLabel).right();
		scoreTable.row();
		scoreTable.add(healthLabel).left();
		scoreTable.add(healthValueLabel).right();

		scoreTable.pad(10);

		ImageButton portrait_G = new ImageButton(Assets.getDrawable("portrait_g"));
		ImageButton portrait_O = new ImageButton(Assets.getDrawable("portrait_o"));
		ImageButton portrait_D = new ImageButton(Assets.getDrawable("portrait_d"));

		portrait_G.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Events.post(new AlienSwitchEvent("G"));
			}
		});

		portrait_O.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Events.post(new AlienSwitchEvent("O"));
			}
		});

		portrait_D.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Events.post(new AlienSwitchEvent("D"));
			}
		});

		VerticalGroup portraits = new VerticalGroup();
		portraits.space(10);
		portraits.pad(10);
		portraits.addActor(portrait_O);
		portraits.addActor(portrait_G);
		portraits.addActor(portrait_D);

		Table table = new Table();
		table.setDebug(GameConfig.getBoolean("DEBUG"));
		table.setFillParent(true);
		table.left().top();
		table.add(timeLabel).pad(10).expand().left().top();
		table.right().top();
		table.add(scoreTable).expandY().top();
		table.row().colspan(2);
		table.right().bottom();
		table.add(portraits).right();

		stage.addActor(table);
	}

	public static int[] splitToComponentTimes(float time) {
		long longVal = (long) time;
		int hours = (int) longVal / 3600;
		int remainder = (int) longVal - hours * 3600;
		int mins = remainder / 60;
		remainder = remainder - mins * 60;
		int secs = remainder;

		return new int[] {hours , mins , secs};
	}

}
