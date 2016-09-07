package me.bauer.BauerCam.Commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;

public class SubImport extends ASubExportImport {

	@Override
	protected void derivedExecute(final String filename) {
		final File file = new File(Main.bauercamDirectory, filename + extension);

		if (!file.exists() || !file.isFile()) {
			Utils.sendInformation(Main.fileDoesNotExist.toString());
			return;
		}

		final ArrayList<Position> points = new ArrayList<Position>();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

			String s;
			while ((s = reader.readLine()) != null) {
				final Position point = Position.fromString(s);
				if (point == null) {
					reader.close();
					return;
				}
				points.add(point);
			}

			PathHandler.setWaypoints(points);
			Utils.sendInformation(Main.importSuccessful.toString());
		} catch (final IOException e) {
			Utils.sendInformation(Main.IOError.toString());
			Utils.sendInformation(e.getMessage());
		}

		if (reader == null) {
			return;
		}
		try {
			reader.close();
		} catch (final IOException e) {
		}
	}

	@Override
	public String getBase() {
		return "import";
	}

	@Override
	public String getDescription() {
		return "/cam import <filename>";
	}

}
