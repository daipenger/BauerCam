package me.bauer.BauerCam;

import org.lwjgl.opengl.GL11;

import me.bauer.BauerCam.Interpolation.CubicInterpolator;
import me.bauer.BauerCam.Interpolation.IAdditionalAngleInterpolator;
import me.bauer.BauerCam.Interpolation.IPolarCoordinatesInterpolator;
import me.bauer.BauerCam.Interpolation.Interpolator;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public final class EventListener {

	@SubscribeEvent
	public void onRender(final RenderWorldLastEvent e) {
		if (PathHandler.isTravelling() || !PathHandler.showPreview() || PathHandler.getWaypointSize() < 2) {
			return;
		}

		final Entity renderEntity = Utils.mc.getRenderViewEntity();
		final float partial = e.getPartialTicks();
		final double renderX = renderEntity.lastTickPosX + (renderEntity.posX - renderEntity.lastTickPosX) * partial;
		final double renderY = renderEntity.lastTickPosY + (renderEntity.posY - renderEntity.lastTickPosY) * partial;
		final double renderZ = renderEntity.lastTickPosZ + (renderEntity.posZ - renderEntity.lastTickPosZ) * partial;

		final Position[] path = PathHandler.getWaypoints();
		// TODO: Make dependent of distances between points
		final int iterations = path.length * 20;
		final Interpolator interpolater = new Interpolator(path, CubicInterpolator.instance,
				IPolarCoordinatesInterpolator.dummy, IAdditionalAngleInterpolator.dummy);

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

		GL11.glTranslated(-renderX, -renderY, -renderZ);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(2.5f);
		GL11.glColor3ub((byte) 255, (byte) 50, (byte) 50);

		Position prev = interpolater.getPoint(0);

		GL11.glBegin(GL11.GL_LINES);
		for (int i = 0; i < iterations;) {
			i++;
			final Position next = interpolater.getPoint((double) i / iterations);
			GL11.glVertex3d(prev.x, prev.y, prev.z);
			GL11.glVertex3d(next.x, next.y, next.z);
			prev = next;
		}
		GL11.glEnd();

		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@SubscribeEvent
	public void onKeyInput(final KeyInputEvent e) {
		if (PathHandler.isTravelling()) {
			return;
		}

		if (Main.point.isPressed()) {
			final Position playerPos = Utils.getPosition();
			PathHandler.addWaypoint(playerPos);
			Utils.sendInformation(Main.pathAdd + " " + PathHandler.getWaypointSize());
		}

		if (Main.cameraReset.isPressed()) {
			CameraRoll.reset();
		}

		if (Main.fovReset.isPressed()) {
			DynamicFOV.reset();
		}
	}

	@SubscribeEvent
	public void onTick(final ClientTickEvent e) {
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
