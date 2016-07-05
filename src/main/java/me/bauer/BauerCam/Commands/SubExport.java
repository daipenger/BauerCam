package me.bauer.BauerCam.Commands;

public class SubExport extends ASubExportImport {

	@Override
	public void derivedExecute(String filename) {
		save(filename);
	}

	@Override
	public String getBase() {
		return "export";
	}

	@Override
	public String getDescription() {
		return "/cam export <filename>";
	}

}
