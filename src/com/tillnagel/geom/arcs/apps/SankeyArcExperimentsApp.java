package com.tillnagel.geom.arcs.apps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tillnagel.geom.arcs.ArcConnection;
import com.tillnagel.geom.arcs.ArcDisplay;
import com.tillnagel.geom.arcs.Edge;
import com.tillnagel.geom.arcs.Node;
import com.tillnagel.geom.arcs.SankeyArc;

import processing.core.PApplet;
import processing.core.PGraphics;
import toxi.geom.Polygon2D;

public class SankeyArcExperimentsApp extends PApplet {

	List<Node> nodes;
	List<Edge> edges;

	public static void main(String args[]) {
		PApplet.main(new String[] { SankeyArcExperimentsApp.class.getName() });
	}

	public void settings() {
		size(1600, 400, P2D);
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
		edges.add(new Edge(node4, node5, 10));
		edges.add(new Edge(node2, node4, 20));
		edges.add(new Edge(node1, node4, 20));
		edges.add(new Edge(node1, node3, 10));

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

	public void draw() {
		background(255);

		// classic arcs
		setEquiDistantNodePositions(100, height - 100, 70);
		drawArc(g, edges);
		drawNodes(nodes, true);

		// sankey arc
		setEquiDistantNodePositions(500, height - 100, 70);
		SankeyArc.drawEdgeArcs(g, edges, false);
		drawNodes(nodes);

		// ordered sankey arc
		Collections.sort(edges);
		setEquiDistantNodePositions(900, height - 100, 70);
		SankeyArc.drawEdgeArcs(g, edges, false);
		drawNodes(nodes);
	}

	public void drawNodes(List<Node> nodes) {
		drawNodes(nodes, false, false, 1);
	}

	public void drawNodes(List<Node> nodes, boolean fixedWidth) {
		drawNodes(nodes, fixedWidth, false, 1);
	}

	public void drawNodes(List<Node> nodes, boolean fixedWidth, boolean showCenter, float valueFactor) {
		for (Node node : nodes) {
			fill(30, 30, 160, 160);
			noStroke();
			float x, y, width;
			float height = 8;
			if (fixedWidth) {
				x = node.pos.x - getThickness(node.maxEdgeValue, valueFactor) / 2;
				y = node.pos.y;
				width = getThickness(node.maxEdgeValue, valueFactor);
			} else {
				x = node.pos.x - getThickness(node.sumTotalEdgeValues, valueFactor) / 2;
				y = node.pos.y;
				width = getThickness(node.sumTotalEdgeValues, valueFactor);
			}
			rect(x, y, width, height);
			if (showCenter) {
				stroke(0, 100);
				line(node.pos.x, node.pos.y + height, node.pos.x, node.pos.y + height + 4);
			}
		}
	}

	protected float getThickness(float value, float valueFactor) {
		return value / valueFactor;
	}

	public void drawArc(PGraphics g, List<Edge> edges) {
		for (Edge edge : edges) {
			Polygon2D poly = ArcConnection.getHalfCircleQuads(edge.nodeA.pos, edge.nodeB.pos, edge.value, true);
			ArcDisplay.drawPolyQuad(g, poly, color(0, 60));
		}
	}

}
