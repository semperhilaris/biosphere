package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.steer.SteeringBehavior;

/**
 * Steering Component
 */
public class Steering implements Component {

	public SteeringBehavior behavior;

	public Steering(SteeringBehavior behavior) {
		this.behavior = behavior;
	}

}
