package me.bauer.BauerCam;

public final class CameraRoll {

	private static final float anglePerKeyPress = 2.5f;

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

}
