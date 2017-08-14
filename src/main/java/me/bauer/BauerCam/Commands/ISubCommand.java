package me.bauer.BauerCam.Commands;

public interface ISubCommand {

	public void execute(String[] args);

	public String getBase();

	public String getDescription();

}
