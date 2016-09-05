package me.bauer.BauerCam.Interpolation;

import me.bauer.BauerCam.Path.Position;
import me.bauer.BauerCam.Path.Vector3D;

public class TargetInterpolator implements PolarCoordinatesInterpolator {

	protected final Vector3D target;

	public TargetInterpolator(final Vector3D target) {
		this.target = target;
	}

	@Override
	public void interpolatePolarCoordinates(final PositionBuilder builder, final Position y0, final Position y1,
			final Position y2, final Position y3, final double step) {
		builder.setPolarCoordinates(builder.getPosition().lookAt(this.target));
	}

}
