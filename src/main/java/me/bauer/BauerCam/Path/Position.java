package me.bauer.BauerCam.Path;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;

public final class Position {

	public final double x;
	public final double y;
	public final double z;
	public final float pitch;
	public final float yaw;
	public final float roll;

	public Position(final double x, final double y, final double z, final float pitch, final float yaw,
			final float roll) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}

	private static final String pad = ":";

	@Override
	public String toString() {
		return this.x + pad + this.y + pad + this.z + pad + this.pitch + pad + this.yaw + pad + this.roll;
	}

	public static Position fromString(final String input) {
		final String error = Main.positionCannotBeParsed + input;

		final String[] parts = input.split(pad);
		if (parts.length != 6) {
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

			return new Position(x, y, z, pitch, yaw, roll);
		} catch (final NumberFormatException e) {
			Utils.sendInformation(error);
			return null;
		}
	}

}
