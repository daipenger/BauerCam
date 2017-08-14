package me.bauer.BauerCam.Commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;
import net.minecraft.util.text.TextFormatting;

public class SubSave extends ASubExportImport {

	@Override
	protected void derivedExecute(final String filename) {
		final Position[] points = PathHandler.getWaypoints();
		if (points.length == 0) {
			Utils.sendInformation(Main.pathIsEmpty.toString(), TextFormatting.YELLOW);
			return;
		}

		final File file = new File(Main.bauercamDirectory, filename + extension);

		if (file.isFile()) {
			Utils.sendInformation(Main.fileAlreadyExists.toString(), TextFormatting.YELLOW);
			return;
		}

		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));

			for (final Position point : points) {
				writer.write(point.toString());
				writer.newLine();
			}

			Utils.sendInformation(Main.exportSuccessful + file.getAbsolutePath());
		} catch (final IOException e) {
			Utils.sendInformation(Main.IOError.toString(), TextFormatting.RED);
			Utils.sendInformation(e.getMessage(), TextFormatting.YELLOW);
		}

		if (writer == null) {
			return;
		}
		try {
			writer.close();
		} catch (final IOException e) {
		}
	}

	@Override
	public String getBase() {
		return "save";
	}

	@Override
	public String getDescription() {
		return "/cam save <filename>";
	}

}
