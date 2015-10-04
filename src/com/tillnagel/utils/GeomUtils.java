package com.tillnagel.utils;

import processing.core.PApplet;
import processing.core.PVector;

public class GeomUtils {

	/**
	 * Calculates a parallel line to given line in specified distance.
	 * 
	 * @param p1x
	 *            X coordinate of line start point.
	 * @param p1y
	 *            Y coordinate of line start point.
	 * @param p2x
	 *            X coordinate of line end point.
	 * @param p2y
	 *            Y coordinate of line end point.
	 * @param distance
	 *            The distance in pixels to render the parallel line.
	 * @return Four coordinates of the parallel line.
	 */
	public static float[] getParallelLine(float p1x, float p1y, float p2x, float p2y, float distance) {
		float l = PApplet.sqrt((p1x - p2x) * (p1x - p2x) + (p1y - p2y) * (p1y - p2y));
		float x1 = p1x + distance * (p2y - p1y) / l;
		float y1 = p1y + distance * (p1x - p2x) / l;
		float x2 = p2x + distance * (p2y - p1y) / l;
		float y2 = p2y + distance * (p1x - p2x) / l;
		return new float[] { x1, y1, x2, y2 };
	}

	/**
	 * Calculates the smallest distance between a line and a point.
	 * 
	 * @param lineStartX
	 *            X of starting point of line.
	 * @param lineStartY
	 *            Y of starting point of line.
	 * @param lineEndX
	 *            X of ending point of line.
	 * @param lineEndY
	 *            Y of ending point of line.
	 * @param px
	 *            X of point.
	 * @param py
	 *            Y of point.
	 * @return The distance.
	 */
	public static float getDistanceBetweenLineAndPoint(float lineStartX, float lineStartY, float lineEndX,
			float lineEndY, float px, float py) {
		float x = lineStartX + 0.5f * (lineEndX - lineStartX);
		float y = lineStartY + 0.5f * (lineEndY - lineStartY);

		PVector tang = new PVector(x, y);
		PVector point = new PVector(px, py);

		return tang.dist(point);
	}

}
