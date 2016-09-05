package me.bauer.BauerCam.Interpolation;

import me.bauer.BauerCam.Path.Position;

public class TargetInterpolator implements PolarCoordinatesInterpolator {

	protected final Position target;

	public TargetInterpolator(final Position target) {
		this.target = target;
	}

	@Override
	public void interpolatePolarCoordinates(final PositionBuilder builder, final Position y0, final Position y1,
			final Position y2, final Position y3, final double step) {
		builder.setPolarCoordinates(builder.getPosition().lookAt(this.target));
	}

}
