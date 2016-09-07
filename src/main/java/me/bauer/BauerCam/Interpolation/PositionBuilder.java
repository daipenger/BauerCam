package me.bauer.BauerCam.Interpolation;

import me.bauer.BauerCam.Path.PolarCoordinates;
import me.bauer.BauerCam.Path.Position;
import me.bauer.BauerCam.Path.Vector3D;

public final class PositionBuilder {

	private Vector3D position;
	private PolarCoordinates polarCoordinates;
	private float roll;
	private float fov;

	public PositionBuilder setPosition(final Vector3D position) {
		this.position = position;
		return this;
	}

	public PositionBuilder setPolarCoordinates(final PolarCoordinates polarCoordinates) {
		this.polarCoordinates = polarCoordinates;
		return this;
	}

	public PositionBuilder setRoll(final float value) {
		this.roll = value;
		return this;
	}

	public PositionBuilder setFov(final float value) {
		this.fov = value;
		return this;
	}

	public Vector3D getPosition() {
		return this.position;
	}

	public PolarCoordinates getPolarCoordinates() {
		return this.polarCoordinates;
	}

	public Position build() {
		return new Position(this.position.x, this.position.y, this.position.z, this.polarCoordinates.pitch,
				this.polarCoordinates.yaw, this.roll, this.fov);
	}

}
