package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.semperhilaris.biosphere.components.Energy;
import com.semperhilaris.biosphere.components.EnergyGenerator;
import com.semperhilaris.biosphere.components.Humans;
import com.semperhilaris.biosphere.components.HumansCapacity;

/**
 *
 */
public class EnergyGeneratorSystem extends IntervalIteratingSystem {

    ComponentMapper<EnergyGenerator> energyGeneratorComponentMapper;
    ComponentMapper<Energy> energyComponentMapper;
    ComponentMapper<Humans> humansComponentMapper;
    ComponentMapper<HumansCapacity> humansCapacityComponentMapper;

    public EnergyGeneratorSystem(float interval) {
        super(Family.all(EnergyGenerator.class, Energy.class, Humans.class, HumansCapacity.class).get(), interval);

        energyGeneratorComponentMapper = ComponentMapper.getFor(EnergyGenerator.class);
        energyComponentMapper = ComponentMapper.getFor(Energy.class);
        humansComponentMapper = ComponentMapper.getFor(Humans.class);
        humansCapacityComponentMapper = ComponentMapper.getFor(HumansCapacity.class);
    }

    @Override
    protected void processEntity(Entity entity) {
        EnergyGenerator energyGenerator = energyGeneratorComponentMapper.get(entity);
        Energy energy = energyComponentMapper.get(entity);
        Humans humans = humansComponentMapper.get(entity);
        HumansCapacity humansCapacity = humansCapacityComponentMapper.get(entity);

        if (humans.value == humansCapacity.value) {
            energy.value += MathUtils.random(energyGenerator.amountMin, energyGenerator.amountMax);
        }
    }

}
