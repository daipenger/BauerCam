package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;
import me.bauer.BauerCam.Path.Vector3D;
import net.minecraft.util.text.TextFormatting;

public class SubCircle implements ISubCommand {

	private static final double sqrt2_2 = Math.sqrt(2) / 2;

	private static final Vector3D rootPoint = new Vector3D(1, 0, 0);

	private static final Vector3D[] rightMovingCircle = { rootPoint, new Vector3D(sqrt2_2, 0, -sqrt2_2),
			new Vector3D(0, 0, -1), new Vector3D(-sqrt2_2, 0, -sqrt2_2), new Vector3D(-1, 0, 0),
			new Vector3D(-sqrt2_2, 0, sqrt2_2), new Vector3D(0, 0, 1), new Vector3D(sqrt2_2, 0, sqrt2_2) };

	private static final Vector3D[] leftMovingCircle = { rootPoint, new Vector3D(sqrt2_2, 0, sqrt2_2),
			new Vector3D(0, 0, 1), new Vector3D(-sqrt2_2, 0, sqrt2_2), new Vector3D(-1, 0, 0),
			new Vector3D(-sqrt2_2, 0, -sqrt2_2), new Vector3D(0, 0, -1), new Vector3D(sqrt2_2, 0, -sqrt2_2) };

	@Override
	public void execute(final String[] args) {
		if (args.length < 4) {
			Utils.sendInformation(getDescription(), TextFormatting.RED);
			return;
		}
		if (PathHandler.getWaypointSize() != 0) {
			Utils.sendInformation(Main.pathIsPopulated.toString(), TextFormatting.YELLOW);
			return;
		}

		final String direction = args[3].toLowerCase();
		final Vector3D[] circle = "right".equals(direction) ? rightMovingCircle
				: "left".equals(direction) ? leftMovingCircle : null;
		if (circle == null) {
			Utils.sendInformation(getDescription(), TextFormatting.RED);
			return;
		}

		try {
			final double radius = Double.parseDouble(args[1]);
			final int circles = Integer.parseInt(args[2]);

			final Position playerPos = Utils.getPosition();

			for (int i = 0; i < circles; i++) {
				for (final Vector3D point : circle) {
					PathHandler.addWaypoint(
							new Position(playerPos.x + point.x * radius, playerPos.y, playerPos.z + point.z * radius,
									playerPos.pitch, playerPos.yaw, playerPos.roll, playerPos.fov));
				}
			}

			PathHandler.addWaypoint(new Position(playerPos.x + rootPoint.x * radius, playerPos.y,
					playerPos.z + rootPoint.z * radius, playerPos.pitch, playerPos.yaw, playerPos.roll, playerPos.fov));

			Utils.sendInformation(Main.pathCircleCreated.toString());
		} catch (final NumberFormatException e) {
			Utils.sendInformation(getDescription(), TextFormatting.RED);
		}
	}

	@Override
	public String getBase() {
		return "circle";
	}

	@Override
	public String getDescription() {
		return "/cam circle <radius> <circles> <right/left>";
	}

}
