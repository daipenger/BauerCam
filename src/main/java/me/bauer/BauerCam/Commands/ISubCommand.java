package me.bauer.BauerCam.Commands;

import net.minecraft.command.CommandException;

public interface ISubCommand {

	public void execute(String[] args) throws CommandException;

	public String getBase();

	public String getDescription();

}
