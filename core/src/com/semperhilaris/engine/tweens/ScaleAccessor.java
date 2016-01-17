package com.semperhilaris.engine.tweens;

import aurelienribon.tweenengine.TweenAccessor;
import com.semperhilaris.engine.components.Scale;

/**
 * TweenAccessor for Scale components
 */
public class ScaleAccessor implements TweenAccessor<Scale> {

	public static final int TWEEN_XY = 0;
	public static final int TWEEN_X = 1;
	public static final int TWEEN_Y = 2;

	@Override
	public int getValues(Scale target, int tweenType, float[] returnValues) {
		switch (tweenType) {
			case TWEEN_XY:
				returnValues[0] = target.x;
				returnValues[1] = target.y;
				return 2;
			case TWEEN_X:
				returnValues[0] = target.x;
				return 1;
			case TWEEN_Y:
				returnValues[0] = target.y;
				return 1;
			default:
				assert false;
				return -1;
		}
	}

	@Override
	public void setValues(Scale target, int tweenType, float[] newValues) {
		switch (tweenType) {
			case TWEEN_XY:
				target.set(newValues[0], newValues[0]);
				break;
			case TWEEN_X:
				target.x = newValues[0];
				break;
			case TWEEN_Y:
				target.y = newValues[0];
				break;
			default:
				assert false;
				break;
		}
	}

}
