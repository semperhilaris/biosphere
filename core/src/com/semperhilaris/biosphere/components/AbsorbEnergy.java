package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/**
 *
 */
public class AbsorbEnergy implements Component {

	public Entity target;

	public AbsorbEnergy(Entity target) {
		this.target = target;
	}

	public AbsorbEnergy() {
		this(null);
	}

}
