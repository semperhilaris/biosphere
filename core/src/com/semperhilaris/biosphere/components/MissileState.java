package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.utils.Location;

/**
 *
 */
public class MissileState implements Component {

	public float lifeTime;
	public boolean isSeekingTarget;

	public Location seekingTarget;

	public MissileState(Location seekingTarget) {
		this.seekingTarget = seekingTarget;
	}

}
