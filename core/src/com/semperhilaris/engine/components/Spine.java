package com.semperhilaris.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.semperhilaris.engine.Assets;

/**
 * Spine Component
 */
public class Spine implements Component {

	public Skeleton skeleton;
	public SkeletonData skeletonData;
	public AnimationState animationState;
	public AnimationStateData animationStateData;
	private String fileExtension;

	public Spine(String skeletonFile, String skin) {

		if (Gdx.files.internal(skeletonFile + ".skel").exists()) {
			fileExtension = ".skel";
		}
		else if (Gdx.files.internal(skeletonFile + ".json").exists()) {
			fileExtension = ".json";
		}
		FileHandle skeletonFileHandle = Gdx.files.internal(skeletonFile + fileExtension);

		TextureAtlas atlas = Assets.getTextureAtlas();
		SkeletonJson json = new SkeletonJson(atlas);
		skeletonData = json.readSkeletonData(skeletonFileHandle);
		skeleton = new Skeleton(skeletonData);
		skeleton.setSkin(skin);
		skeleton.setSlotsToSetupPose();
		animationStateData = new AnimationStateData(skeletonData);
		animationState = new AnimationState(animationStateData);
	}

	public Spine(String skeletonFile) {
		this(skeletonFile, "default");
	}

}
