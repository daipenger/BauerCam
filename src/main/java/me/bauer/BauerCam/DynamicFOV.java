package me.bauer.BauerCam;

public final class DynamicFOV {

	private static final float fovPerKeyPress = 0.2f;

	public static void increase() {
		Utils.mc.gameSettings.fovSetting += fovPerKeyPress;
	}

	public static void decrease() {
		Utils.mc.gameSettings.fovSetting -= fovPerKeyPress;
	}

	public static void reset() {
		// Hard coded game default
		Utils.mc.gameSettings.fovSetting = 70;
	}

	public static void set(float fov) {
		Utils.mc.gameSettings.fovSetting = fov;
	}

	public static float get() {
		return Utils.mc.gameSettings.fovSetting;
	}

}
