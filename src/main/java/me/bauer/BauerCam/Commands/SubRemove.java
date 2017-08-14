package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import net.minecraft.util.text.TextFormatting;

public class SubRemove implements ISubCommand {

	@Override
	public void execute(final String[] args) {
		if (args.length == 1) {
			Utils.sendInformation(getDescription(), TextFormatting.RED);
			return;
		}

		try {
			final int index = Integer.parseInt(args[1]) - 1;

			if (PathHandler.remove(index)) {
				Utils.sendInformation(Main.pathPointRemoved.toString() + (index + 1));
			} else {
				Utils.sendInformation(Main.pathDoesNotExist.toString(), TextFormatting.YELLOW);
			}
		} catch (final NumberFormatException e) {
			Utils.sendInformation(Main.pathDoesNotExist.toString(), TextFormatting.YELLOW);
		}
	}

	@Override
	public String getBase() {
		return "remove";
	}

	@Override
	public String getDescription() {
		return "/cam remove <point>";
	}

}
