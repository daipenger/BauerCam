package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import net.minecraft.command.CommandException;

public class SubReplace implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		if (args.length == 1) {
			throw new CommandException("Usage: /cam replace <point>", new Object[0]);
		}
		try {
			final int index = Integer.parseInt(args[1]) - 1;
			PathHandler.replace(Utils.getPosition(Utils.mc.thePlayer), index);
		} catch (final NumberFormatException e) {
			throw new CommandException(args[1] + " is not a valid point", new Object[0]);
		}
	}

	@Override
	public String getBase() {
		return "replace";
	}

	@Override
	public String getDescription() {
		return "/cam replace <point>";
	}

}
