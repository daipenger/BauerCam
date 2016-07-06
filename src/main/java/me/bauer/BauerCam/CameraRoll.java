package me.bauer.BauerCam;

import org.lwjgl.opengl.GL11;

public final class CameraRoll {

	private static final float anglePerKeyPress = (float) Math.toRadians(2.5);

	public static float roll = 0;

	public static void rotateClockWise() {
		roll += anglePerKeyPress;
	}

	public static void rotateCounterClockWise() {
		roll -= anglePerKeyPress;
	}

	public static void reset() {
		roll = 0;
	}

	public static void applyRoll() {
		// Do not explicitly set roll to 0 (when the player is hurt for example
		// minecraft uses roll
		if (roll == 0) {
			return;
		}
		GL11.glRotatef(roll, 0, 0, 1);
	}

}
