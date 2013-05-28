package ar.edu.unlam.programacion3.practica2.tda.sel.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

import ar.edu.unlam.programacion3.practica2.tda.sel.SistemaLinealDeEcuaciones;
import ar.edu.unlam.programacion3.practica2.tda.sel.exceptions.FilaDeCerosException;
import ar.edu.unlam.programacion3.practica2.tda.sel.exceptions.MatrizSingularException;

public class SerializadorDeSEL {

	private final static String inPath = "doc/loteDePruebas/entradas/";
	private final static String outPath = "doc/loteDePruebas/salidasGeneradas/";
	private final static String inExtension = ".in";
	private final static String outExtension = ".out";

	public static void main(String[] args) {
		File file;
		FileReader fileReader;
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;

		// Listamos archivos en directorio de entrada terminados en ".in".
		file = new File(inPath);
		String[] archivosDeEntrada = file.list(new FiltroPorExtension(inExtension));

		for (String nombreArchivo : archivosDeEntrada) {
			try {
				// Generamos los paths completos
				String fullInPath = new StringBuffer()
										.append(inPath)
										.append(nombreArchivo)
										.toString();
				String fullOutPath = new StringBuffer()
										.append(outPath)
										.append(nombreArchivo.replace(inExtension, outExtension))
										.toString();

				// Abrimos el archivo in para lectura
				file = new File(fullInPath);
				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);

				// Creamos el archivo out para salida
				file = new File(fullOutPath);
				printWriter = new PrintWriter(file);

				System.out.println("Procesando " + fullInPath + "...");
				SistemaLinealDeEcuaciones.resolverSELDesdeArchivo(bufferedReader, printWriter);
				System.out.println("Fin de proceso de: " + fullInPath + ".");

			} catch(FileNotFoundException ex) {
				ex.printStackTrace();
			} catch(IOException ex) {
				ex.printStackTrace();
			} catch(NumberFormatException ex) {
				ex.printStackTrace();
			} catch(MatrizSingularException ex) {
				printWriter.println("Matriz Singular");
			} catch(FilaDeCerosException ex) {
				printWriter.println("Imposible resolver por Factorizacion LU");
			}
			finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				if (printWriter != null) {
					printWriter.close();
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
