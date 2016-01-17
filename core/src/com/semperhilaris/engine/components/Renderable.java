package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;

/**
 *
 */
public class Renderable implements Component {

	public PolygonSprite polygonSprite;
	public PolygonRegion polygonRegion;

	public Renderable(PolygonRegion polygonRegion) {
		this.polygonRegion = polygonRegion;
		this.polygonSprite = new PolygonSprite(polygonRegion);
	}

}
