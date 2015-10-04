package com.tillnagel.geom.arcs;

import java.util.HashMap;
import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import toxi.geom.Polygon2D;
import toxi.geom.Vec2D;

public class SankeyArc {

	public static float valueFactor = 1;

	public static void drawEdgeArcs(PGraphics pg, List<Edge> edges) {
		drawEdgeArcs(pg, edges, true);
	}

	public static void drawEdgeArcs(PGraphics pg, List<Edge> edges, boolean useGradient) {
		String oldId = "";
		Vec2D currentSourcePos = new Vec2D();

		HashMap<String, Vec2D> currentTargetPosMap = new HashMap<String, Vec2D>();

		for (Edge edge : edges) {

			// Each new source node stores current position and goes backwards
			// Assumption: edges are ordered 1) by source nodes, and 2) by target nodes

			if (!edge.nodeA.id.equals(oldId)) {
				// If we just went to first edge of new node, set the current position anew
				currentSourcePos = edge.nodeA.pos.add(getWeight(edge.nodeA.sumTotalEdgeValues) / 2, 0);
				oldId = edge.nodeA.id;
			}
			currentSourcePos.subSelf(getWeight(edge.value), 0);

			// Target node position
			Vec2D currentTargetPos = currentTargetPosMap.get(edge.nodeB.id);
			if (currentTargetPos == null) {
				currentTargetPos = edge.nodeB.pos.add(getWeight(edge.nodeB.sumTotalEdgeValues) / 2, 0).sub(
						getWeight(edge.nodeB.sumOutgoingEdgeValues), 0);
				currentTargetPosMap.put(edge.nodeB.id, currentTargetPos);
			}
			currentTargetPos.subSelf(getWeight(edge.value), 0);

			float weight = getWeight(edge.value);
			// FIXME PAPER! Use correct visual encoding
			float alpha = PApplet.constrain(PApplet.map(edge.value, 1, 40, 150, 240), 150, 240);
			Polygon2D arcPolygon = ArcConnection.getHalfCircleQuads(currentSourcePos.add(weight / 2, 0),
					currentTargetPos.add(weight / 2, 0), weight, true);
			if (useGradient) {
				ArcDisplay.drawGradientPolyQuad(pg, arcPolygon, alpha);
			} else {
				ArcDisplay.drawPolyQuad(pg, arcPolygon, pg.color(0, 60));
			}

		}
	}

	protected static float getWeight(float value) {
		return value / valueFactor;
	}

	public static void setNodeValues(List<Edge> edges) {
		for (Edge edge : edges) {
			edge.nodeA.numEdges++;
			edge.nodeA.sumTotalEdgeValues += edge.value;
			edge.nodeA.sumOutgoingEdgeValues += edge.value;
			edge.nodeA.updateMaxAndMinEdgeValue(edge.value);

			edge.nodeB.numEdges++;
			edge.nodeB.sumTotalEdgeValues += edge.value;
			edge.nodeB.sumIncomingEdgeValues += edge.value;
			edge.nodeB.updateMaxAndMinEdgeValue(edge.value);
		}
	}

	public static HashMap<String, Node> createMap(List<Node> nodes) {
		HashMap<String, Node> nodeMap = new HashMap<String, Node>();
		for (Node node : nodes) {
			nodeMap.put(node.id, node);
		}
		return nodeMap;
	}

}
