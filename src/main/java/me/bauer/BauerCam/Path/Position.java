package me.bauer.BauerCam.Path;

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

}
