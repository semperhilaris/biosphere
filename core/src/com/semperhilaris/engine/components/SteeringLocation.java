package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;
import com.semperhilaris.engine.ai.steer.box2d.Box2dLocation;

/**
 * SteeringLocation Component
 */
public class SteeringLocation implements Component {

	public Box2dLocation box2dLocation;

	public SteeringLocation(Box2dLocation box2dLocation) {
		this.box2dLocation = box2dLocation;
	}

}
