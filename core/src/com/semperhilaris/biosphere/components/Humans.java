package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 * Humans Component
 */
public class Humans implements Component {

	public int value;

	public Humans(int value) {
		this.value = value;
	}

	public Humans() {
		this(0);
	}

}
