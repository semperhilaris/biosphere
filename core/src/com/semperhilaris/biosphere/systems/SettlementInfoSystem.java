package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.semperhilaris.biosphere.components.*;
import com.semperhilaris.biosphere.util.ColorScheme;
import com.semperhilaris.engine.components.CameraFocus;
import com.semperhilaris.engine.components.Position;

/**
 * SettlementInfo System
 */
public class SettlementInfoSystem extends IteratingSystem {

	private Camera camera;
	private Batch batch;
	private BitmapFont font;

	ComponentMapper<SettlementReference> settlementReferenceComponentMapper;
	ComponentMapper<Position> positionComponentMapper;
	ComponentMapper<Energy> energyComponentMapper;
	ComponentMapper<EnergyRequirement> energyRequirementComponentMapper;
	ComponentMapper<Humans> humansComponentMapper;
	ComponentMapper<HumansCapacity> humansCapacityComponentMapper;
	ComponentMapper<Health> healthComponentMapper;

	public SettlementInfoSystem(Camera camera, Batch batch) {
		super(Family.all(SettlementInfoFlag.class, SettlementReference.class, Position.class).get());

		settlementReferenceComponentMapper = ComponentMapper.getFor(SettlementReference.class);
		positionComponentMapper = ComponentMapper.getFor(Position.class);
		energyComponentMapper = ComponentMapper.getFor(Energy.class);
		energyRequirementComponentMapper = ComponentMapper.getFor(EnergyRequirement.class);
		humansComponentMapper = ComponentMapper.getFor(Humans.class);
		humansCapacityComponentMapper = ComponentMapper.getFor(HumansCapacity.class);
		healthComponentMapper = ComponentMapper.getFor(Health.class);

		this.camera = camera;
		this.batch = batch;

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenvector_future.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 24;

		font = generator.generateFont(parameter);
	}

	@Override
	public void update(float deltaTime) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		super.update(deltaTime);
		batch.end();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		float alpha = 1;

		SettlementReference settlementReference = settlementReferenceComponentMapper.get(entity);
		Position position = positionComponentMapper.get(entity);

		font.setColor(Color.WHITE);
		ImmutableArray<Entity> entities = getEngine().getEntitiesFor(Family.all(CameraFocus.class).get());
		if (entities.size() != 0) {
			Position cameraPosition = positionComponentMapper.get(entities.get(0));
			double distance = cameraPosition.distance(position);
			float coeff = ((float)distance - 200) / 400;
			alpha = MathUtils.clamp(1 - coeff, 0, 1);
			font.getData().setScale(1 + coeff);
		}

		Humans humans = humansComponentMapper.get(settlementReference.settlement);
		HumansCapacity humansCapacity = humansCapacityComponentMapper.get(settlementReference.settlement);
		if (humans != null && humansCapacity != null) {
			font.setColor(ColorScheme.HUMANS);
			font.getColor().a = alpha;
			font.draw(batch,
					humans.value + " / " + humansCapacity.value,
					position.x - 100,
					position.y + 50,
					200, Align.center, false);
		}

		Energy energy = energyComponentMapper.get(settlementReference.settlement);
		EnergyRequirement energyRequirement = energyRequirementComponentMapper.get(settlementReference.settlement);
		if (energy != null && energyRequirement != null) {
			font.setColor(ColorScheme.ENERGY);
			font.getColor().a = alpha;
			font.draw(batch,
					//MathUtils.floor(energy.value / (energyRequirement.value / 100)) + " %",
					energy.value + " / " + energyRequirement.value,
					position.x - 100,
					position.y,
					200, Align.center, false);
		}

		Health health = healthComponentMapper.get(settlementReference.settlement);
		if (health != null) {
			font.setColor(ColorScheme.HEALTH);
			font.getColor().a = alpha;
			font.draw(batch,
					health.value + " / " + health.max,
					position.x - 100,
					position.y - 50,
					200, Align.center, false);
		}

	}

}
