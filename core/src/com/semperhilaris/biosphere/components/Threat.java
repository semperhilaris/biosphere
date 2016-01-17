package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 *
 */
public class Threat implements Component {

	public int priority;

	public Threat(int priority) {
		this.priority = priority;
	}

	public Threat() {
		this(0);
	}

}
