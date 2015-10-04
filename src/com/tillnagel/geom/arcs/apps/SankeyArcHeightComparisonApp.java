package com.tillnagel.geom.arcs.apps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tillnagel.geom.arcs.Edge;
import com.tillnagel.geom.arcs.Node;
import com.tillnagel.geom.arcs.SankeyArc;

import processing.core.PApplet;

public class SankeyArcHeightComparisonApp extends SankeyArcExperimentsApp {

	List<Node> nodes;
	List<Edge> edges;

	public static void main(String args[]) {
		PApplet.main(new String[] { SankeyArcHeightComparisonApp.class.getName() });
	}

	public void settings() {
		size(900, 500, P2D);
		smooth();
	}

	public void setup() {
		textFont(createFont("Sans-Serif", 9));

		Node node1 = new Node("1", 1, "1");
		Node node2 = new Node("2", 2, "2");
		Node node3 = new Node("3", 3, "3");
		Node node4 = new Node("4", 4, "4");
		nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		nodes.add(node4);
		edges = new ArrayList<Edge>();
		edges.add(new Edge(node1, node2, 50));
		edges.add(new Edge(node2, node3, 10));
		edges.add(new Edge(node3, node4, 10));

		// Collections.sort(edges);

		SankeyArc.setNodeValues(edges);

		noLoop();
	}

	private void setEquiDistantNodePositions(List<Node> nodes, int xStart, int yStart, int distance) {
		int i = 0;
		for (Node node : nodes) {
			float x = xStart + i * distance;
			float y = yStart;
			node.pos.x = x;
			node.pos.y = y;
			i++;
		}
	}

	public void draw() {
		background(255);

		// classic arcs
		setEquiDistantNodePositions(nodes, 100, 200, 100);
		drawArc(g, edges);
		drawNodes(nodes, true, false, 1);

		Collections.sort(edges);
		setEquiDistantNodePositions(nodes, 500, 200, 100);
		SankeyArc.drawEdgeArcs(g, edges, false);
		drawNodes(nodes, false, false, 1);

	}

}
