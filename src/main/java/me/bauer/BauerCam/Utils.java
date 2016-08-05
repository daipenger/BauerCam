package me.bauer.BauerCam;

import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public final class Utils {

	public static final Minecraft mc = Minecraft.getMinecraft();
	/**
	 * Describes how often {@link PathHandler#tick()} is called per frame
	 * <p>
	 * calls are made from
	 * {@link EventListener#onRender(net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent)})
	 */
	public static final int renderPhases = Phase.values().length;

	public static Position getPosition(final EntityPlayer player) {
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
		// force tackles desync
		if (force) {
			// teleport command syntax: /tp [target player] <x> <y> <z> [<y-rot>
			// <x-rot>]
			final String tpCommand = "/tp " + pos.x + " " + pos.y + " " + pos.z + " " + pos.yaw + " " + pos.pitch;
			player.sendChatMessage(tpCommand);
		}
		setPositionProperly(player, pos);
		CameraRoll.roll = pos.roll;
	}

	public static void setPositionProperly(final Entity entity, final Position pos) {
		// This procedure here is crucial! When not done properly (eg.
		// setPositionAndRotation is not properly) it can lead to
		// spinning camera movement (probably yaw angle which may incorrectly be
		// bounded inside -180 and 180 degrees)
		// FUN FACT: PixelCam had the same issue!
		// FUN FACT 2: setLocationAndAngles solves this but instead results in
		// desync when setting the position of entities both in the client world
		// and server world
		// -> not loading chunks anymore on the client side
		// Workaround: Send a teleport command
		entity.setLocationAndAngles(pos.x, pos.y, pos.z, pos.yaw, pos.pitch);
		// Prevents inaccurate/wobbly/jerky angle movement (setLocationAndAngles
		// only sets previous values for x,y,z -> partial tick interpolation is
		// still also done for angles by the engine)
		entity.prevRotationYaw = pos.yaw;
		entity.prevRotationPitch = pos.pitch;
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
