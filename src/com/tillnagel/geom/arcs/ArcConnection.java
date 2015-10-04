package com.tillnagel.geom.arcs;

import com.tillnagel.utils.GeomUtils;

import processing.core.PApplet;
import toxi.geom.Line2D;
import toxi.geom.Polygon2D;
import toxi.geom.Vec2D;

public class ArcConnection {

	public static final int NUM_STEPS = 50;
	
	public static Polygon2D getHalfCircle(Vec2D p1, Vec2D p2, float weight, boolean upside) {
		Line2D line = new Line2D(p1, p2);
		Vec2D midPoint = line.getMidPoint();

		float dist = line.getLength();
		float startAngle = p1.sub(p2).heading();

		float radius = dist / 2;
		Polygon2D poly = new Polygon2D();
		float step = PApplet.PI / NUM_STEPS;
		for (int i = 0; i < NUM_STEPS; i++) {
			float theta = startAngle + (upside ? (i * step) : (-i * step));
			poly.add(Vec2D.fromTheta(theta).scaleSelf(radius).addSelf(midPoint));
		}

		return poly;
	}

	public static Polygon2D getHalfCircleQuads(Vec2D p1, Vec2D p2, float weight, boolean upside) {
		Line2D line = new Line2D(p1, p2);
		Vec2D midPoint = line.getMidPoint();

		float dist = line.getLength();
		float startAngle = p1.sub(p2).heading();

		float radius = dist / 2;
		Polygon2D poly = new Polygon2D();
		float step = PApplet.PI / NUM_STEPS;

		Vec2D oldPoint = null;
		for (int i = 0; i <= NUM_STEPS + 1; i++) {
			float theta = startAngle + (upside ? (i * step) : (-i * step));
			Vec2D point = Vec2D.fromTheta(theta).scaleSelf(radius).addSelf(midPoint);

			// FIXME tn, 14 Dec, 2011: Set arc beginning and end segments correctly.
			// (Use fewer numSteps to see issue)

			if (oldPoint != null) {
				float parLineTop[] = GeomUtils.getParallelLine(oldPoint.x, oldPoint.y, point.x, point.y, -weight / 2);
				float parLineBottom[] = GeomUtils.getParallelLine(oldPoint.x, oldPoint.y, point.x, point.y, weight / 2);
				Vec2D parPointTop = new Vec2D(parLineTop[0], parLineTop[1]);
				Vec2D parPointBottom = new Vec2D(parLineBottom[0], parLineBottom[1]);
				poly.add(parPointBottom);
				poly.add(parPointTop);
			}
			oldPoint = point.copy();
		}

		return poly;
	}

}
