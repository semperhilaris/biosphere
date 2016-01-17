package com.semperhilaris.biosphere.components;

import com.badlogic.ashley.core.Component;

/**
 *
 */
public class Timer implements Component {

    public float value;

    public Timer(float value) {
        this.value = value;
    }

    public Timer() {
        this(0);
    }

}
