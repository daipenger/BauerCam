package me.bauer.BauerCam.Path;

import java.util.ArrayList;

import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Interpolation.CubicInterpolator;
import me.bauer.BauerCam.Interpolation.Interpolator;
import me.bauer.BauerCam.Interpolation.TargetInterpolator;

public final class PathHandler {

	private final static ArrayList<Position> points = new ArrayList<Position>();
	private static Vector3D target;
	private static ActivePath currentPath = null;

	// Additional path properties

	public static void setTarget(final Vector3D target) {
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
	}

	public static void stopTravelling() {
		currentPath = null;
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

	public static void setWaypoints(final ArrayList<Position> points) {
		PathHandler.points.clear();
		PathHandler.points.addAll(points);
	}

	public static Position[] getWaypoints() {
		return points.toArray(new Position[points.size()]);
	}

	public static void clearWaypoints() {
		points.clear();
	}

	public static void addWaypoint(final Position pos) {
		points.add(pos);
	}

	public static Position getWaypoint(final int index) {
		if (isInBounds(index)) {
			return points.get(index);
		}
		return null;
	}

	public static boolean removeLastWaypoint() {
		if (points.isEmpty()) {
			return false;
		}
		points.remove(points.size() - 1);
		return true;
	}

	public static boolean remove(final int index) {
		if (isInBounds(index)) {
			points.remove(index);
			return true;
		}
		return false;
	}

	public static boolean insert(final Position position, final int index) {
		if (isInBounds(index)) {
			points.add(index, position);
			return true;
		}
		return false;
	}

	public static boolean replace(final Position position, final int index) {
		if (isInBounds(index)) {
			points.set(index, position);
			return true;
		}
		return false;
	}

	public static int getWaypointSize() {
		return points.size();
	}

	// End of waypoints

}
