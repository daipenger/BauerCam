package me.bauer.BauerCam.Commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;
import net.minecraft.command.CommandException;

public abstract class ASubExportImport implements ISubCommand {

	@Override
	public final void execute(final String[] args) throws CommandException {
		if (args.length == 1) {
			throw new CommandException(getDescription(), new Object[0]);
		}
		derivedExecute(args[1]);
	}

	public abstract void derivedExecute(String filename);

	private static final String extension = ".txt";

	public void save(final String fileName) {
		final Position[] points = PathHandler.getWaypoints();
		if (points.length == 0) {
			Utils.sendInformation(Main.pathIsEmpty.toString());
			return;
		}

		final File file = new File(Main.bauercamDirectory, fileName + extension);

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

	public void load(final String fileName) {
		final File file = new File(Main.bauercamDirectory, fileName + extension);

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

}
