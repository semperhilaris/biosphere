package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.semperhilaris.biosphere.util.ResourceChangeEntry;

/**
 *
 */
public class ResourceChange implements Component {

	public Array<ResourceChangeEntry> entries = new Array<ResourceChangeEntry>();

	public void add(String label, Color color) {
		entries.add(new ResourceChangeEntry(label, color));
	}

}
