package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Path.PathHandler;
import net.minecraft.command.CommandException;

public class SubUndo implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		PathHandler.removeLastWaypoint();
	}

	@Override
	public String getBase() {
		return "undo";
	}

	@Override
	public String getDescription() {
		return "/cam undo";
	}

}
