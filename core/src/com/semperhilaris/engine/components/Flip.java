package com.semperhilaris.engine.components;


import com.badlogic.ashley.core.Component;

/**
 * Flip Component
 */
public class Flip implements Component {

	public boolean x, y;

	public Flip(boolean x, boolean y) {
		this.x = x;
		this.y = y;
	}

	public Flip(boolean xy) {
		this(xy, xy);
	}

}
