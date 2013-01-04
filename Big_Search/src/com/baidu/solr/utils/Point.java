package com.baidu.solr.utils;

public class Point<K, V> {
	private K x;

	private V y;

	public Point(K x, V y) {
		this.x = x;
		this.y = y;
	}

	public final K getX() {
		return x;
	}

	public final V getY() {
		return y;
	}

	public final void setX(K x) {
		this.x = x;
	}

	public final void setY(V y) {
		this.y = y;
	}
}