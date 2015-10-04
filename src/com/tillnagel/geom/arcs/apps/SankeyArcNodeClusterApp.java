package com.tillnagel.geom.arcs.apps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tillnagel.geom.arcs.Edge;
import com.tillnagel.geom.arcs.Node;
import com.tillnagel.geom.arcs.SankeyArc;

import processing.core.PApplet;

public class SankeyArcNodeClusterApp extends SankeyArcExperimentsApp {

	List<Node> nodes;
	List<Edge> edges;

	public static void main(String args[]) {
		PApplet.main(new String[] { SankeyArcNodeClusterApp.class.getName() });
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
		Node node5 = new Node("5", 5, "5");
		Node node6 = new Node("6", 6, "6");
		Node node7 = new Node("7", 7, "7");
		Node node8 = new Node("8", 8, "8");
		nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		nodes.add(node4);
		nodes.add(node5);
		nodes.add(node6);
		nodes.add(node7);
		nodes.add(node8);
		edges = new ArrayList<Edge>();
		edges.add(new Edge(node1, node8, 10));
		edges.add(new Edge(node2, node8, 10));
		edges.add(new Edge(node5, node8, 10));
		edges.add(new Edge(node6, node8, 10));

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
		setEquiDistantNodePositions(nodes, 100, 200, 20);
		drawArc(g, edges);
		drawNodes(nodes, true, false, 1);
		
		Collections.sort(edges);
		setEquiDistantNodePositions(nodes, 500, 200, 20);
		SankeyArc.drawEdgeArcs(g, edges, false);
		drawNodes(nodes, false, false, 1);

	}

}
