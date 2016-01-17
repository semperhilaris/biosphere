package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;

/**
 * PointOfInterest Component
 */
public class PointOfInterest implements Component {

	public float outerRadius;
	public float innerRadius;
	public float zoom = 0f;

	public PointOfInterest(float outerRadius, float innerRadius, float zoom) {
		this.outerRadius = outerRadius;
		this.innerRadius = innerRadius;
		this.zoom = zoom;
	}
	public PointOfInterest(float outerRadius, float innerRadius) {
		this(outerRadius, innerRadius, 1f);
	}

	public float getRadiusDistance() {
		return outerRadius - innerRadius;
	}

}
