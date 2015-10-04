package com.tillnagel.geom.arcs.apps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tillnagel.geom.arcs.Edge;
import com.tillnagel.geom.arcs.Node;
import com.tillnagel.geom.arcs.SankeyArc;

import processing.core.PApplet;

public class SankeyArcApp extends PApplet {

	List<Node> nodes;
	List<Edge> edges;

	float xStart = 25;

	public static void main(String args[]) {
		PApplet.main(new String[] { SankeyArcApp.class.getName() });
	}

	public void settings() {
		size(2500, 1200, P2D);
		smooth();
	}

	public void setup() {
		textFont(createFont("Sans-Serif", 9));

		JSONObject jsonObject = loadNetworkJSON();
		nodes = createNodesFromJSON(jsonObject);
		edges = createEdgesFromJSON(jsonObject, nodes);
		Collections.sort(edges);

		setNodePositions(xStart);
		SankeyArc.setNodeValues(edges);

		// noLoop();
	}

	private void setNodePositions(float xStart) {
		int i = 0;
		for (Node node : nodes) {
			float x = xStart + i * 82; // (full width: 32), (original width: 13)
			float y = height - 200;
			node.pos.x = x;
			node.pos.y = y;
			i++;
		}
	}

	public void mouseDragged() {
		xStart += (mouseX - pmouseX);
		setNodePositions(xStart);
	}

	public void draw() {
		background(255);

		SankeyArc.drawEdgeArcs(g, edges, false);

		// Draw all nodes
		for (Node node : nodes) {
			fill(30, 30, 160, 160);
			noStroke();
			rect(node.pos.x - node.sumTotalEdgeValues / 2, node.pos.y, node.sumTotalEdgeValues, 8);
		}

		// Draw all node labels
		for (Node node : nodes) {
			fill(0, 160);
			pushMatrix();
			translate(node.pos.x, node.pos.y + 10);
			rotate(radians(45));
			text(node.name, 0, 0);
			popMatrix();
		}
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

	private List<Node> createNodesFromJSON(JSONObject jsonObject) {
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

	private List<Edge> createEdgesFromJSON(JSONObject jsonObject, List<Node> nodes) {
		// Lookup map to faster find nodes
		HashMap<String, Node> nodeMap = SankeyArc.createMap(nodes);

		List<Edge> edges = new ArrayList<Edge>();
		JSONArray edgesArray = (JSONArray) jsonObject.get("links");
		for (int i = 0; i < edgesArray.size(); i++) {
			JSONObject edgeObj = (JSONObject) edgesArray.get(i);
			Long sourceId = (Long) edgeObj.get("source");
			Long targetId = (Long) edgeObj.get("target");
			// Ensure direction is always from lower ID to higher ID
			if (sourceId > targetId) {
				Long t = sourceId;
				sourceId = targetId;
				targetId = t;
			}

			Long value = (Long) edgeObj.get("value");

			// FIXME Remove test-halving values
			// float valueF = (float)value / 6.0f;
			float valueF = (float) value / 1f;
			if (valueF > 0) {
				Node sourceNode = nodeMap.get(sourceId.toString());
				Node targetNode = nodeMap.get(targetId.toString());

				Edge edge = new Edge(sourceNode, targetNode, valueF);
				edges.add(edge);
			}
		}

		return edges;
	}

}
