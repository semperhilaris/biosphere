package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 *
 */
public class SelfDestruction implements Component {

	public int amountMin;
	public int amountMax;

	public SelfDestruction(int amountMin, int amountMax) {
		this.amountMin = amountMin;
		this.amountMax = amountMax;
	}

	public SelfDestruction(int amount) {
		this(amount, amount);
	}

}
