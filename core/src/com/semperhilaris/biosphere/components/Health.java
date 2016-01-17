package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 * Health Component
 */
public class Health implements Component {

	public int value;
	public int max;

	public Health(int value, int max) {
		this.value = value;
		this.max = max;
	}

	public Health(int value) {
		this(value, value);
	}

}
