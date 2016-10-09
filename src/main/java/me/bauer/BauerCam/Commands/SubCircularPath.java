package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;
import me.bauer.BauerCam.Path.Vector3D;
import net.minecraft.command.CommandException;

public class SubCircularPath implements ISubCommand {

	private static final double sqrt2_2 = Math.sqrt(2) / 2;

	@Override
	public void execute(final String[] args) throws CommandException {
		if (args.length < 3) {
			throw new CommandException(getDescription(), new Object[0]);
		}

		if (PathHandler.getWaypointSize() != 0) {
			throw new CommandException(Main.pathIsPopulated.toString(), new Object[0]);
		}

		try {
			final double radius = Double.parseDouble(args[1]);
			final int circles = Integer.parseInt(args[2]);

			final Position playerPos = Utils.getPosition();

			final Vector3D[] circleGrid = { new Vector3D(1, 0, 0), new Vector3D(sqrt2_2, 0, sqrt2_2),
					new Vector3D(0, 0, 1), new Vector3D(-sqrt2_2, 0, sqrt2_2), new Vector3D(-1, 0, 0),
					new Vector3D(-sqrt2_2, 0, -sqrt2_2), new Vector3D(0, 0, -1), new Vector3D(sqrt2_2, 0, -sqrt2_2) };

			for (int i = 0; i < circleGrid.length; i++) {
				circleGrid[i] = circleGrid[i].multiply(radius);
			}

			for (int i = 0; i < circles; i++) {
				for (final Vector3D point : circleGrid) {
					PathHandler.addWaypoint(new Position(playerPos.x + point.x, playerPos.y, playerPos.z + point.z,
							playerPos.pitch, playerPos.yaw, playerPos.roll, playerPos.fov));
				}
			}

			Utils.sendInformation(Main.pathCircleCreated.toString());
		} catch (final NumberFormatException e) {
			throw new CommandException(getDescription(), new Object[0]);
		}
	}

	@Override
	public String getBase() {
		return "circle";
	}

	@Override
	public String getDescription() {
		return "/cam circle <radius> <circles>";
	}

}
