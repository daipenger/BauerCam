package me.bauer.BauerCam.Path;

import java.util.ArrayList;

import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Interpolation.CubicInterpolator;
import me.bauer.BauerCam.Interpolation.Interpolator;
import me.bauer.BauerCam.Interpolation.TargetInterpolator;

public final class PathHandler {

	private final static ArrayList<Position> points = new ArrayList<Position>();
	private final static ArrayList<IPathChangeListener> listeners = new ArrayList<IPathChangeListener>();
	private static Vector3D target;
	private static ActivePath activePath = null;
	private static boolean preview = true;

	// Additional path properties

	public static void setTarget(final Vector3D target) {
		PathHandler.target = target;
	}

	public static void removeTarget() {
		PathHandler.target = null;
	}

	public static boolean hasTarget() {
		return target != null;
	}

	public static void switchPreview() {
		preview = !preview;
	}

	public static boolean showPreview() {
		return preview && activePath == null;
	}

	// End of path properties

	// Travel control

	public static void startTravelling(final long frames) {
		final long iterations = frames * Utils.renderPhases;
		final Interpolator interpolator = target == null
				? new Interpolator(getWaypoints(), CubicInterpolator.instance, CubicInterpolator.instance,
						CubicInterpolator.instance)
				: new Interpolator(getWaypoints(), CubicInterpolator.instance, new TargetInterpolator(target),
						CubicInterpolator.instance);

		activePath = new ActiveInterpolatorPath(interpolator, iterations);
	}

	public static void stopTravelling() {
		activePath = null;
	}

	public static boolean isTravelling() {
		return activePath != null;
	}

	// End of travel control

	// Auxiliary methods

	public static void tick() {
		if (isTravelling()) {
			activePath.tick();
		}
	}

	public static void addPathChangeListener(final IPathChangeListener listener) {
		listeners.add(listener);
	}

	private static void pushChange() {
		for (final IPathChangeListener o : listeners) {
			o.onPathChange();
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
		pushChange();
	}

	public static Position[] getWaypoints() {
		return points.toArray(new Position[points.size()]);
	}

	public static void clearWaypoints() {
		points.clear();
		pushChange();
	}

	public static void addWaypoint(final Position pos) {
		points.add(pos);
		pushChange();
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
		pushChange();
		return true;
	}

	public static boolean remove(final int index) {
		if (isInBounds(index)) {
			points.remove(index);
			pushChange();
			return true;
		}
		return false;
	}

	public static boolean insert(final Position position, final int index) {
		if (isInBounds(index)) {
			points.add(index, position);
			pushChange();
			return true;
		}
		return false;
	}

	public static boolean replace(final Position position, final int index) {
		if (isInBounds(index)) {
			points.set(index, position);
			pushChange();
			return true;
		}
		return false;
	}

	public static int getWaypointSize() {
		return points.size();
	}

	// End of waypoints

}
