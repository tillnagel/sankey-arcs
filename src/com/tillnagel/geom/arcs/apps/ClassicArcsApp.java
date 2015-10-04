package com.tillnagel.geom.arcs.apps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tillnagel.geom.arcs.Edge;
import com.tillnagel.geom.arcs.Node;
import com.tillnagel.geom.arcs.SankeyArc;

import processing.core.PApplet;

public class ClassicArcsApp extends SankeyArcExperimentsApp {

	List<Node> nodes;
	List<Edge> edges;

	
	boolean showCircles = true;
	boolean scaleCircles = true;
	
	public static void main(String args[]) {
		PApplet.main(new String[] { ClassicArcsApp.class.getName() });
	}

	public void settings() {
		size(1000, 400, P2D);
		smooth();
	}

	public void setup() {
		textFont(createFont("Sans-Serif", 9));

		initGraph();
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

		setEquiDistantNodePositions(nodes, 50, 350, 100);
		drawArc(g, edges);
		
		if (showCircles) {
			for (Node node : nodes) {
				fill(30, 30, 160, 160);
				noStroke();
				float s = (scaleCircles) ? node.sumTotalEdgeValues / 2 : 10;
				ellipse(node.pos.x, node.pos.y, s, s);
				
			}
		}
		drawNodes(nodes, true, false, 0);
	}
	
	public void keyPressed() {
		if (key == 'e') {
			for (Edge edge : edges) {
				edge.value = 10;
			}
		}
		if (key == 'c') {
			showCircles = !showCircles;
		}
		if (key == 's') {
			scaleCircles = !scaleCircles;
		}
	}
	
	public void mousePressed() {
		initGraph();
	}
	
	public void initGraph() {
		Node node1 = new Node("1", 1, "1");
		Node node2 = new Node("2", 2, "2");
		Node node3 = new Node("3", 3, "3");
		Node centerNode = new Node("4", 4, "4");
		Node node5 = new Node("5", 5, "5");
		Node node6 = new Node("6", 6, "6");
		Node node7 = new Node("7", 7, "7");
		Node node8 = new Node("8", 8, "8");
		nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		nodes.add(centerNode);
		nodes.add(node5);
		nodes.add(node6);
		nodes.add(node7);
		nodes.add(node8);
		edges = new ArrayList<Edge>();
		edges.add(new Edge(node1, centerNode, 5 * 2));
		edges.add(new Edge(node2, centerNode, 20 * 2));
		edges.add(new Edge(node3, centerNode, 10 * 2));
		// edges.add(new Edge(centerNode, node5, 10));
		//edges.add(new Edge(centerNode, node6, 5));
		edges.add(new Edge(centerNode, node7, 10 * 2));
		edges.add(new Edge(centerNode, node8, 5 * 2));

		for (int i = 0; i < 10; i++) {
			// Create an edge between two random nodes
			int n = round(random(nodes.size() - 2));
			Node nodeA = nodes.get(n);
			int nB = round(random(n + 1, nodes.size() - 1));
			Node nodeB = nodes.get(nB);

			Edge edge = new Edge(nodeA, nodeB, random(5, 10));
			if (!edges.contains(edge)) {
				edges.add(edge);
			}
		}

		Collections.sort(edges);

		SankeyArc.setNodeValues(edges);
	}
	
}
