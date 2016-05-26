package me.bauer.BauerCam;

import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public final class Utils {

	public static final Minecraft mc = Minecraft.getMinecraft();

	public static Position getPosition(final EntityPlayer player) {
		// correcting yaw angle is not wanted for a fully functional
		// interpolation (see teleport method for more information on position
		// handling)
		return new Position(player.posX, player.posY, player.posZ, player.rotationPitch, player.rotationYaw,
				CameraRoll.roll);
	}

	public static void addPosition() {
		final EntityPlayerSP player = mc.thePlayer;
		if (player == null) {
			return;
		}
		PathHandler.addWaypoint(getPosition(player));
	}

	/**
	 * @param pos
	 *            Position to teleport the player to
	 * @param force
	 *            should be true when teleporting over a presumably large
	 *            distance
	 */
	public static void teleport(final Position pos, final boolean force) {
		final EntityPlayerSP player = mc.thePlayer;
		if (player == null) {
			return;
		}
		// This procedure here is crucial! When not done properly (eg.
		// setPositionAndRotation is not properly) it can lead to
		// spinning camera movement (probably yaw angle which may incorrectly be
		// bounded
		// inside -180 and 180 degrees)
		// FUN FACT: PixelCam had the same issue!
		// FUN FACT 2: setLocationAndAngles solves this but instead results in
		// desync
		// with
		// the server when the client is teleported inside an unloaded chunk on
		// the client side (however this did not quite occur when just using
		// /cam goto)
		// Workaround: Sending a teleport command
		// This whole comment is hilarious
		if (force) {
			final String tpCommand = "/tp " + (int) pos.x + " " + (int) pos.y + " " + (int) pos.z;
			player.sendChatMessage(tpCommand);
		}
		player.setLocationAndAngles(pos.x, pos.y, pos.z, pos.yaw, pos.pitch);
		CameraRoll.roll = pos.roll;
	}

	public static void sendInformation(final String msg) {
		final EntityPlayerSP player = mc.thePlayer;
		if (player == null) {
			return;
		}
		player.addChatMessage(new TextComponentString(msg));
	}

	public static int parseSafely(final String input, final int def) {
		try {
			return Integer.parseInt(input);
		} catch (final NumberFormatException e) {
			return def;
		}
	}

	public static double parseSafely(final String input, final double def) {
		try {
			return Double.parseDouble(input);
		} catch (final NumberFormatException e) {
			return def;
		}
	}

}
