package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 * EnergyTarget Component
 */
public class GiveEnergyTarget implements Component {

	public float radius;

	public GiveEnergyTarget(float radius) {
		this.radius = radius;
	}

}
