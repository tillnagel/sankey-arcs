package com.tillnagel.geom.arcs;

public class Edge implements Comparable<Edge> {

	public static enum Directionality {
		NON, UNI, BI
	}

	public Node nodeA;
	public Node nodeB;

	public float value;

	public Directionality directionality = Directionality.UNI;

	public Edge(Node nodeA, Node nodeB, float value) {
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.value = value;
	}

	@Override
	public int compareTo(Edge other) {
		int c = nodeA.compareTo(other.nodeA);
		if (c == 0) {
			c = nodeB.compareTo(other.nodeB);
		}
		return c;
	}

	public String toString() {
		return nodeA.toString() + " to " + nodeB.toString() + " : " + value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Edge) {
			Edge otherEdge = (Edge) obj;
			return nodeA.id.equals(otherEdge.nodeA.id) && nodeB.id.equals(otherEdge.nodeB.id);
		}
		return false;
	}

}
