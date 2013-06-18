package ar.edu.unlam.programacion3.oia.seleccion.nosepuedevivirahi.util;

import java.io.File;
import java.io.FilenameFilter;

import ar.edu.unlam.programacion3.oia.seleccion.nosepuedevivirahi.NoSePuedeVivirAhi;

public class CorrerTodoElLote {

	private final static String IN_PATH = "doc/NoSePuedeVivirAhi/Lote de Pruebas/Entradas/";
	private final static String OUT_PATH = "doc/NoSePuedeVivirAhi/Lote de Pruebas/Salidas Generadas/";
	private final static String IN_EXT = ".in";
	private final static String OUT_EXT = ".out";
	
	public static void main(String[] args) {
		String[] directoryListing = new File(IN_PATH).list(new FileExtensionFilter(IN_EXT));
		for(String fileName : directoryListing) {
			NoSePuedeVivirAhi.main(new String[] {IN_PATH + fileName, OUT_PATH + fileName.replace(IN_EXT, OUT_EXT)});
		}
	}
	
}

class FileExtensionFilter implements FilenameFilter {
	String extension;
	
	public FileExtensionFilter(String extension) {
		this.extension = extension;
	}
	
	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith(extension);
	}
}
