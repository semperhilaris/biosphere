package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 * HumansCapacity Component
 */
public class HumansCapacity implements Component {

	public int value;

	public HumansCapacity(int value) {
		this.value = value;
	}

	public HumansCapacity() {
		this(0);
	}

}
