package ar.edu.unlam.programacion3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

import ar.edu.unlam.programacion3.adt.grafo.GrafoNoPesado;

@SuppressWarnings("unused")
public class ColorearLote {

	private static final String IN_PATH = "lote/entradas/";
	private static final String OUT_PATH = "lote/salidas/";
	private static final String IN_EXTENSION = ".in";
	private static final String OUT_EXTENSION = ".out";

	public static void main(String[] args) {
		File file;
		FileReader fileReader;
		BufferedReader bufferEntrada = null;
		PrintWriter bufferSalida = null;

		GrafoNoPesado grafo = null;
		
		// Listamos archivos en directorio de salida terminados en ".in".
		file = new File(IN_PATH);
		String[] archivosDeEntrada = file.list(new FiltroPorExtension(IN_EXTENSION));

		// Por cada archivo encontrado: 
		// 		* Creamos el grafo desde el archivo.
		//		* Coloreamos y escribimos el resultado.
		for (String nombreArchivo : archivosDeEntrada) {
			try {

				// Calculamos el path de entrada para crear el grafo
				String fullInPath = new StringBuffer().append(IN_PATH)
													  .append(nombreArchivo)
													  .toString();
				
				grafo = new GrafoNoPesado(fullInPath);
				
				// Calculamos el path de salida para cada algoritmo, coloreamos y escribimos la salida
				String fullOutPath = new StringBuffer().append(OUT_PATH + "/secuencial/")
						.append(nombreArchivo.replace(IN_EXTENSION, OUT_EXTENSION))
						.toString();
				
				grafo.coloreoSecuencial(fullOutPath);
				
				fullOutPath = new StringBuffer().append(OUT_PATH + "/matula/")
						.append(nombreArchivo.replace(IN_EXTENSION, OUT_EXTENSION))
						.toString();
				
				grafo.coloreoMatula(fullOutPath);
				
				fullOutPath = new StringBuffer().append(OUT_PATH + "/powell/")
						.append(nombreArchivo.replace(IN_EXTENSION, OUT_EXTENSION))
						.toString();
				
				grafo.coloreoPowell(fullOutPath);

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
