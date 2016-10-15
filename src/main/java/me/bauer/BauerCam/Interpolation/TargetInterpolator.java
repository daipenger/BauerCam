package me.bauer.BauerCam.Interpolation;

import me.bauer.BauerCam.Path.Position;
import me.bauer.BauerCam.Path.Vector3D;

public class TargetInterpolator implements IPolarCoordinatesInterpolator {

	protected final Vector3D target;

	public TargetInterpolator(final Vector3D target) {
		this.target = target;
	}

	@Override
	public void interpolatePolarCoordinates(final PositionBuilder builder, final Position y0, final Position y1,
			final Position y2, final Position y3, final double step) {
		// 1.62 is the default height for player eye positions
		builder.setPolarCoordinates(builder.getPosition().addY(1.62).lookAt(this.target));
	}

}
