package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public final class CamCommand extends CommandBase {

	private static final ISubCommand[] commands = { new SubStart(), new SubStop(), new SubGoto(), new SubInsert(),
			new SubRemove(), new SubReplace(), new SubUndo(), new SubClear(), new SubSave(), new SubLoad(),
			new SubTarget(), new SubCircle(), new SubPreview() };

	@Override
	public String getName() {
		return "cam";
	}

	@Override
	public String getUsage(final ICommandSender sender) {
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
	public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args)
			throws CommandException {
		if (sender != Utils.mc.player) {
			throw new CommandException(Main.commandHasToBePlayer.toString(), new Object[0]);
		}
		if (args.length == 0) {
			throw new CommandException(getUsage(sender), new Object[0]);
		}

		final String base = args[0].toLowerCase();

		for (final ISubCommand c : commands) {
			if (c.getBase().equals(base)) {
				c.execute(args);
				return;
			}
		}

		throw new CommandException(getUsage(sender), new Object[0]);
	}

}
