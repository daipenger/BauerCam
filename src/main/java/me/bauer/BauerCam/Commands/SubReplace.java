package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import net.minecraft.util.text.TextFormatting;

public class SubReplace implements ISubCommand {

	@Override
	public void execute(final String[] args) {
		if (args.length == 1) {
			Utils.sendInformation(getDescription(), TextFormatting.RED);
			return;
		}

		try {
			final int index = Integer.parseInt(args[1]) - 1;

			if (PathHandler.replace(Utils.getPosition(), index)) {
				Utils.sendInformation(Main.pathReplace.toString() + (index + 1));
			} else {
				Utils.sendInformation(Main.pathDoesNotExist.toString(), TextFormatting.YELLOW);
			}
		} catch (final NumberFormatException e) {
			Utils.sendInformation(Main.pathDoesNotExist.toString(), TextFormatting.YELLOW);
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
