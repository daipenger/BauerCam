package me.bauer.BauerCam.CameraRollCoreMod;

import java.util.Map;

import me.bauer.BauerCam.Main;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion(value = Main.minecraftTargetVersion)
public final class CameraRollLoader implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[] { CameraRollInjector.class.getName() };
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(final Map<String, Object> data) {
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
