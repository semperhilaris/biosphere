package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;
import com.esotericsoftware.spine.Slot;

/**
 * SpineSlot Component
 */
public class SpineSlot implements Component {

	public Slot slot;

	public SpineSlot(Slot slot) {
		this.slot = slot;
	}

}
