package com.tillnagel.geom.arcs;

import toxi.geom.Vec2D;

public class Node implements Comparable<Node> {

	public String id;

	public Comparable sequenceId;

	public String name;

	public Vec2D pos;

	public int numEdges;

	public float sumIncomingEdgeValues;
	public float sumOutgoingEdgeValues;
	public float sumTotalEdgeValues;

	public float maxEdgeValue = Float.MIN_VALUE;
	public float minEdgeValue = Float.MAX_VALUE;

	public Node() {
		this.pos = new Vec2D();
	}

	public Node(String id, Comparable sequenceId, String name) {
		this();
		this.id = id;
		this.sequenceId = sequenceId;
		this.name = name;
	}

	@Override
	public int compareTo(Node other) {
		return sequenceId.compareTo(other.sequenceId);
	}

	@Override
	public String toString() {
		return name + "(" + id + ")";
	}

	public void updateMaxAndMinEdgeValue(float value) {
		if (value > maxEdgeValue) {
			maxEdgeValue = value;
		}
		if (value < minEdgeValue) {
			minEdgeValue = value;
		}
	}

}
