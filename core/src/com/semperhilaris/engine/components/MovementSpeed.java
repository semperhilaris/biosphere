package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;

/**
 * MovementSpeed Component
 */
public class MovementSpeed implements Component {

	public float value;

	public MovementSpeed(float value) {
		this.value = value;
	}

}
