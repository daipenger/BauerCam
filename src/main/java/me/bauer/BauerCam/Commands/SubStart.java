package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import net.minecraft.command.CommandException;

public class SubStart implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		if (args.length == 1) {
			throw new CommandException(getDescription(), new Object[0]);
		}
		final long frames = Utils.parseSafely(args[1], 0);
		if (frames <= 0) {
			throw new CommandException("Frames may not be zero or less", new Object[0]);
		}
		if (PathHandler.getWaypointCount() <= 1) {
			throw new CommandException("You have to set at least two points to start travelling", new Object[0]);
		}
		PathHandler.startTravelling(frames);
	}

	@Override
	public String getBase() {
		return "start";
	}

	@Override
	public String getDescription() {
		return "/cam start <frames>";
	}

}
