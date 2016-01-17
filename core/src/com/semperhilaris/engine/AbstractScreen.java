package com.semperhilaris.engine;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Array;

/**
 *
 */
public class AbstractScreen implements LazyLoader {

	private boolean loaded;

	protected AbstractGame game;
	protected Array<Class<? extends EntitySystem>> entitySystems = new Array<Class<? extends EntitySystem>>();

	public AbstractScreen(AbstractGame game) {

		this.game = game;

		// Subscribe to the EventBus
		Events.subscribe(this);
	}

	protected void enableSystem(Class<? extends EntitySystem> system) {
		this.entitySystems.add(system);
	}

	protected void enableSystems(Class<? extends EntitySystem>... systems) {
		for (Class<? extends EntitySystem> system : systems) {
			enableSystem(system);
		}
	}

	protected void render() {
		if (Assets.assetManager.getProgress() == 1 && !loaded) {
			loaded = true;
			afterLoad();
		}
	}

	protected void show() {
		for (Class<? extends EntitySystem> system : entitySystems) {
			if (game.engine.getSystem(system) != null) {
				Log.msg("Enable System", system.getSimpleName());
				game.engine.getSystem(system).setProcessing(true);
			}
		}
	}

	protected void hide() {
		for (Class<? extends EntitySystem> system : entitySystems) {
			if (game.engine.getSystem(system) != null) {
				Log.msg("Disable System", system.getSimpleName());
				game.engine.getSystem(system).setProcessing(false);
			}
		}
	}

	@Override
	public void afterLoad() {

	}

}
