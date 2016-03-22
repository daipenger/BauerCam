package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Path.PathHandler;
import net.minecraft.command.CommandException;

public class SubStop implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		PathHandler.stopTravelling();
	}

	@Override
	public String getBase() {
		return "stop";
	}

	@Override
	public String getDescription() {
		return "/cam stop";
	}

}
