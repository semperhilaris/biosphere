package com.semperhilaris.engine.systems.ai;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.math.Vector2;
import com.semperhilaris.engine.components.Physics2D;
import com.semperhilaris.engine.components.Steering;

/**
 *
 */
public class Physics2DSteeringSystem extends IteratingSystem {

	ComponentMapper<Steering> steeringComponentMapper;
	ComponentMapper<Physics2D> physics2DComponentMapper;

	private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());


	public Physics2DSteeringSystem() {
		super(Family.all(Steering.class, Physics2D.class).get());

		steeringComponentMapper = ComponentMapper.getFor(Steering.class);
		physics2DComponentMapper = ComponentMapper.getFor(Physics2D.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Steering steering = steeringComponentMapper.get(entity);
		Physics2D physics2D = physics2DComponentMapper.get(entity);

		steering.behavior.calculateSteering(steeringOutput);

		applySteering(steeringOutput, physics2D, deltaTime);
	}

	protected void applySteering (SteeringAcceleration<Vector2> steering, Physics2D physics2D, float deltaTime) {
		boolean anyAccelerations = false;

		// Update position and linear velocity.
		if (!steering.linear.isZero()) {
			// this method internally scales the force by deltaTime
			physics2D.body.applyForceToCenter(steering.linear, true);
			anyAccelerations = true;
		}

		// Update orientation and angular velocity
		if (physics2D.independentFacing) {
			if (steering.angular != 0) {
				// this method internally scales the torque by deltaTime
				physics2D.body.applyTorque(steering.angular, true);
				anyAccelerations = true;
			}
		} else {
			// If we haven't got any velocity, then we can do nothing.
			Vector2 linVel = physics2D.getLinearVelocity();
			if (!linVel.isZero(physics2D.getZeroLinearSpeedThreshold())) {
				float newOrientation = physics2D.vectorToAngle(linVel);
				physics2D.body.setAngularVelocity((newOrientation - physics2D.getAngularVelocity()) * deltaTime); // this is superfluous if independentFacing is always true
				physics2D.body.setTransform(physics2D.body.getPosition(), newOrientation);
			}
		}

		if (anyAccelerations) {
			// body.activate();

			// Cap the linear speed
			Vector2 velocity = physics2D.body.getLinearVelocity();
			float currentSpeedSquare = velocity.len2();
			float maxLinearSpeed = physics2D.getMaxLinearSpeed();
			if (maxLinearSpeed > 0 && currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
				physics2D.body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float)Math.sqrt(currentSpeedSquare)));
			}

			// Cap the angular speed
			float maxAngVelocity = physics2D.getMaxAngularSpeed();
			if (maxAngVelocity > 0 && physics2D.body.getAngularVelocity() > maxAngVelocity) {
				physics2D.body.setAngularVelocity(maxAngVelocity);
			}
		}
	}


}
