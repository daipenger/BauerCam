package me.bauer.BauerCam;

import org.lwjgl.input.Keyboard;

import me.bauer.BauerCam.Commands.CamCommand;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = Main.modId, version = Main.version, clientSideOnly = true, acceptedMinecraftVersions = Main.minecraftTargetVersion)
public final class Main {

	public static final String modId = "BauerCam";
	public static final String version = "1.0";
	public static final String minecraftTargetVersion = "1.8.9";

	public final static KeyBinding point = new KeyBinding("Add path point", Keyboard.KEY_P, modId);
	public final static KeyBinding cameraClock = new KeyBinding("Roll clockwise", Keyboard.KEY_L, modId);
	public final static KeyBinding cameraCounterClock = new KeyBinding("Roll counterclockwise", Keyboard.KEY_J, modId);
	public final static KeyBinding cameraReset = new KeyBinding("Reset Roll", Keyboard.KEY_K, modId);

	@Mod.EventHandler
	public void init(final FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new EventListener());
		ClientCommandHandler.instance.registerCommand(new CamCommand());
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent event) {
		ClientRegistry.registerKeyBinding(point);
		ClientRegistry.registerKeyBinding(cameraClock);
		ClientRegistry.registerKeyBinding(cameraCounterClock);
		ClientRegistry.registerKeyBinding(cameraReset);
	}

}
