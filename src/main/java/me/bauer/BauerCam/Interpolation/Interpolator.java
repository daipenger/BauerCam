package me.bauer.BauerCam.Interpolation;

import java.util.List;

import me.bauer.BauerCam.Path.Position;

public abstract class Interpolator {

	protected final Position[] points;

	public Interpolator(final List<Position> points) {
		this.points = new Position[points.size()];
		for (int i = 0; i < this.points.length; i++) {
			this.points[i] = points.get(i);
		}
	}

	public Position getPoint(final double pathPosition) {
		final int actualLength = this.points.length - 1;
		final double section = pathPosition * actualLength;

		int section1 = (int) section;
		if (section1 == actualLength) {
			// pathPosition unavoidably reaches the last node at the very end ->
			// correcting that
			section1--;
		}
		final int section2 = section1 + 1;

		final double step = section - section1;

		return interpolate(actualLength, section1, section2, step);
	}

	/**
	 *
	 * @param actualLength
	 *            is the actual "length" of the array; calculated with
	 *            points.length - 1 (visually the path-length from index 0 to n)
	 * @param section1
	 *            The most recent node the player left behind
	 * @param section2
	 *            The upcoming node the player has to pass
	 * @param step
	 *            The fraction of which the player has already reached the next
	 *            node (0-> he is still at section1, 1 -> he is already at
	 *            section2)
	 * @return
	 */
	protected abstract Position interpolate(int actualLength, int section1, int section2, double step);

}
