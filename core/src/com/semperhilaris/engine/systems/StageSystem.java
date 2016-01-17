package com.semperhilaris.engine.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * An EntitySystem that contains a Scene2D stage.
 * Input events are passed to that stage if the system is enabled and added to the game's InputMultiplexer.
 */
public class StageSystem extends EntitySystem implements InputProcessor {

	public Stage stage;

	public StageSystem(Viewport viewport, Batch batch) {
		stage = new Stage(viewport, batch);
	}

	@Override
	public void update(float deltaTime) {
		if (checkProcessing()) {
			stage.act(deltaTime);
			stage.draw();
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		return checkProcessing() && stage.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return checkProcessing() && stage.keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		return checkProcessing() && stage.keyTyped(character);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return checkProcessing() && stage.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return checkProcessing() && stage.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return checkProcessing() && stage.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return checkProcessing() && stage.mouseMoved(screenX, screenY);
	}

	@Override
	public boolean scrolled(int amount) {
		return checkProcessing() && stage.scrolled(amount);
	}

}
