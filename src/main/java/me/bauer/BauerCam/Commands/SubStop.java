package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;

public class SubStop implements ISubCommand {

	@Override
	public void execute(final String[] args) {
		PathHandler.stopTravelling();
		Utils.sendInformation(Main.pathStopped.toString());
	}

	@Override
	public String getBase() {
		return "stop";
	}

	@Override
	public String getDescription() {
		return "/cam stop";
	}

}
