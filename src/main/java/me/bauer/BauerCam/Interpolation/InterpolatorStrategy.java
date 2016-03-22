package me.bauer.BauerCam.Interpolation;

import me.bauer.BauerCam.Path.Position;

public interface InterpolatorStrategy {

	/**
	 *
	 * @param points
	 * @param actualLength
	 *            is the actual "length" of the array; calculated with
	 *            points.length - 1 (visually the path from index 0 to n)
	 * @param section1
	 * @param section2
	 * @param step
	 * @return
	 */
	public Position interpolate(Position[] points, int actualLength, int section1, int section2, double step);

}
