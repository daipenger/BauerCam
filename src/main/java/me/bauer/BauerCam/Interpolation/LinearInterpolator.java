package me.bauer.BauerCam.Interpolation;

import me.bauer.BauerCam.Path.PolarCoordinates;
import me.bauer.BauerCam.Path.Position;
import me.bauer.BauerCam.Path.Vector3D;

public final class LinearInterpolator
		implements IPositionInterpolator, IPolarCoordinatesInterpolator, IAdditionalAngleInterpolator {

	public static final LinearInterpolator instance = new LinearInterpolator();

	private LinearInterpolator() {
	}

	@Override
	public void interpolatePosition(final PositionBuilder builder, final Position y0, final Position y1,
			final Position y2, final Position y3, final double step) {
		builder.setPosition(new Vector3D(InterpolationUtils.linear(y1.x, y2.x, step),
				InterpolationUtils.linear(y1.y, y2.y, step), InterpolationUtils.linear(y1.z, y2.z, step)));
	}

	@Override
	public void interpolatePolarCoordinates(final PositionBuilder builder, final Position y0, final Position y1,
			final Position y2, final Position y3, final double step) {
		builder.setPolarCoordinates(new PolarCoordinates((float) InterpolationUtils.linear(y1.pitch, y2.pitch, step),
				(float) InterpolationUtils.linear(y1.yaw, y2.yaw, step)));
	}

	@Override
	public void interpolateAdditionAngles(final PositionBuilder builder, final Position y0, final Position y1,
			final Position y2, final Position y3, final double step) {
		builder.setRoll((float) InterpolationUtils.linear(y1.roll, y2.roll, step));
		builder.setFov((float) InterpolationUtils.linear(y1.fov, y2.fov, step));
	}

}
