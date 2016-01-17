package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 * AbsorbEnergyTarget Component
 */
public class AbsorbEnergyTarget implements Component {

	public float radius;

	public AbsorbEnergyTarget(float radius) {
		this.radius = radius;
	}

}
