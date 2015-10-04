package com.tillnagel.geom.arcs.apps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tillnagel.geom.arcs.Edge;
import com.tillnagel.geom.arcs.Node;
import com.tillnagel.geom.arcs.SankeyArc;

import processing.core.PApplet;

public class SankeyArcVsCircosApp extends SankeyArcExperimentsApp {

	List<Node> nodes;
	List<Edge> edges;

	public static void main(String args[]) {
		PApplet.main(new String[] { SankeyArcVsCircosApp.class.getName() });
	}

	public void settings() {
		size(900, 500, P2D);
		smooth();
	}

	public void setup() {
		textFont(createFont("Sans-Serif", 9));

		Node nodeA = new Node("1", 1, "A");
		Node nodeB = new Node("2", 2, "B");
		Node nodeC = new Node("3", 3, "C");
		Node nodeD = new Node("4", 4, "D");
		Node nodeE = new Node("5", 5, "E");
		Node nodeF = new Node("6", 6, "F");
		nodes = new ArrayList<Node>();
		nodes.add(nodeA);
		nodes.add(nodeB);
		nodes.add(nodeC);
		nodes.add(nodeD);
		nodes.add(nodeE);
		nodes.add(nodeF);
		edges = new ArrayList<Edge>();
		edges.add(new Edge(nodeA, nodeD, 100));
		edges.add(new Edge(nodeA, nodeE, 192));
		edges.add(new Edge(nodeA, nodeF, 251));
		edges.add(new Edge(nodeB, nodeD, 24));
		edges.add(new Edge(nodeB, nodeE, 44));
		edges.add(new Edge(nodeB, nodeF, 54));
		edges.add(new Edge(nodeC, nodeD, 122));
		edges.add(new Edge(nodeC, nodeE, 104));
		edges.add(new Edge(nodeC, nodeF, 171));

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
//		setEquiDistantNodePositions(nodes, 100, 200, 20);
//		drawArc(g, edges);
//		drawNodes(nodes, true, false, 1);
		
		Collections.sort(edges);
		setEquiDistantNodePositions(nodes, 400, 300, 65);
		
		SankeyArc.valueFactor = 7;
		SankeyArc.drawEdgeArcs(g, edges, false);
		drawNodes(nodes, false, false, 7);

	}

}
