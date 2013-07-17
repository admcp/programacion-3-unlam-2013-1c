package ar.edu.unlam.programacion3.practica2.tda.sel.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import ar.edu.unlam.programacion3.practica2.tda.sel.SistemaLinealDeEcuaciones;

public class ResolvedorDeLoteSEL {

	private final static String IN_PATH = "doc/loteDePruebas/entradas/";
	private final static String OUT_PATH = "doc/loteDePruebas/salidasGeneradas/";
	private final static String IN_EXTENSION = ".in";
	private final static String OUT_EXTENSION = ".out";

	public static void main(String[] args) {
		File file;

		// Listamos archivos en directorio de entrada terminados en ".in".
		file = new File(IN_PATH);
		String[] archivosDeEntrada = file.list(new FiltroPorExtension(IN_EXTENSION));

		for (String nombreArchivo : archivosDeEntrada) {
			try {
				// Generamos los paths completos
				String fullInPath = new StringBuffer().append(IN_PATH).append(nombreArchivo).toString();
				String fullOutPath = new StringBuffer().append(OUT_PATH).append(nombreArchivo.replace(IN_EXTENSION, OUT_EXTENSION)).toString();

				System.out.println("Procesando " + fullInPath + "...");
				SistemaLinealDeEcuaciones.resolverSELDesdeArchivo(fullInPath, fullOutPath);
				System.out.println("Fin de proceso de: " + fullInPath + ".");

			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
			}
		}

	}

	private static class FiltroPorExtension implements FilenameFilter {
		private String extension;

		public FiltroPorExtension(String extension) {
			this.extension = extension;
		}

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(extension);
		}
	}
}
