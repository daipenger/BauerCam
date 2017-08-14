package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;

public class SubClear implements ISubCommand {

	@Override
	public void execute(final String[] args) {
		PathHandler.clearWaypoints();
		Utils.sendInformation(Main.pathReset.toString());
		if (PathHandler.hasTarget()) {
			Utils.sendInformation(Main.pathResetBewareTarget.toString());
		}
	}

	@Override
	public String getBase() {
		return "clear";
	}

	@Override
	public String getDescription() {
		return "/cam clear";
	}

}
