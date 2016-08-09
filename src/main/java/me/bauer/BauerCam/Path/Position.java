package me.bauer.BauerCam.Path;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;

public final class Position extends Vector3D {

	public final float pitch;
	public final float yaw;
	public final float roll;
	public final float fov;

	public Position(final double x, final double y, final double z, final float pitch, final float yaw,
			final float roll, final float fov) {
		super(x, y, z);
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
		this.fov = fov;
	}

	@Override
	public String toString() {
		return super.toString() + padding + this.pitch + padding + this.yaw + padding + this.roll + padding + this.fov;
	}

	public static Position fromString(final String input) {
		final String error = Main.positionCannotBeParsed + input;

		final String[] parts = input.split(padding);
		if (parts.length != 7) {
			Utils.sendInformation(error);
			return null;
		}

		try {
			final double x = Double.parseDouble(parts[0]);
			final double y = Double.parseDouble(parts[1]);
			final double z = Double.parseDouble(parts[2]);
			final float pitch = Float.parseFloat(parts[3]);
			final float yaw = Float.parseFloat(parts[4]);
			final float roll = Float.parseFloat(parts[5]);
			final float fov = Float.parseFloat(parts[6]);

			return new Position(x, y, z, pitch, yaw, roll, fov);
		} catch (final NumberFormatException e) {
			Utils.sendInformation(error);
			return null;
		}
	}

}
