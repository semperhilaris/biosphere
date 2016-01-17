package com.semperhilaris.biosphere.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.semperhilaris.biosphere.components.Timer;
import com.semperhilaris.engine.Events;
import com.semperhilaris.engine.events.ScreenChangeEvent;

/**
 *
 */
public class TimerCountdownSystem extends IteratingSystem {

    ComponentMapper<Timer> timerComponentMapper;

    public TimerCountdownSystem() {
        super(Family.all(Timer.class).get());

        timerComponentMapper = ComponentMapper.getFor(Timer.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Timer timer = timerComponentMapper.get(entity);
        if (timer.value > 0) {
            timer.value -= deltaTime;
            if (timer.value < 0) {
                timer.value = 0;
                Events.post(new ScreenChangeEvent("SCORE"));
            }
        }
    }

}
