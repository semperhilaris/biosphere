package com.semperhilaris.engine;

public interface LazyLoader {

	boolean loaded = false;

	public abstract void afterLoad();

}
