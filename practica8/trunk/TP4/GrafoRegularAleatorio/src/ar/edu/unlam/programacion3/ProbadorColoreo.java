package ar.edu.unlam.programacion3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

public class ProbadorColoreo {
	
	private static final String IN_PATH = "lote/entradas/";
	private static final String OUT_PATH = "lote/salidas/";
	private static final String IN_EXTENSION = ".in";
	private static final String OUT_EXTENSION = ".out";
	
	public static void main(String[] args) {
		File file;
		FileReader fileReader;
		BufferedReader bufferEntrada = null;
		PrintWriter bufferSalida = null;

		// Listamos archivos en directorio de salida terminados en ".out".
		file = new File(OUT_PATH);
		String[] archivosDeEntrada = file.list(new FiltroPorExtension(OUT_EXTENSION));
		
		for (String nombreArchivo : archivosDeEntrada) {
			try {
				// Generamos los paths completos
				String fullOutPath = new StringBuffer().append(OUT_PATH)
													   .append(nombreArchivo)
													   .toString();
				String fullInPath = new StringBuffer().append(IN_PATH)
													  .append(nombreArchivo.replace(OUT_EXTENSION, IN_EXTENSION))
													  .toString();

				// Abrimos el archivo in para lectura
				file = new File(fullInPath);
				fileReader = new FileReader(file);
				bufferEntrada = new BufferedReader(fileReader);

				// Abrimos el archivo out para escritura
				file = new File(fullOutPath);
				bufferSalida = new PrintWriter(file);

				String buffer;
				String[] splitBuffer;
				
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
			} finally {
				if (bufferEntrada != null) {
					try {
						bufferEntrada.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				if (bufferSalida != null) {
					bufferSalida.close();
				}
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
