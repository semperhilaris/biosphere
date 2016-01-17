package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 *
 */
public class EnergyGenerator implements Component {

    public int amountMin;
    public int amountMax;

    public EnergyGenerator(int amountMin, int amountMax) {
        this.amountMin = amountMin;
        this.amountMax = amountMax;
    }

    public EnergyGenerator(int amount) {
        this(amount, amount);
    }

}
