package me.bauer.BauerCam;

import me.bauer.BauerCam.Path.PathHandler;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public final class EventListener {

	@SubscribeEvent
	public void onKeyInput(final KeyInputEvent e) {
		if (PathHandler.isTravelling()) {
			return;
		}

		if (Main.point.isPressed()) {
			Utils.addPosition();
		}

		if (Main.cameraReset.isPressed()) {
			CameraRoll.reset();
		}

		if (Main.fovReset.isPressed()) {
			DynamicFOV.reset();
		}
	}

	@SubscribeEvent
	public void onTick(final TickEvent e) {
		if (PathHandler.isTravelling()) {
			return;
		}

		if (Main.cameraClock.isKeyDown()) {
			CameraRoll.rotateClockWise();
		}

		if (Main.cameraCounterClock.isKeyDown()) {
			CameraRoll.rotateCounterClockWise();
		}

		if (Main.fovHigh.isKeyDown()) {
			DynamicFOV.increase();
		}

		if (Main.fovLow.isKeyDown()) {
			DynamicFOV.decrease();
		}
	}

	@SubscribeEvent
	public void onRender(final RenderTickEvent e) {
		PathHandler.tick();
	}

	@SubscribeEvent
	public void onOrientCamera(final EntityViewRenderEvent.CameraSetup e) {
		// Do not explicitly set roll to 0 (when the player is hurt for example
		// minecraft uses roll)
		if (CameraRoll.roll == 0) {
			return;
		}
		e.setRoll(CameraRoll.roll);
	}

}
