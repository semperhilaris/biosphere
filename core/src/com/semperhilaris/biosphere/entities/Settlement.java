package com.semperhilaris.biosphere.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Slot;
import com.semperhilaris.biosphere.components.*;
import com.semperhilaris.engine.GameConfig;
import com.semperhilaris.engine.components.*;

/**
 * Settlement Entity
 */
public class Settlement extends Entity {

	public Settlement(Bone bone, Slot slot) {
		super();

		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		fixtureDef.shape = new CircleShape();
		fixtureDef.shape.setRadius(150 * GameConfig.getFloat("PIXELS_TO_METERS"));
		fixtureDef.filter.groupIndex = 1;
		Physics2D physics2D = new Physics2D(bodyDef, fixtureDef);

		add(new SettlementFlag());
		add(new Position());
		add(new Rotation());
		add(physics2D);
		add(new SpineBone(bone));
		add(new SpineSlot(slot));
		add(new EvolutionStage());
		add(new Energy());
		add(new EnergyRequirement(100));
		add(new GiveEnergyTarget(100));
		add(new Humans(5));
		add(new HumansCapacity(10));
		add(new HumansSpawnRate(5));
		add(new AbductHumansTarget(100));
		add(new DestroyTarget(100));
		add(new Health(100));
		add(new PointOfInterest(600, 200, -4));
		add(new Threat());
		add(new EnergyGenerator(1, 15));
		add(new SelfDestruction(1, 10));
		add(new ResourceChange());
	}

}
