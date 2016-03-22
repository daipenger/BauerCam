package me.bauer.BauerCam;

import me.bauer.BauerCam.Path.PathHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public final class EventListener {

	private static final float anglePerKeyPress = (float) Math.toRadians(2.5);

	@SubscribeEvent
	public void onTick(final TickEvent e) {
		if (PathHandler.isTravelling()) {
			return;
		}

		if (Main.cameraClock.isKeyDown()) {
			CameraRoll.roll += anglePerKeyPress;
		}

		if (Main.cameraCounterClock.isKeyDown()) {
			CameraRoll.roll -= anglePerKeyPress;
		}

		if (Main.point.isPressed()) {
			Utils.addPosition();
		}

		if (Main.cameraReset.isPressed()) {
			CameraRoll.roll = 0;
		}
	}

	@SubscribeEvent
	public void onRender(final RenderTickEvent e) {
		PathHandler.tick();
	}

	/**
	 * Replaced with core mod for optifine support
	 */
	/**
	 * @SubscribeEvent public void
	 *                 onOrientCamera(EntityViewRenderEvent.CameraSetup e) {
	 *                 e.roll = CameraRoll.roll; }
	 */

}
