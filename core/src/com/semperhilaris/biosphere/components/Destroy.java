package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/**
 *
 */
public class Destroy implements Component {

	public Entity target;

	public Destroy(Entity target) {
		this.target = target;
	}

	public Destroy() {
		this(null);
	}

}
