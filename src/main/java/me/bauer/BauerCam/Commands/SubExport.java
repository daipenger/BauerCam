package me.bauer.BauerCam.Commands;

import net.minecraft.command.CommandException;

public class SubExport extends ASubExportImport {

	@Override
	public void execute(final String[] args) throws CommandException {
		if (args.length == 1) {
			throw new CommandException(getDescription(), new Object[0]);
		}
		save(args[1]);
	}

	@Override
	public String getBase() {
		return "export";
	}

	@Override
	public String getDescription() {
		return "/cam export <filename>";
	}

}
