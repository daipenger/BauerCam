package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import net.minecraft.command.CommandException;

public class SubInsert implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		if (args.length == 1) {
			throw new CommandException(getDescription(), new Object[0]);
		}

		try {
			final int index = Integer.parseInt(args[1]) - 1;

			if (PathHandler.insert(Utils.getPosition(), index)) {
				Utils.sendInformation(Main.pathPointInserted + " " + (index + 1));
			} else {
				Utils.sendInformation(Main.pathDoesNotExist.toString());
			}
		} catch (final NumberFormatException e) {
			throw new CommandException(Main.pathDoesNotExist.toString(), new Object[0]);
		}
	}

	@Override
	public String getBase() {
		return "insert";
	}

	@Override
	public String getDescription() {
		return "/cam insert <before point>";
	}

}
