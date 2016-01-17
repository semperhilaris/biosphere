package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 * DestroyTarget Component
 */
public class DestroyTarget implements Component {

	public float radius;

	public DestroyTarget(float radius) {
		this.radius = radius;
	}

}
