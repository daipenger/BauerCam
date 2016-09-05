package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.Main;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Vector3D;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandException;

public class SubTarget implements ISubCommand {

	@Override
	public void execute(String[] args) throws CommandException {
		if (args.length == 1) {
			throw new CommandException(getDescription(), new Object[0]);
		}

		String op = args[1].toLowerCase();

		if ("set".equals(op)) {
			EntityPlayerSP player = Utils.mc.thePlayer;
			Vector3D target = new Vector3D(player.posX, player.posY, player.posZ);
			PathHandler.setTarget(target);
			Utils.sendInformation(Main.pathTargetSet.toString());
		} else if ("off".equals(op)) {
			PathHandler.removeTarget();
			Utils.sendInformation(Main.pathTargetRemoved.toString());
		} else {
			throw new CommandException(getDescription(), new Object[0]);
		}
	}

	@Override
	public String getBase() {
		return "target";
	}

	@Override
	public String getDescription() {
		return "/cam target <set/off>";
	}

}
