package me.bauer.BauerCam;

import net.minecraft.client.resources.I18n;

/**
 * Use this class via {@link Object#toString()}
 */
public final class LocalizedString {

	// Sadly cannot cache a key mapping, because there is no event
	// indicating a changed setting

	private final String key;

	public LocalizedString(final String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return I18n.format(this.key);
	}

}
