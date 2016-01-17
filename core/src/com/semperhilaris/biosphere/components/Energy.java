package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 *
 */
public class Energy implements Component {

	public int value;

	public Energy(int value) {
		this.value = value;
	}

	public Energy() {
		this(0);
	}

}
