package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public final class CamCommand extends CommandBase {

	private static final ISubCommand[] commands = { new SubStart(), new SubStop(), new SubGoto(), new SubReplace(),
			new SubUndo(), new SubClear(), new SubExport(), new SubImport() };

	@Override
	public String getCommandName() {
		return "cam";
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		final StringBuilder s = new StringBuilder();
		s.append(Main.commands);
		for (final ISubCommand c : commands) {
			s.append("\n");
			s.append(c.getDescription());
		}
		return s.toString();
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (sender != Utils.mc.thePlayer) {
			throw new CommandException(Main.commandHasToBePlayer.toString(), new Object[0]);
		}
		if (args.length == 0) {
			throw new CommandException(getCommandUsage(sender), new Object[0]);
		}

		final String base = args[0].toLowerCase();

		for (final ISubCommand c : commands) {
			if (c.getBase().equals(base)) {
				c.execute(args);
				return;
			}
		}

		throw new CommandException(getCommandUsage(sender), new Object[0]);
	}

}
