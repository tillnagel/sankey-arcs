package com.tillnagel.geom.arcs.apps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import processing.core.PApplet;
import toxi.geom.Polygon2D;
import toxi.geom.Vec2D;

import com.tillnagel.geom.arcs.ArcConnection;
import com.tillnagel.geom.arcs.Edge;
import com.tillnagel.geom.arcs.Node;

public class SimpleArcApp extends PApplet {

	List<Node> nodes;
	List<Edge> edges;

	public static void main(String args[]) {
		PApplet.main(new String[] { SimpleArcApp.class.getName() });
	}

	public void settings() {
		size(1200, 800, P2D);
		smooth();
	}

	public void setup() {
		textFont(createFont("Sans-Serif", 9));

		JSONObject jsonObject = loadNetworkJSON();
		nodes = getNodes(jsonObject);
		edges = getEdges(jsonObject, nodes);

		setNodePositions();
		setNodeValues();
	}

	private void setNodeValues() {
		for (Edge edge : edges) {
			edge.nodeA.sumTotalEdgeValues += edge.value;
			edge.nodeB.sumTotalEdgeValues += edge.value;
			edge.nodeA.numEdges++;
			edge.nodeB.numEdges++;
		}
	}

	private void setNodePositions() {
		int i = 0;
		for (Node node : nodes) {
			float x = 50 + i * 14;
			float y = height / 2 + 200;
			node.pos.x = x;
			node.pos.y = y;
			i++;
		}
	}

	public void draw() {
		background(255);

		for (Edge edge : edges) {
			drawTwoColoredHalfCircle(edge.nodeA.pos, edge.nodeB.pos, edge.value, false);
		}

		for (Node node : nodes) {
			fill(240, 220, 220);
			stroke(0, 60);
			float s = map(node.numEdges, 0, 30, 4, 25);
			ellipse(node.pos.x, node.pos.y, s, s);
		}

		for (Node node : nodes) {
			fill(0, 160);
			pushMatrix();
			translate(node.pos.x, node.pos.y + 10);
			rotate(radians(45));
			text(node.name, 0, 0);
			popMatrix();
		}
	}

	private void drawTwoColoredHalfCircle(Vec2D pos1, Vec2D pos2, float value, boolean upside) {
		Polygon2D poly = ArcConnection.getHalfCircleQuads(pos1, pos2, value, upside);
		noFill();
		noStroke();
		fill(0, 60);

		beginShape(QUAD_STRIP);
		// int i = 0;
		// int j = 0;
		for (Vec2D v : poly.vertices) {
			vertex(v.x, v.y);
			// i++;
			// if (i % 2 == 0) {
			// j++;
			// }
		}
		endShape();
	}

	public JSONObject loadNetworkJSON() {
		String[] jsonStrings = loadStrings("network-miserables2.json");
		String jsonString = "";
		for (int i = 0; i < jsonStrings.length; i++) {
			jsonString = jsonString.concat(jsonStrings[i]);
		}

		JSONObject jsonObject = null;
		try {
			JSONParser parser = new JSONParser();
			jsonObject = (JSONObject) parser.parse(jsonString);
		} catch (ParseException pe) {
			System.out.println(pe);
		}
		return jsonObject;
	}

	public List<Node> getNodes(JSONObject jsonObject) {
		List<Node> nodes = new ArrayList<Node>();
		JSONArray nodesArray = (JSONArray) jsonObject.get("nodes");
		for (int i = 0; i < nodesArray.size(); i++) {
			JSONObject nodeObj = (JSONObject) nodesArray.get(i);
			Long sequenceId = new Long(i);
			String id = sequenceId.toString();
			Node node = new Node(id, sequenceId, (String) nodeObj.get("nodeName"));
			nodes.add(node);
		}
		return nodes;
	}

	public List<Edge> getEdges(JSONObject jsonObject, List<Node> nodes) {
		HashMap<String, Node> nodeMap = createMap(nodes);

		List<Edge> edges = new ArrayList<Edge>();
		JSONArray edgesArray = (JSONArray) jsonObject.get("links");
		for (int i = 0; i < edgesArray.size(); i++) {
			JSONObject edgeObj = (JSONObject) edgesArray.get(i);
			String sourceId = ((Long) edgeObj.get("source")).toString();
			String targetId = ((Long) edgeObj.get("target")).toString();

			Long value = (Long) edgeObj.get("value");
			Node sourceNode = nodeMap.get(sourceId);
			Node targetNode = nodeMap.get(targetId);

			Edge edge = new Edge(sourceNode, targetNode, value);
			edges.add(edge);
		}

		return edges;
	}

	private HashMap<String, Node> createMap(List<Node> nodes) {
		HashMap<String, Node> nodeMap = new HashMap<String, Node>();
		for (Node node : nodes) {
			nodeMap.put(node.id, node);
		}
		return nodeMap;
	}
}
