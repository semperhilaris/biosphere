package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 *
 */
public class EnergyRequirement implements Component {

	public int value;

	public EnergyRequirement(int value) {
		this.value = value;
	}

	public EnergyRequirement() {
		this(0);
	}
}
