package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;
import com.semperhilaris.biosphere.entities.Settlement;

/**
 * SettlementReference Component
 */
public class SettlementReference implements Component {

	public Settlement settlement;

	public SettlementReference(Settlement settlement) {
		this.settlement = settlement;
	}

}
