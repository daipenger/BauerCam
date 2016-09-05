package me.bauer.BauerCam.Interpolation;

import me.bauer.BauerCam.Path.PolarCoordinates;
import me.bauer.BauerCam.Path.Position;
import me.bauer.BauerCam.Path.Vector3D;

public final class CubicInterpolator
		implements PositionInterpolator, PolarCoordinatesInterpolator, AdditionalAngleInterpolator {

	@Override
	public void interpolatePosition(final PositionBuilder builder, final Position y0, final Position y1,
			final Position y2, final Position y3, final double step) {
		builder.setPosition(new Vector3D(InterpolationUtils.cubic_catmull(y0.x, y1.x, y2.x, y3.x, step),
				InterpolationUtils.cubic_catmull(y0.y, y1.y, y2.y, y3.y, step),
				InterpolationUtils.cubic_catmull(y0.z, y1.z, y2.z, y3.z, step)));
	}

	@Override
	public void interpolatePolarCoordinates(final PositionBuilder builder, final Position y0, final Position y1,
			final Position y2, final Position y3, final double step) {
		builder.setPolarCoordinates(
				new PolarCoordinates(InterpolationUtils.cubic(y0.pitch, y1.pitch, y2.pitch, y3.pitch, (float) step),
						InterpolationUtils.cubic(y0.yaw, y1.yaw, y2.yaw, y3.yaw, (float) step)));
	}

	@Override
	public void interpolateAdditionAngles(final PositionBuilder builder, final Position y0, final Position y1,
			final Position y2, final Position y3, final double step) {
		builder.setRoll(InterpolationUtils.cubic(y0.roll, y1.roll, y2.roll, y3.roll, (float) step));
		builder.setFov(InterpolationUtils.cubic(y0.fov, y1.fov, y2.fov, y3.fov, (float) step));
	}

}
