package me.bauer.BauerCam.Interpolation;

import me.bauer.BauerCam.Path.Position;

public interface PolarCoordinatesInterpolator {

	/**
	 * This module gets invoked AFTER
	 * {@link PositionInterpolator#interpolate(PositionBuilder, Position, Position, Position, Position, double)}
	 * which means that x, y and z in the {@link PositionBuilder} should be
	 * already populated
	 * <p>
	 * This invoke HAS TO calculate pitch and yaw
	 *
	 * @param builder
	 *            The builder where all intermediate results are to be saved to
	 * @param y0
	 *            The node before the last left behind node which is the same as
	 *            y1 at the beginning of the path
	 * @param y1
	 *            The most recent node the player left behind
	 * @param y2
	 *            The upcoming node the player has to pass
	 * @param y3
	 *            The node after the upcoming node which is the same as y2 at
	 *            the end of the path
	 * @param step
	 *            The fraction of which the player has already reached the next
	 *            node (0-> he is still at y1, 1 -> he is already at y2)
	 */
	public void interpolatePolarCoordinates(PositionBuilder builder, Position y0, Position y1, Position y2, Position y3,
			double step);

}
