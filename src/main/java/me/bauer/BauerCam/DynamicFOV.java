package me.bauer.BauerCam;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;

public final class DynamicFOV {

	private static final GameSettings settings = Utils.mc.gameSettings;

	/**
	 * Extends default of {@link Options#FOV#getValueMax()}
	 */
	private static final float upperBound = 150;
	/**
	 * Extends default of {@link Options#FOV#getValueMin()}
	 */
	private static final float lowerBound = 5;
	/**
	 * Should be 70
	 */
	private static final float defaultFOV = (Options.FOV.getValueMax() + Options.FOV.getValueMin()) / 2;

	private static final float fovPerKeyPress = 0.25f;

	public static void increase() {
		settings.fovSetting += fovPerKeyPress;
		verify();
	}

	public static void decrease() {
		settings.fovSetting -= fovPerKeyPress;
		verify();
	}

	public static void reset() {
		settings.fovSetting = defaultFOV;
	}

	public static void set(float fov) {
		settings.fovSetting = fov;
		verify();
	}

	public static float get() {
		return settings.fovSetting;
	}

	private static void verify() {
		if (settings.fovSetting > upperBound) {
			settings.fovSetting = upperBound;
		} else if (settings.fovSetting < lowerBound) {
			settings.fovSetting = lowerBound;
		}
	}

}
