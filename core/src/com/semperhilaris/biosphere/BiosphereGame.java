package com.semperhilaris.biosphere;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.semperhilaris.biosphere.screens.*;
import com.semperhilaris.biosphere.systems.*;
import com.semperhilaris.engine.AbstractGame;
import com.semperhilaris.engine.Events;
import com.semperhilaris.engine.GameConfig;
import com.semperhilaris.engine.events.ScreenChangeEvent;
import com.semperhilaris.engine.systems.ContactListenerSystem;
import com.semperhilaris.engine.systems.Physics2DSystem;
import com.semperhilaris.engine.systems.SpineAnimationSystem;
import com.semperhilaris.engine.systems.SpineBoneTransformSystem;
import com.semperhilaris.engine.systems.ai.Physics2DSteeringSystem;
import com.semperhilaris.engine.systems.ai.SteeringLocationSystem;
import com.semperhilaris.engine.systems.camera.PointOfInterestCameraSystem;
import com.semperhilaris.engine.systems.debug.FPSCounterRenderingSystem;
import com.semperhilaris.engine.systems.debug.Physics2DDebugRenderingSystem;
import com.semperhilaris.engine.systems.debug.PointOfInterestDebugRenderingSystem;
import com.semperhilaris.engine.systems.debug.SpineDebugRenderingSystem;
import com.semperhilaris.engine.systems.input.MoveToSystem;
import com.semperhilaris.engine.systems.render.SpineRenderingSystem;

/**
 * BIOSPHERE
 *
 * 2D space simulation game
 *
 * @author David Fröhlich
 */
public class BiosphereGame extends AbstractGame {

	private World physicsWorld;
	private OrthographicCamera uiCamera;
	private OrthographicCamera worldCamera;
	private PolygonSpriteBatch spriteBatch;
	private Viewport uiViewport;
	private Viewport worldViewport;

	@Override
	public void create () {
		super.create();

		// Create the physics world
		physicsWorld = new World(new Vector2(0,0), true);

		// Create the batches
		spriteBatch = new PolygonSpriteBatch();

		// Create the cameras
		uiCamera = new OrthographicCamera();
		worldCamera = new OrthographicCamera();

		// Create the viewports
		uiViewport = new ExtendViewport(
				GameConfig.getFloat("VIEWPORT_MIN_WIDTH"),
				GameConfig.getFloat("VIEWPORT_MIN_HEIGHT"),
				GameConfig.getFloat("VIEWPORT_MAX_WIDTH"),
				GameConfig.getFloat("VIEWPORT_MAX_HEIGHT"));
		worldViewport = new ExtendViewport(
				GameConfig.getFloat("VIEWPORT_MIN_WIDTH"),
				GameConfig.getFloat("VIEWPORT_MIN_HEIGHT"),
				GameConfig.getFloat("VIEWPORT_MAX_WIDTH"),
				GameConfig.getFloat("VIEWPORT_MAX_HEIGHT"),
				worldCamera);

		// Create the systems
		addSystem(new SpineAnimationSystem());
		addSystem(new SpineBoneTransformSystem());
		addSystem(new Physics2DSystem(physicsWorld));
		addSystem(new ContactListenerSystem(physicsWorld));
		addSystem(new LoadingScreenSystem(uiViewport, spriteBatch));
		addSystem(new SpaceBackgroundSystem(uiViewport, spriteBatch));
		addSystem(new MenuScreenSystem(uiViewport, spriteBatch));
		addSystem(new HowToPlayScreenSystem(uiViewport, spriteBatch));
		addSystem(new ScoreScreenSystem(uiViewport, spriteBatch));
		addSystem(new PointOfInterestCameraSystem(worldCamera, 5f));
		addSystem(new Physics2DSteeringSystem());
		addSystem(new AbductHumansRenderingSystem(worldCamera, spriteBatch));
		addSystem(new GiveEnergyRenderingSystem(worldCamera, spriteBatch));
		addSystem(new DestroyRenderingSystem(worldCamera, spriteBatch));
		addSystem(new SpineRenderingSystem(worldCamera, spriteBatch));
		addSystem(new SettlementInfoSystem(worldCamera, spriteBatch));
		addSystem(new SettlementEvolutionSystem(5f));
		addSystem(new HumansSpawnSystem(45f));
		addSystem(new AsteroidSpawnSystem(10f));
		addSystem(new AsteroidDepletedSystem(10f));
		addSystem(new SettlementDestroyedSystem());
		addSystem(new AsteroidDestroyedSystem());
		addSystem(new AsteroidEnergyCollectionSystem());
		addSystem(new SteeringLocationSystem());
		addSystem(new GiveEnergySystem(worldViewport, 0.5f));
		addSystem(new AbductHumansSystem(worldViewport, 0.5f));
		addSystem(new DestroySystem(worldViewport, 0.5f));
		addSystem(new HUDSystem(uiViewport, spriteBatch));
		addSystem(new MoveToSystem(worldViewport));
		addSystem(new MissileAccelerationSystem());
		addSystem(new MissileStateSystem());
		addSystem(new MissileCollisionSystem());
		addSystem(new UngratefulBastardsSystem());
		addSystem(new TimerCountdownSystem());
		addSystem(new BackgroundMusicSystem());
		addSystem(new DeathSystem());
		addSystem(new ApocalypseSystem());
		addSystem(new EnergyGeneratorSystem(5));
		addSystem(new SelfDestructionSystem(5));
		addSystem(new MotionTiltSystem());
		addSystem(new ResourceChangeInfoRenderingSystem(worldCamera, spriteBatch));

		// Debug systems
		if (GameConfig.getBoolean("DEBUG")) {
			addSystem(new Physics2DDebugRenderingSystem(physicsWorld, worldCamera));
			addSystem(new PointOfInterestDebugRenderingSystem(worldCamera));
			addSystem(new SpineDebugRenderingSystem(worldCamera));
			addSystem(new FPSCounterRenderingSystem(uiCamera, uiViewport, spriteBatch));
		}

		// Create the screens
		addScreen("LOADING", new LoadingScreen(this));
		addScreen("MENU", new MenuScreen(this));
		addScreen("TUTORIAL", new HowToPlayScreen(this));
		addScreen("GAME", new GameScreen(this, physicsWorld));
		addScreen("SCORE", new ScoreScreen(this));

		// Set the initial screen
		Events.post(new ScreenChangeEvent("LOADING"));
	}

	@Override
	public void render () {
		// Update the cameras
		uiCamera.update();
		worldCamera.update();
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		// Update the viewports
		worldViewport.update(width, height);
		uiViewport.update(width, height);
	}

	@Override
	public void dispose() {
		super.dispose();
		spriteBatch.dispose();
		physicsWorld.dispose();
	}

}
