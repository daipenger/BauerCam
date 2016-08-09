package me.bauer.BauerCam.Path;

public class Vector3D {

	protected static final String padding = "/";

	public final double x;
	public final double y;
	public final double z;

	public Vector3D(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return this.x + padding + this.y + padding + this.z;
	}

	public final double lengthSquared() {
		return x * x + y * y + z * z;
	}

	public final double length() {
		return Math.sqrt(lengthSquared());
	}

	public Vector3D normalize() {
		double length = length();
		return new Vector3D(x / length, y / length, z / length);
	}

	public Vector3D subtract(Vector3D other) {
		return new Vector3D(x - other.x, y - other.y, z - other.z);
	}

	public PolarCoordinates lookAt(Vector3D target) {
		Vector3D cartesianCoordinates = target.subtract(this).normalize();

		double pitch = Math.asin(cartesianCoordinates.y);
		double yaw = Math.atan2(cartesianCoordinates.z, cartesianCoordinates.x);

		// Into degrees
		pitch = Math.toDegrees(pitch);
		yaw = Math.toDegrees(yaw);

		// Minecraft specific corrections
		pitch = -pitch;
		yaw += 90;

		return new PolarCoordinates((float) pitch, (float) yaw);
	}

}
