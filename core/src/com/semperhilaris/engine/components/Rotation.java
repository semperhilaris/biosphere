package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;

/**
 *
 */
public class Rotation implements Component {

	public float x, y;

	public Rotation(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Rotation(float x) {
		this(x, 0);
	}

	public Rotation() {
		this(0, 0);
	}

}
