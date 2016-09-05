package me.bauer.BauerCam.Interpolation;

public final class InterpolationUtils {

	/**
	 * Interpolates between y1 and y2. This is a cubic interpolation which
	 * behaves like Catmull-Rom
	 *
	 * @param y0
	 * @param y1
	 * @param y2
	 * @param y3
	 * @param x
	 *            from 0 to 1
	 * @return
	 */
	public static double cubic_catmull(final double y0, final double y1, final double y2, final double y3,
			final double x) {
		final double a = -0.5 * y0 + 1.5 * y1 - 1.5 * y2 + 0.5 * y3;
		final double b = y0 - 2.5 * y1 + 2 * y2 - 0.5 * y3;
		final double c = -0.5 * y0 + 0.5 * y2;

		return ((a * x + b) * x + c) * x + y1;
	}

	/**
	 * Interpolates between y1 and y2. This is a simple cubic interpolation
	 *
	 * @param y0
	 * @param y1
	 * @param y2
	 * @param y3
	 * @param x
	 *            from 0 to 1
	 * @return
	 */
	public static float cubic(final float y0, final float y1, final float y2, final float y3, final float x) {
		final float a = y3 - y2 - y0 + y1;
		final float b = y0 - y1 - a;
		final float c = y2 - y0;

		return ((a * x + b) * x + c) * x + y1;
	}

}
