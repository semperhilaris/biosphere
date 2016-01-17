package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;

/**
 * Scale Component
 */
public class Scale implements Component {

	public float x, y;

	public Scale(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Scale(float xy) {
		this(xy, xy);
	}

	public Scale() {
		this(1, 1);
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void set(float xy) {
		this.x = xy;
		this.y = xy;
	}

}
