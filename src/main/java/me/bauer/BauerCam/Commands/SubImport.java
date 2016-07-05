package me.bauer.BauerCam.Commands;

public class SubImport extends ASubExportImport {

	@Override
	public void derivedExecute(String filename) {
		load(filename);
	}

	@Override
	public String getBase() {
		return "import";
	}

	@Override
	public String getDescription() {
		return "/cam import <filename>";
	}

}
