package com.tillnagel.geom.arcs.apps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tillnagel.geom.arcs.Edge;
import com.tillnagel.geom.arcs.Node;
import com.tillnagel.geom.arcs.SankeyArc;

import processing.core.PApplet;

public class SankeyArcLayoutComparisonApp extends SankeyArcExperimentsApp {

	List<Node> nodes;
	List<Edge> edges;

	public static void main(String args[]) {
		PApplet.main(new String[] { SankeyArcLayoutComparisonApp.class.getName() });
	}

	public void settings() {
		size(800, 600, P2D);
		smooth();
	}

	public void setup() {
		textFont(createFont("Sans-Serif", 9));

		Node node1 = new Node("1", 1, "1");
		Node node2 = new Node("2", 2, "2");
		Node node3 = new Node("3", 3, "3");
		Node node4 = new Node("4", 4, "4");
		Node node5 = new Node("5", 5, "5");

		nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		nodes.add(node4);
		nodes.add(node5);

		edges = new ArrayList<Edge>();
		edges.add(new Edge(node4, node5, 20));
		edges.add(new Edge(node2, node4, 40));
		edges.add(new Edge(node1, node4, 40));
		edges.add(new Edge(node1, node3, 20));

		// Collections.sort(edges);

		SankeyArc.setNodeValues(edges);

		noLoop();
	}

	private void setEquiDistantNodePositions(int xStart, int yStart, int distance) {
		int i = 0;
		for (Node node : nodes) {
			float x = xStart + i * distance;
			float y = yStart;
			node.pos.x = x;
			node.pos.y = y;
			i++;
		}
	}

	private void setEquiDistantGaps(int xStart, int yStart, int distance) {
		float oldX = xStart;
		Node oldNode = null;
		for (Node node : nodes) {
			float x = oldX + distance + node.sumTotalEdgeValues / 2;
			if (oldNode != null) {
				x = x + oldNode.sumTotalEdgeValues / 2;
			}
			float y = yStart;
			node.pos.x = x;
			node.pos.y = y;

			oldX = x;
			oldNode = node;
		}
	}

	public void draw() {
		background(255);

		// classic arcs
		// setEquiDistantNodePositions(100, 200, 25);
		setEquiDistantNodePositions(100, 100, 50);
		drawArc(g, edges);
		drawNodes(nodes, true, true, 1);

		// sankey arc, same dist -> overlapping
		Collections.sort(edges);
		setEquiDistantNodePositions(100, 300, 50);
		SankeyArc.drawEdgeArcs(g, edges, false);
		drawNodes(nodes, false, true, 1);

		// sankey arc, higher dist -> no overlapping, but needs more space
		setEquiDistantNodePositions(100, 500, 70);
		SankeyArc.drawEdgeArcs(g, edges, false);
		drawNodes(nodes, false, true, 1);

		// sankey arc, equi gaps -> no overlapping, needs more space
		setEquiDistantGaps(500, 500, 10);
		SankeyArc.drawEdgeArcs(g, edges, false);
		drawNodes(nodes, false, true, 1);

		// sankey arc, different encoding -> no overlapping, same space
		setEquiDistantNodePositions(100, 700, 50);
		SankeyArc.valueFactor = 2;
		SankeyArc.drawEdgeArcs(g, edges, false);
		drawNodes(nodes, false, true, 2);

	}

}
