package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/**
 * GiveEnergy Component
 */
public class GiveEnergy implements Component {

	public Entity target;

	public GiveEnergy(Entity target) {
		this.target = target;
	}

	public GiveEnergy() {
		this(null);
	}

}
