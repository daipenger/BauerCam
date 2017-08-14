package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import net.minecraft.util.text.TextFormatting;

public class SubStart implements ISubCommand {

	@Override
	public void execute(final String[] args) {
		if (args.length == 1) {
			Utils.sendInformation(getDescription(), TextFormatting.RED);
			return;
		}

		final long frames = Utils.parseSafely(args[1], 0);
		if (frames <= 0) {
			Utils.sendInformation(Main.commandInvalidFrames.toString(), TextFormatting.YELLOW);
			return;
		}
		if (PathHandler.getWaypointSize() <= 1) {
			Utils.sendInformation(Main.commandAtLeastTwoPoints.toString(), TextFormatting.YELLOW);
			return;
		}

		PathHandler.startTravelling(frames);
		Utils.sendInformation(Main.pathStarted.toString());
	}

	@Override
	public String getBase() {
		return "start";
	}

	@Override
	public String getDescription() {
		return "/cam start <frames>";
	}

}
