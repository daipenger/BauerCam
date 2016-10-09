package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Path.PathHandler;
import net.minecraft.command.CommandException;

public class SubCircularPath implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		if (args.length < 3) {
			throw new CommandException(getDescription(), new Object[0]);
		}

		if (PathHandler.getWaypointCount() == 0) {
			// TODO message
			throw new CommandException("", new Object[0]);
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
