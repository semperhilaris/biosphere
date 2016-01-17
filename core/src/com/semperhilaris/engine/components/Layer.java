package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;

/**
 * Layer Component
 */
public class Layer implements Component {

	public int index;

	public Layer(int index) {
		this.index = index;
	}

	public Layer() {
		this(0);
	}

}
