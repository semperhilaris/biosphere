package com.semperhilaris.engine;

import com.semperhilaris.engine.events.Event;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;

/**
 * Global EventBus.
 */
public class Events {

	private static MBassador<Event> eventBus = new MBassador<Event>(BusConfiguration.SyncAsync());

	public static void post(Event event) {
		Log.msg("Event posted", event.getClass().getSimpleName());
		eventBus.post(event).now();
	}

	public static void subscribe(Object object) {
		eventBus.subscribe(object);
	}

}
