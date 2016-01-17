package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 *
 */
public class Aggressor implements Component {

	public float radius;
	public float timeSinceLastFired;

	public Aggressor(float radius) {
		this.radius = radius;
	}

	public Aggressor() {
		this(0);
	}

}
