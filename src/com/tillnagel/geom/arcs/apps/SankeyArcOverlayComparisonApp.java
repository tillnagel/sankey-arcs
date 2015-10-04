package com.tillnagel.geom.arcs.apps;

import java.util.ArrayList;
import java.util.List;

import com.tillnagel.geom.arcs.Edge;
import com.tillnagel.geom.arcs.Node;
import com.tillnagel.geom.arcs.SankeyArc;

import processing.core.PApplet;

public class SankeyArcOverlayComparisonApp extends SankeyArcExperimentsApp {

	List<Node> closeNodes;
	List<Edge> closeEdges;

	List<Node> distantNodes;
	List<Edge> distantEdges;

	public static void main(String args[]) {
		PApplet.main(new String[] { SankeyArcOverlayComparisonApp.class.getName() });
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
		closeNodes = new ArrayList<Node>();
		closeNodes.add(node1);
		closeNodes.add(node2);
		closeNodes.add(node3);
		closeEdges = new ArrayList<Edge>();
		closeEdges.add(new Edge(node1, node3, 40));
		closeEdges.add(new Edge(node2, node3, 10));

		// Collections.sort(edges);

		SankeyArc.setNodeValues(closeEdges);

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
		setEquiDistantNodePositions(closeNodes, 100, 200, 100);
		drawArc(g, closeEdges);
		drawNodes(closeNodes, true, false, 1);
		
		setEquiDistantNodePositions(closeNodes, 400, 200, 100);
		closeNodes.get(1).pos.x += 60;
		drawArc(g, closeEdges);
		drawNodes(closeNodes, true, false, 1);

		setEquiDistantNodePositions(closeNodes, 100, 400, 100);
		closeNodes.get(1).pos.x -= 20;
		SankeyArc.drawEdgeArcs(g, closeEdges, false);
		drawNodes(closeNodes, false, false, 1);
		
		setEquiDistantNodePositions(closeNodes, 400, 400, 100);
		closeNodes.get(1).pos.x += 40;
		SankeyArc.drawEdgeArcs(g, closeEdges, false);
		drawNodes(closeNodes, false, false, 1);
		//
		// // sankey arc, higher dist -> no overlapping, but needs more space
		// setEquiDistantNodePositions(100, 450, 35);
		// SankeyArc.drawEdgeArcs(g, edges, false);
		// drawNodes(nodes, false, true, 1);
		//
		// // sankey arc, different encoding -> no overlapping, same space
		// setEquiDistantNodePositions(100, 600, 25);
		// SankeyArc.valueFactor = 2;
		// SankeyArc.drawEdgeArcs(g, edges, false);
		// drawNodes(nodes, false, true, 2);

	}

}
