package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;

/**
 * Position Component
 */
public class Position implements Component {

	public float x, y, z;

	public Position(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Position(float x, float y) {
		this(x, y, 0);
	}

	public Position() {
		this(0,0,0);
	}

	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public double distance(Position position) {
		return Math.sqrt((x - position.x) * (x - position.x) + (y - position.y) * (y - position.y));
	}

	public double distance(float x, float y) {
		return Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
	}

}
