package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import net.minecraft.command.CommandException;

public class SubPreview implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		PathHandler.switchPreview();
		if (PathHandler.showPreview()) {
			Utils.sendInformation(Main.renderPreviewOn.toString());
		} else {
			Utils.sendInformation(Main.renderPreviewOff.toString());
		}
	}

	@Override
	public String getBase() {
		return "preview";
	}

	@Override
	public String getDescription() {
		return "/cam preview";
	}

}
