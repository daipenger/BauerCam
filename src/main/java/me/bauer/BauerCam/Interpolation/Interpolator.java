package me.bauer.BauerCam.Interpolation;

import java.util.List;

import me.bauer.BauerCam.Path.Position;

public final class Interpolator {

	protected final PositionInterpolator a;
	protected final PolarCoordinatesInterpolator b;
	protected final AdditionalAngleInterpolator c;

	protected final Position[] points;

	/**
	 * is the actual "length" of the array which is points.length - 1 (visually
	 * the length of the path you have to go from index 0 to n)
	 */
	protected final int pathLength;

	public Interpolator(final List<Position> points, final PositionInterpolator a, final PolarCoordinatesInterpolator b,
			final AdditionalAngleInterpolator c) {
		this.points = new Position[points.size()];
		for (int i = 0; i < this.points.length; i++) {
			this.points[i] = points.get(i);
		}

		this.pathLength = this.points.length - 1;

		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Position getPoint(final double pathPosition) {
		final double section = pathPosition * this.pathLength;

		int section1 = (int) section;
		if (section1 == this.pathLength) {
			// pathPosition unavoidably reaches the last node at the very end ->
			// correcting that
			section1--;
		}
		final int section2 = section1 + 1;

		final double step = section - section1;

		int section0 = section1 - 1;
		int section3 = section2 + 1;

		// Bounding the outer nodes inside the array if necessary
		// I could extrapolate these points, but this is a quick and acceptable
		// solution: It even creates some kind of fade in and out
		if (section0 < 0) {
			section0 = 0;
		}
		if (section3 > this.pathLength) {
			section3 = this.pathLength;
		}

		final Position y0 = this.points[section0];
		final Position y1 = this.points[section1];
		final Position y2 = this.points[section2];
		final Position y3 = this.points[section3];

		final PositionBuilder builder = new PositionBuilder();

		this.a.interpolatePosition(builder, y0, y1, y2, y3, step);
		this.b.interpolatePolarCoordinates(builder, y0, y1, y2, y3, step);
		this.c.interpolateAdditionAngles(builder, y0, y1, y2, y3, step);

		return builder.build();
	}

}
