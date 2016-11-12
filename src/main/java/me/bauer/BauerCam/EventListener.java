package me.bauer.BauerCam;

import org.lwjgl.opengl.GL11;

import me.bauer.BauerCam.Interpolation.CubicInterpolator;
import me.bauer.BauerCam.Interpolation.IAdditionalAngleInterpolator;
import me.bauer.BauerCam.Interpolation.IPolarCoordinatesInterpolator;
import me.bauer.BauerCam.Interpolation.Interpolator;
import me.bauer.BauerCam.Path.IPathChangeListener;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public final class EventListener implements IPathChangeListener {

	public static final EventListener instance = new EventListener();
	/**
	 * Describes how many lines per block intersection should be drawn
	 */
	private static final double previewFineness = 2.5;

	private Position[] previewPoints;

	/**
	 * Use {@link EventListener#instance}
	 */
	private EventListener() {
		this.previewPoints = null;
		PathHandler.addPathChangeListener(this);
	}

	@Override
	public void onPathChange() {
		if (PathHandler.getWaypointSize() > 1) {
			final Position[] path = PathHandler.getWaypoints();
			final Interpolator interpolater = new Interpolator(path, CubicInterpolator.instance,
					IPolarCoordinatesInterpolator.dummy, IAdditionalAngleInterpolator.dummy);

			double distances = 0;

			Position prev = path[0];

			// The use of direct distances instead of the actual interpolated
			// slope means that there will always be less drawn lines per block,
			// however this is a good enough approximation

			for (int i = 1; i < path.length; i++) {
				final Position next = path[i];
				distances += prev.distance(next);
				prev = next;
			}

			int iterations = (int) (distances * previewFineness + 0.5);
			// Snap to next mod 2
			iterations += iterations & 1;

			this.previewPoints = new Position[iterations];
			for (int i = 0; i < iterations; i++) {
				this.previewPoints[i] = interpolater.getPoint((double) i / (iterations - 1));
			}

		} else {
			this.previewPoints = null;
		}
	}

	@SubscribeEvent
	public void onRender(final RenderWorldLastEvent e) {
		if (this.previewPoints == null || !PathHandler.showPreview()) {
			return;
		}

		final Entity renderEntity = Utils.mc.getRenderViewEntity();
		final float partial = e.getPartialTicks();
		final double renderX = renderEntity.lastTickPosX + (renderEntity.posX - renderEntity.lastTickPosX) * partial;
		final double renderY = renderEntity.lastTickPosY + (renderEntity.posY - renderEntity.lastTickPosY) * partial;
		final double renderZ = renderEntity.lastTickPosZ + (renderEntity.posZ - renderEntity.lastTickPosZ) * partial;

		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();

		GlStateManager.translate(-renderX, -renderY, -renderZ);
		GlStateManager.disableLighting();
		GlStateManager.disableTexture2D();
		GlStateManager.glLineWidth(5f);
		GlStateManager.color(1.0f, 0.2f, 0.2f);

		GL11.glBegin(GL11.GL_LINES);
		for (int i = 0; i < (this.previewPoints.length - 1); i++) {
			final Position prev = this.previewPoints[i];
			final Position next = this.previewPoints[i + 1];
			GL11.glVertex3d(prev.x, prev.y, prev.z);
			GL11.glVertex3d(next.x, next.y, next.z);
		}
		GL11.glEnd();

		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
	}

	@SubscribeEvent
	public void onKeyInput(final KeyInputEvent e) {
		if (PathHandler.isTravelling()) {
			return;
		}

		if (Main.point.isPressed()) {
			final Position playerPos = Utils.getPosition();
			PathHandler.addWaypoint(playerPos);
			Utils.sendInformation(Main.pathAdd.toString() + PathHandler.getWaypointSize());
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
