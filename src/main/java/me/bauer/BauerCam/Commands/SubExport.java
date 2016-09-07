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

public class SubExport extends ASubExportImport {

	@Override
	protected void derivedExecute(final String filename) {
		final Position[] points = PathHandler.getWaypoints();
		if (points.length == 0) {
			Utils.sendInformation(Main.pathIsEmpty.toString());
			return;
		}

		final File file = new File(Main.bauercamDirectory, filename + extension);

		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));

			for (final Position point : points) {
				writer.write(point.toString());
				writer.newLine();
			}

			Utils.sendInformation(Main.exportSuccessful.toString());
		} catch (final IOException e) {
			Utils.sendInformation(Main.IOError.toString());
			Utils.sendInformation(e.getMessage());
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
		return "export";
	}

	@Override
	public String getDescription() {
		return "/cam export <filename>";
	}

}
