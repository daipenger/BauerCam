package me.bauer.BauerCam.Path;

public abstract class ActivePath {

	public abstract void tick();

	protected final void stop() {
		PathHandler.stopTravelling();
	}

}
