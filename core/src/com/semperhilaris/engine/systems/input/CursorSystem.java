package com.semperhilaris.engine.systems.input;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.ObjectMap;
import com.semperhilaris.engine.Events;
import com.semperhilaris.engine.Log;
import com.semperhilaris.engine.events.CursorChangeEvent;
import net.engio.mbassy.listener.Handler;

/**
 *
 */
public class CursorSystem extends EntitySystem {

	private ObjectMap<String, Cursor> cursors = new ObjectMap<String, Cursor>(10);

	public CursorSystem() {
		Events.subscribe(this);
	}

	public CursorSystem(ObjectMap<String, String> cursorNames) {
		this();
		for (ObjectMap.Entry<String, String> entry : cursorNames) {
			addCursor(entry.key, entry.value);
		}
	}

	public void addCursor(String fileName, String alias) {
		cursors.put(alias, Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal(fileName)), 16, 16));
	}

	@Handler
	public void setCursor(CursorChangeEvent cursorChangeEvent) {
		if (checkProcessing()) {
			Log.msg("Cursor Change", cursorChangeEvent.alias);
			Gdx.graphics.setCursor(cursors.get(cursorChangeEvent.alias));
		}
	}

}
