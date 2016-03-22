package me.bauer.BauerCam.Interpolation;

import java.util.List;

import me.bauer.BauerCam.Path.Position;

public final class Interpolator {

	private static final InterpolatorStrategy strategy = new CubicInterpolator();

	private final Position[] points;

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

		return strategy.interpolate(this.points, actualLength, section1, section2, step);
	}

	/**
	 * public static void setStrategy(int index) { if (index < 0 || index >=
	 * strategies.length) { Utils.sendInformation("No valid strategy index");
	 * return; } strategy = strategies[index]; Utils.sendInformation(
	 * "New strategy set"); }
	 *
	 * public static void printStrategies() { Utils.sendInformation(
	 * "Current interpolator:");
	 * Utils.sendInformation(strategy.getDescription()); Utils.sendInformation(
	 * "Available interpolators:"); for (int i = 0; i < strategies.length; i++)
	 * Utils.sendInformation(i + ": " + strategies[i].getDescription()); }
	 **/
}
