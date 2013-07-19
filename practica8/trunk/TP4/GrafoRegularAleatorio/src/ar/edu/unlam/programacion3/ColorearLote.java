package ar.edu.unlam.programacion3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

import ar.edu.unlam.programacion3.adt.grafo.Coloreo;
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

		// Listamos archivos en directorio de salida terminados en ".in".
		file = new File(IN_PATH);
		String[] archivosDeEntrada = file.list(new FiltroPorExtension(IN_EXTENSION));

		for (String nombreArchivo : archivosDeEntrada) {
			
			int cantidadDeNodos = 0;
			int cantidadDeArcos = 0;
			double porcentajeDeAdyacencia = 0;
			int gradoMax = 0;
			int gradoMin = 0;
			GrafoNoPesado grafo = null;
			
			try {

				// Abrimos el archivo in para lectura
				String fullInPath = new StringBuffer().append(IN_PATH)
													  .append(nombreArchivo)
													  .toString();

				file = new File(fullInPath);
				fileReader = new FileReader(file);
				bufferEntrada = new BufferedReader(fileReader);

				String buffer;
				String[] splitBuffer;

				// Leemos el encabezado del grafo regular
				buffer = bufferEntrada.readLine();
				splitBuffer = buffer.split(" ");

				cantidadDeNodos = Integer.parseInt(splitBuffer[0]);
				cantidadDeArcos = Integer.parseInt(splitBuffer[1]);
				porcentajeDeAdyacencia = Double.parseDouble(splitBuffer[2]);
				gradoMax = Integer.parseInt(splitBuffer[3]);
				gradoMin = Integer.parseInt(splitBuffer[4]);

				// Creamos un grafo para dichos nodos
				grafo = new GrafoNoPesado(cantidadDeNodos);

				// Leemos los arcos y los vamos incorporando al grafo
				for (int i = 0; i < cantidadDeArcos; i++) {
					buffer = bufferEntrada.readLine();
					splitBuffer = buffer.split(" ");

					int nodoOrigen = Integer.parseInt(splitBuffer[0]);
					int nodoDestino = Integer.parseInt(splitBuffer[1]);

					grafo.agregarArco(nodoOrigen, nodoDestino);
				}

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

			// Abrimos el archivo out para escritura
			for (int k = 0; k < 3; k++) {
				try {
					// Calculamos el prefijo de acuerdo al algoritmo dado por k.
					// k = 0: secuencial, k = 1: powell, k = 2: matula
					// Y coloreamos mediante el algoritmo elegido
					String prefix = null;
					if (k == 0) {
						prefix = "Secuencial";
						Coloreo.secuencial(grafo);
					} else if (k == 1) {
						prefix = "Powell";
						Coloreo.powell(grafo);
					} else if (k == 2) {
						prefix = "Matula";
						Coloreo.matula(grafo);
					}

					String fullOutPath = new StringBuffer().append(OUT_PATH)
							.append(prefix + "-")
							.append(nombreArchivo.replace(IN_EXTENSION, OUT_EXTENSION))
							.toString();

					file = new File(fullOutPath);
					bufferSalida = new PrintWriter(file);

					// Escribimos el encabezado
					bufferSalida.println(cantidadDeNodos + " " + grafo.getNumeroCromatico() + " " + gradoMax);

					// Escribimos los nodos y sus colores
					for (int i = 0; i < cantidadDeNodos; i++) {
						bufferSalida.println(grafo.getNodo(i).getNombre() + " " + 
										     grafo.getNodo(i).getColor());
					}
					
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				} finally {
					if (bufferSalida != null) {
						bufferSalida.close();
					}
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
