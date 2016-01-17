package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;
import com.esotericsoftware.spine.Bone;

/**
 * SpineBone Component
 */
public class SpineBone implements Component {

	public Bone bone;

	public SpineBone(Bone bone) {
		this.bone = bone;
	}

}
