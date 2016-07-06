package me.bauer.BauerCam.Interpolation;

import java.util.List;

import me.bauer.BauerCam.Path.Position;

public interface IInterpolatorFactory {

	public Interpolator getInterpolator(List<Position> points);

}
