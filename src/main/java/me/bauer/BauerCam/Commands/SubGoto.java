package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;
import net.minecraft.util.text.TextFormatting;

public class SubGoto implements ISubCommand {

	@Override
	public void execute(final String[] args) {
		if (args.length == 1) {
			Utils.sendInformation(getDescription(), TextFormatting.RED);
			return;
		}

		try {
			final int index = Integer.parseInt(args[1]) - 1;

			final Position pos = PathHandler.getWaypoint(index);
			if (pos == null) {
				throw new NumberFormatException();
			}
			Utils.teleport(pos, true);

			Utils.sendInformation(Main.commandTravelledTo.toString() + (index + 1));
		} catch (final NumberFormatException e) {
			Utils.sendInformation(Main.pathDoesNotExist.toString(), TextFormatting.YELLOW);
		}
	}

	@Override
	public String getBase() {
		return "goto";
	}

	@Override
	public String getDescription() {
		return "/cam goto <point>";
	}

}
