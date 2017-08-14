package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Utils;
import net.minecraft.util.text.TextFormatting;

public abstract class ASubExportImport implements ISubCommand {

	protected static final String extension = ".txt";

	@Override
	public final void execute(final String[] args) {
		if (args.length == 1) {
			Utils.sendInformation(getDescription(), TextFormatting.RED);
			return;
		}

		derivedExecute(args[1]);
	}

	protected abstract void derivedExecute(String filename);

}
