package me.bauer.BauerCam.Commands;

import net.minecraft.command.CommandException;

public abstract class ASubExportImport implements ISubCommand {

	protected static final String extension = ".txt";

	@Override
	public final void execute(final String[] args) throws CommandException {
		if (args.length == 1) {
			throw new CommandException(getDescription(), new Object[0]);
		}
		derivedExecute(args[1]);
	}

	protected abstract void derivedExecute(String filename);

}
