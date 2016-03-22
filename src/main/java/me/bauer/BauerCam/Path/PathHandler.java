package me.bauer.BauerCam.Path;

import java.util.ArrayList;

import me.bauer.BauerCam.Utils;

public final class PathHandler {

	private final static ArrayList<Position> waypoints = new ArrayList<Position>();
	private static ActivePath currentPath = null;

	public static void startTravelling(final long iterations) {
		currentPath = new ActiveInterpolatorPath(waypoints, iterations);
		Utils.sendInformation("Travelling started");
	}

	public static void stopTravelling() {
		currentPath = null;
		Utils.sendInformation("Travelling finished");
	}

	public static boolean isTravelling() {
		return currentPath != null;
	}

	public static void tick() {
		if (isTravelling()) {
			currentPath.tick();
		}
	}

	private static boolean isInBounds(final int index) {
		return index > -1 && index < waypoints.size();
	}

	public static void addWaypoint(final Position pos) {
		waypoints.add(pos);
		Utils.sendInformation("Added Point " + getWaypointCount());
	}

	public static Position getWaypoint(final int index) {
		if (isInBounds(index)) {
			return waypoints.get(index);
		}
		return null;
	}

	public static void removeLastWaypoint() {
		if (waypoints.isEmpty()) {
			Utils.sendInformation("Path is empty");
			return;
		}
		waypoints.remove(waypoints.size() - 1);
		Utils.sendInformation("Last one removed");
	}

	public static void replace(final Position position, final int index) {
		if (isInBounds(index)) {
			waypoints.set(index, position);
			Utils.sendInformation("Replaced point " + (index + 1));
			return;
		}
		Utils.sendInformation("This point does not exist");
	}

	public static void clearWaypoints() {
		waypoints.clear();
		Utils.sendInformation("Reset performed");
	}

	public static int getWaypointCount() {
		return waypoints.size();
	}

}
