package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/**
 * AbductHumans Component
 */
public class AbductHumans implements Component {

	public Entity target;

	public AbductHumans(Entity target) {
		this.target = target;
	}

	public AbductHumans() {
		this(null);
	}

}
