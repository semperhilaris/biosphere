package com.semperhilaris.engine.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.semperhilaris.engine.Log;
import com.semperhilaris.engine.components.Layer;
import com.semperhilaris.engine.components.Position;

import java.util.Comparator;

/**
 * Comparator used to sort renderable entities. First by Layer, then by Position.
 */
public class RenderOrderComparator implements Comparator<Entity> {

	ComponentMapper<Position> positionComponentMapper;
	ComponentMapper<Layer> layerComponentMapper;

	public RenderOrderComparator() {
		positionComponentMapper = ComponentMapper.getFor(Position.class);
		layerComponentMapper = ComponentMapper.getFor(Layer.class);
	}

	@Override
	public int compare(Entity e1, Entity e2) {
		Layer layer1 = layerComponentMapper.get(e1);
		Layer layer2 = layerComponentMapper.get(e2);

		Position position1 = positionComponentMapper.get(e1);
		Position position2 = positionComponentMapper.get(e2);

		int layerIndex1 = layer1 != null ? layer1.index : 0;
		int layerIndex2 = layer2 != null ? layer2.index : 0;

		float positionZ1 = position1 != null ? position1.z : 0;
		float positionZ2 = position2 != null ? position2.z : 0;

		if (layerIndex1 != layerIndex2) {
			return (int)Math.signum(layerIndex1 - layerIndex2);
		} else {
			return (int)Math.signum(positionZ1 - positionZ2);
		}
	}

}