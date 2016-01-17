package com.semperhilaris.engine;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.ObjectMap;
import com.semperhilaris.engine.events.ScreenChangeEvent;
import net.engio.mbassy.listener.Handler;

/**
 * Abstract Game
 *
 * Base class for a game.
 * Holds the Entity Component System engine, manages Screens and input events.
 */
public abstract class AbstractGame extends ApplicationAdapter {

	// Entity Component System
	public PooledEngine engine = new PooledEngine();

	// Input
	public InputMultiplexer inputMultiplexer = new InputMultiplexer();

	// Screens
	public ObjectMap<String, AbstractScreen> screens = new ObjectMap<String, AbstractScreen>();
	public AbstractScreen currentScreen;

	// TweenManager
	public TweenManager tweenManager;

	@Override
	public void create() {
		// Create the Entity Component System Engine
		engine = new PooledEngine();

		// Subscribe to the EventBus
		Events.subscribe(this);

		// Load all Assets
		Assets.load();

		// Set the InputMultiplexer
		Gdx.input.setInputProcessor(inputMultiplexer);

		// Create the TweenManager
		tweenManager = new TweenManager();
	}

	@Override
	public void render () {
		// Update TweenManager
		tweenManager.update(Gdx.graphics.getDeltaTime());

		// Update the current Screen
		currentScreen.render();

		// Clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Update Entity Component System
		engine.update(Gdx.graphics.getDeltaTime());

		// Load assets
		Assets.assetManager.update();
	}

	@Override
	public void dispose() {
		Assets.assetManager.dispose();
	}

	public void addScreen(String name, AbstractScreen screen) {
		screens.put(name, screen);
	}

	public void addSystem(EntitySystem system, int priority) {
		system.priority = priority;
		system.setProcessing(false);
		engine.addSystem(system);
		if (system instanceof InputProcessor) {
			inputMultiplexer.addProcessor((InputProcessor)system);
		} else if (system instanceof GestureDetector.GestureListener) {
			inputMultiplexer.addProcessor(new GestureDetector((GestureDetector.GestureListener)system));
		}
	}

	public void addSystem(EntitySystem system) {
		addSystem(system, 0);
	}

	@Handler
	public void handleScreenChange(ScreenChangeEvent screenChangeEvent) {
		Log.msg("Screen Change", screenChangeEvent.screenName);
		if (currentScreen != null) {
			currentScreen.hide();
		}
		AbstractScreen screen = screens.get(screenChangeEvent.screenName);
		if (screen != null) {
			screen.show();
			currentScreen = screen;
		} else {
			Log.msg("Screen Change", "Screen not found", Log.ERROR);
		}
	}

}
