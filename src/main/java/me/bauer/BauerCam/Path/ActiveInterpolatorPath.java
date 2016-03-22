package me.bauer.BauerCam.Path;

import java.util.List;

import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Interpolation.Interpolator;

public final class ActiveInterpolatorPath extends ActivePath {

	private final Interpolator interpolator;
	private final long iterations;
	private long currentIteration;

	public ActiveInterpolatorPath(final List<Position> points, final long iterations) {
		this.iterations = iterations;
		this.interpolator = new Interpolator(points);
		Utils.teleport(this.interpolator.getPoint(0), true);
	}

	@Override
	public void tick() {
		this.currentIteration++;

		final Position pos = this.interpolator.getPoint(this.currentIteration / (float) this.iterations);

		Utils.teleport(pos, false);

		if (this.currentIteration >= this.iterations) {
			pushStopNotice();
		}
	}

}
