package com.tillnagel.geom.arcs;

import processing.core.PConstants;
import processing.core.PGraphics;
import toxi.color.ColorGradient;
import toxi.color.ColorList;
import toxi.color.TColor;
import toxi.geom.Polygon2D;
import toxi.geom.Vec2D;

public class ArcDisplay {

	public static ColorList colorList;
	public static final int incomingColor = -16733264; // color(0, 171, 176); // tron turqoise
	public static final int outgoingColor = -20921; // color(255, 174, 71); // tron orange

	static {
		ColorGradient grad = new ColorGradient();
		grad.addColorAt(0, TColor.newARGB(incomingColor));
		grad.addColorAt(ArcConnection.NUM_STEPS + 1, TColor.newARGB(outgoingColor));
		colorList = grad.calcGradient(0, ArcConnection.NUM_STEPS + 1);
	}

	public static void drawPolyQuad(PGraphics pg, Polygon2D poly, int color) {
		pg.noStroke();
		pg.fill(color);

		pg.beginShape(PConstants.QUAD_STRIP);
		for (Vec2D v : poly.vertices) {
			pg.vertex(v.x, v.y);
		}
		pg.endShape();
	}

	public static void drawGradientPolyQuad(PGraphics pg, Polygon2D poly, float alpha) {
		pg.noStroke();

		pg.beginShape(PConstants.QUAD_STRIP);
		int i = 0;
		int j = 0;
		for (Vec2D v : poly.vertices) {
			pg.fill(colorList.get(j).toARGB(), alpha);
			pg.vertex(v.x, v.y);
			i++;
			if (i % 2 == 0) {
				j++;
			}
		}
		pg.endShape();
	}

}
