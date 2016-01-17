package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 *
 */
public class HumansSpawnRate implements Component {

	public int value;

	public HumansSpawnRate(int value) {
		this.value = value;
	}

	public HumansSpawnRate() {
		this(0);
	}

}
