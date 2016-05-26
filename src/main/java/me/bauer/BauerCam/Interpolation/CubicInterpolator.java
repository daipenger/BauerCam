package me.bauer.BauerCam.Interpolation;

import java.util.List;

import me.bauer.BauerCam.Path.Position;

public final class CubicInterpolator extends Interpolator {

	public static final IInterpolatorFactory factory = new IInterpolatorFactory() {
		@Override
		public Interpolator getInterpolator(List<Position> points) {
			return new CubicInterpolator(points);
		}
	};

	public CubicInterpolator(List<Position> points) {
		super(points);
	}

	@Override
	protected Position interpolate(final int actualLength, final int section1, final int section2, final double step) {
		int section0 = section1 - 1;
		int section3 = section2 + 1;

		// Bounding the outer nodes inside the array if necessary
		// I could extrapolate these points, but this is a quick and acceptable
		// solution
		if (section0 < 0) {
			section0 = 0;
		}
		if (section3 > actualLength) {
			section3 = actualLength;
		}

		final Position y0 = points[section0];
		final Position y1 = points[section1];
		final Position y2 = points[section2];
		final Position y3 = points[section3];

		return new Position(interpolate(y0.x, y1.x, y2.x, y3.x, step), interpolate(y0.y, y1.y, y2.y, y3.y, step),
				interpolate(y0.z, y1.z, y2.z, y3.z, step),
				(float) interpolate(y0.pitch, y1.pitch, y2.pitch, y3.pitch, step),
				(float) interpolate(y0.yaw, y1.yaw, y2.yaw, y3.yaw, step),
				(float) interpolate(y0.roll, y1.roll, y2.roll, y3.roll, step));
	}

	/**
	 * Interpolates between y1 and y2. This is a cubic interpolation
	 *
	 * @param y0
	 * @param y1
	 * @param y2
	 * @param y3
	 * @param x
	 *            from 0 to 1
	 * @return
	 */
	private static double interpolate(final double y0, final double y1, final double y2, final double y3,
			final double x) {
		final double a = y3 - y2 - y0 + y1;
		final double b = y0 - y1 - a;
		final double c = y2 - y0;

		return ((a * x + b) * x + c) * x + y1;
	}

}
