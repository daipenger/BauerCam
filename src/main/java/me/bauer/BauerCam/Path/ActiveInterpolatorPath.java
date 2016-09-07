package me.bauer.BauerCam.Path;

import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Interpolation.Interpolator;

public final class ActiveInterpolatorPath extends ActivePath {

	private final Interpolator interpolator;
	private final long iterations;

	private long currentIteration;

	public ActiveInterpolatorPath(final Interpolator interpolator, final long iterations) {
		this.iterations = iterations;
		this.interpolator = interpolator;
		Utils.teleport(this.interpolator.getPoint(0), true);
	}

	@Override
	public void tick() {
		this.currentIteration++;

		final Position pos = this.interpolator.getPoint(this.currentIteration / (double) this.iterations);

		Utils.teleport(pos, false);

		if (this.currentIteration >= this.iterations) {
			stop();
		}
	}

}
