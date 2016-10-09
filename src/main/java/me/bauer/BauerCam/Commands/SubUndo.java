package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import net.minecraft.command.CommandException;

public class SubUndo implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		if (PathHandler.removeLastWaypoint()) {
			Utils.sendInformation(Main.pathUndo.toString());
		} else {
			Utils.sendInformation(Main.pathIsEmpty.toString());
		}
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
