package me.bauer.BauerCam.Path;

import java.util.ArrayList;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Interpolation.CubicInterpolator;
import me.bauer.BauerCam.Interpolation.Interpolator;
import me.bauer.BauerCam.Interpolation.TargetInterpolator;

public final class PathHandler {

	private final static ArrayList<Position> points = new ArrayList<Position>();
	private static Vector3D target;
	private static ActivePath currentPath = null;

	// Additional path properties

	public static void setTarget(Vector3D target) {
		PathHandler.target = target;
	}

	public static void removeTarget() {
		PathHandler.target = null;
	}

	// End of path properties

	// Travel control

	public static void startTravelling(final long frames) {
		final long iterations = frames * Utils.renderPhases;
		final Interpolator interpolator = target == null
				? new Interpolator(points, CubicInterpolator.instance, CubicInterpolator.instance,
						CubicInterpolator.instance)
				: new Interpolator(points, CubicInterpolator.instance, new TargetInterpolator(target),
						CubicInterpolator.instance);

		currentPath = new ActiveInterpolatorPath(interpolator, iterations);
		Utils.sendInformation(Main.pathStarted.toString());
	}

	public static void stopTravelling() {
		currentPath = null;
		Utils.sendInformation(Main.pathStopped.toString());
	}

	public static boolean isTravelling() {
		return currentPath != null;
	}

	// End of travel control

	// Auxiliary methods

	public static void tick() {
		if (isTravelling()) {
			currentPath.tick();
		}
	}

	private static boolean isInBounds(final int index) {
		return index > -1 && index < points.size();
	}

	// End of auxiliary methods

	// Waypoints

	public static Position[] getWaypoints() {
		return points.toArray(new Position[points.size()]);
	}

	public static void setWaypoints(final ArrayList<Position> points) {
		PathHandler.points.clear();
		PathHandler.points.addAll(points);
	}

	public static void addWaypoint(final Position pos) {
		points.add(pos);
		Utils.sendInformation(Main.pathAdd + " " + getWaypointCount());
	}

	public static Position getWaypoint(final int index) {
		if (isInBounds(index)) {
			return points.get(index);
		}
		return null;
	}

	public static void removeLastWaypoint() {
		if (points.isEmpty()) {
			Utils.sendInformation(Main.pathIsEmpty.toString());
			return;
		}
		points.remove(points.size() - 1);
		Utils.sendInformation(Main.pathUndo.toString());
	}

	public static void replace(final Position position, final int index) {
		if (isInBounds(index)) {
			points.set(index, position);
			Utils.sendInformation(Main.pathReplace + " " + (index + 1));
			return;
		}
		Utils.sendInformation(Main.pathDoesNotExist.toString());
	}

	public static void clearWaypoints() {
		points.clear();
		Utils.sendInformation(Main.pathReset.toString());
	}

	public static int getWaypointCount() {
		return points.size();
	}

	// End of waypoints

}
