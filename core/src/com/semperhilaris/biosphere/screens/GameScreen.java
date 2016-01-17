package com.semperhilaris.biosphere.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Quad;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.Skeleton;
import com.semperhilaris.biosphere.BiosphereGame;
import com.semperhilaris.biosphere.components.*;
import com.semperhilaris.biosphere.entities.*;
import com.semperhilaris.biosphere.events.AlienSwitchEvent;
import com.semperhilaris.biosphere.systems.*;
import com.semperhilaris.engine.*;
import com.semperhilaris.engine.ai.steer.box2d.Box2dLocation;
import com.semperhilaris.engine.components.*;
import com.semperhilaris.engine.events.CursorChangeEvent;
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
import com.semperhilaris.engine.tweens.ScaleAccessor;
import net.engio.mbassy.listener.Handler;

/**
 * Game screen
 */
public class GameScreen extends AbstractScreen {

	private boolean isLoaded;

	Alien alien_G, alien_O, alien_D;
	Entity gameState, moveToTarget;

	GiveEnergy giveEnergy;
	AbductHumans abductHumans;
	Destroy destroy;

	World world;

	public GameScreen(AbstractGame game, World world) {
		super(game);

		this.world = world;

		enableSystems(
				SpaceBackgroundSystem.class,
				SpineAnimationSystem.class,
				SpineBoneTransformSystem.class,
				Physics2DSystem.class,
				ContactListenerSystem.class,
				MoveToSystem.class,
				SteeringLocationSystem.class,
				GiveEnergySystem.class,
				AbductHumansSystem.class,
				DestroySystem.class,
				PointOfInterestCameraSystem.class,
				Physics2DSteeringSystem.class,
				AbductHumansRenderingSystem.class,
				GiveEnergyRenderingSystem.class,
				DestroyRenderingSystem.class,
				SpineRenderingSystem.class,
				SettlementInfoSystem.class,
				SettlementEvolutionSystem.class,
				SettlementDestroyedSystem.class,
				HumansSpawnSystem.class,
				AsteroidSpawnSystem.class,
				AsteroidDepletedSystem.class,
				AsteroidDestroyedSystem.class,
				AsteroidEnergyCollectionSystem.class,
				MissileAccelerationSystem.class,
				MissileStateSystem.class,
				MissileCollisionSystem.class,
				UngratefulBastardsSystem.class,
				TimerCountdownSystem.class,
				HUDSystem.class,
				BackgroundMusicSystem.class,
				DeathSystem.class,
				ApocalypseSystem.class,
				EnergyGeneratorSystem.class,
				SelfDestructionSystem.class,
				MotionTiltSystem.class,
				ResourceChangeInfoRenderingSystem.class);

		// Debug Systems
		enableSystems(
				SpineDebugRenderingSystem.class,
				Physics2DDebugRenderingSystem.class,
				PointOfInterestDebugRenderingSystem.class,
				FPSCounterRenderingSystem.class);

		// Register TweenAccessors
		Tween.registerAccessor(Scale.class, new ScaleAccessor());

		// Switch to default cursor
		Events.post(new CursorChangeEvent("DEFAULT"));
	}

	@Override
	protected void show() {
		super.show();
		if (isLoaded) {
			game.engine.removeAllEntities();
			game.engine.clearPools();

			Array<Body> bodies = new Array<Body>();
			world.getBodies(bodies);
			for (Body body : bodies) {
				world.destroyBody(body);
			}

			initialize();
		}
	}

	@Override
	public void afterLoad() {
		initialize();
		isLoaded = true;
	}

	public void initialize() {
		//Create the GameState
		gameState = new Entity();
		gameState
				.add(new GameState())
				.add(new Health(100))
				.add(new Humans(0))
				.add(new Energy(150))
				.add(new Timer(300));
		game.engine.addEntity(gameState);

		// Create the MoveToTarget
		moveToTarget = new Entity();
		moveToTarget
				.add(new MoveToTarget())
				.add(new Position(0, 800))
				.add(new SteeringLocation(new Box2dLocation()));
		game.engine.addEntity(moveToTarget);

		// Create the aliens
		alien_G = new Alien("alien_g", 100, 800);
		alien_O = new Alien("alien_o", 0, 800);
		alien_D = new Alien("alien_d", -100, 800);
		game.engine.addEntity(alien_G);
		game.engine.addEntity(alien_O);
		game.engine.addEntity(alien_D);

		// Give special properties to the aliens
		giveEnergy = new GiveEnergy();
		abductHumans = new AbductHumans();
		destroy = new Destroy();

		alien_G.add(giveEnergy);
		alien_O.add(abductHumans);
		alien_D.add(destroy);

		// Create the asteroid spawn points
		float[] asteroidSpawnPositions = new float[] {
				0, 1200,
				0, -1200,
				1200, 0,
				-1200, 0,

				1500, 1500,
				700, 1000,
				500, 900,

				-1500, 1500,
				-700, 1000,
				-500, 900,

				1500, -1500,
				700, -1000,
				500, -900,

				-1500, -1500,
				-700, -1000,
				-500, -900,
		};
		for (int i=0; i<asteroidSpawnPositions.length-1; i+=2) {
			Entity asteroidSpawn = new Entity();
			asteroidSpawn.add(new AsteroidSpawn());
			asteroidSpawn.add(new Position(asteroidSpawnPositions[i], asteroidSpawnPositions[i+1]));
			game.engine.addEntity(asteroidSpawn);
		}

		// Create the planet
		Planet planet = new Planet(0, 0);
		game.engine.addEntity(planet);

		// Create the settlements on the planet
		for (int i=1; i <= 8; i++) {
			Skeleton skeleton = planet.getComponent(Spine.class).skeleton;
			Settlement settlement = new Settlement(
					skeleton.findBone("ground"+i),
					skeleton.findSlot("attachment"+i));
			game.engine.addEntity(settlement);

			Entity settlementInfo = new Entity();
			settlementInfo.add(new SettlementInfoFlag());
			settlementInfo.add(new SpineBone(skeleton.findBone("info"+i)));
			settlementInfo.add(new Position());
			settlementInfo.add(new SettlementReference(settlement));
			game.engine.addEntity(settlementInfo);

			Entity missileLaunchTarget = new Entity();
			missileLaunchTarget.add(new MissileLaunchTargetFlag());
			missileLaunchTarget.add(new SpineBone(skeleton.findBone("missiletarget"+i)));
			missileLaunchTarget.add(new Position());
			missileLaunchTarget.add(new Rotation());
			missileLaunchTarget.add(new SteeringLocation(new Box2dLocation()));
			missileLaunchTarget.add(new SettlementReference(settlement));
			game.engine.addEntity(missileLaunchTarget);

		}

		// Create the world bounds
		float boundSize = 2500;
		WorldBounds worldBoundsTop = new WorldBounds(0, boundSize, boundSize * GameConfig.getFloat("PIXELS_TO_METERS"), 1);
		WorldBounds worldBoundsRight = new WorldBounds(boundSize, 0, 1, boundSize * GameConfig.getFloat("PIXELS_TO_METERS"));
		WorldBounds worldBoundsBottom = new WorldBounds(0, -boundSize, boundSize * GameConfig.getFloat("PIXELS_TO_METERS"), 1);
		WorldBounds worldBoundsLeft = new WorldBounds(-boundSize, 0, 1, boundSize * GameConfig.getFloat("PIXELS_TO_METERS"));
		game.engine.addEntity(worldBoundsTop);
		game.engine.addEntity(worldBoundsRight);
		game.engine.addEntity(worldBoundsBottom);
		game.engine.addEntity(worldBoundsLeft);

		// Set the initially active alien
		Events.post(new AlienSwitchEvent("O"));
	}

	@Handler
	public void handleAlienSwitch(AlienSwitchEvent alienSwitchEvent) {

		Log.msg("Switching active alien", alienSwitchEvent.name);

		giveEnergy.target = null;
		abductHumans.target = null;
		destroy.target = null;

		Alien activeAlien, inactiveAlien1, inactiveAlien2;

		if (alienSwitchEvent.name.equals("G")) {
			activeAlien = alien_G;
			inactiveAlien1 = alien_O;
			inactiveAlien2 = alien_D;
		} else if (alienSwitchEvent.name.equals("O")) {
			activeAlien = alien_O;
			inactiveAlien1 = alien_G;
			inactiveAlien2 = alien_D;
		} else {
			activeAlien = alien_D;
			inactiveAlien1 = alien_G;
			inactiveAlien2 = alien_O;
		}

		// Remove PlayerControl and CameraFocus from all aliens, then give it to the active one.
		alien_G.remove(PlayerControl.class);
		alien_O.remove(PlayerControl.class);
		alien_D.remove(PlayerControl.class);
		alien_G.remove(CameraFocus.class);
		alien_O.remove(CameraFocus.class);
		alien_D.remove(CameraFocus.class);
		activeAlien.add(new PlayerControl());
		activeAlien.add(new CameraFocus());

		// Set the Layer of the active alien to 0 and set the others to 1.
		activeAlien.getComponent(Layer.class).index = 0;
		inactiveAlien1.getComponent(Layer.class).index = -1;
		inactiveAlien2.getComponent(Layer.class).index = -1;
		SpineRenderingSystem spineRenderingSystem = game.engine.getSystem(SpineRenderingSystem.class);
		if (spineRenderingSystem != null) {
			spineRenderingSystem.forceSort();
		}

		// Set the Scale of the active alien to 1 and set the others to 0.5 using Tweens.
		Tween.to(activeAlien.getComponent(Scale.class), ScaleAccessor.TWEEN_XY, 1.0f)
				.target(1f).ease(Quad.INOUT).start(game.tweenManager);
		Tween.to(inactiveAlien1.getComponent(Scale.class), ScaleAccessor.TWEEN_XY, 1.0f)
				.target(0.5f).ease(Quad.INOUT).start(game.tweenManager);
		Tween.to(inactiveAlien2.getComponent(Scale.class), ScaleAccessor.TWEEN_XY, 1.0f)
				.target(0.5f).ease(Quad.INOUT).start(game.tweenManager);

		// Distribute new AI for all three aliens. The active one must seek the MoveToTarget, the others should follow.
		Physics2D activeAlienPhysics = activeAlien.getComponent(Physics2D.class);
		Physics2D inactiveAlien1Physics = inactiveAlien1.getComponent(Physics2D.class);
		Physics2D inactiveAlien2Physics = inactiveAlien2.getComponent(Physics2D.class);
		SteeringLocation moveToTargetLocation = moveToTarget.getComponent(SteeringLocation.class);

		alien_G.remove(Steering.class);
		alien_O.remove(Steering.class);
		alien_D.remove(Steering.class);

		activeAlien
				.add(new Steering(
						new BlendedSteering(activeAlienPhysics)
								.add(new Arrive(activeAlienPhysics, moveToTargetLocation.box2dLocation)
										.setDecelerationRadius(10f)
										.setArrivalTolerance(1f), 0.9f)));

		inactiveAlien1
				.add(new Steering(
						new BlendedSteering(inactiveAlien1Physics)
								.add(new Arrive(inactiveAlien1Physics, activeAlienPhysics)
										.setDecelerationRadius(10f)
										.setArrivalTolerance(1f), 0.9f)
								.add(new Flee(inactiveAlien1Physics, inactiveAlien2Physics), 0.1f)));

		inactiveAlien2
				.add(new Steering(
						new BlendedSteering(inactiveAlien2Physics)
								.add(new Arrive(inactiveAlien2Physics, activeAlienPhysics)
										.setDecelerationRadius(10f)
										.setArrivalTolerance(1f), 0.9f)
								.add(new Flee(inactiveAlien2Physics, inactiveAlien1Physics), 0.1f)));

	}

}
