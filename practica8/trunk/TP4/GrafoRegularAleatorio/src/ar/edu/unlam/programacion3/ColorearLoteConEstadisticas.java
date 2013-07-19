package ar.edu.unlam.programacion3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ar.edu.unlam.programacion3.adt.grafo.GrafoNoPesado;

@SuppressWarnings("unused")
public class ColorearLoteConEstadisticas {

	private static final String IN_PATH = "lote/entradas/";
	private static final String OUT_PATH = "lote/salidas/";
	private static final String IN_EXTENSION = ".in";
	private static final String OUT_EXTENSION = ".out";
	private static final String EST_EXTENSION = ".csv";

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
		Set<Par> cantidadDeColores;
		Map<Integer, Integer> colorFrecuencia;
		for (String nombreArchivo : archivosDeEntrada) {
			try {

				// Calculamos el path de entrada para crear el grafo
				String fullInPath = new StringBuffer().append(IN_PATH)
													  .append(nombreArchivo)
													  .toString();
				
				grafo = new GrafoNoPesado(fullInPath);
				
				System.out.println("Procesando " + fullInPath);
				
				// Calculamos el path de salida para cada algoritmo, coloreamos y escribimos la salida
				String fullOutPath = new StringBuffer().append(OUT_PATH + "/secuencial/")
						.append(nombreArchivo.replace(IN_EXTENSION, OUT_EXTENSION))
						.toString();
				
				String fullOutPathEst = fullOutPath.replace(OUT_EXTENSION, EST_EXTENSION);
				
				cantidadDeColores = new HashSet<Par>();
				for (int i = 0; i < 10000; i++) {
					grafo.coloreoSecuencial(fullOutPath);
					Par nuevoPar = new Par(grafo.getNumeroCromatico());
					if(cantidadDeColores.add(nuevoPar) == false) {
						for(Par par : cantidadDeColores) {
							if(par.equals(nuevoPar)) {
								par.aumentarFrecuencia();
							}
						}
					}
					if(i % 100 == 0) {
						System.out.println("Secuencial: " + String.format("%.2f", (i / 10000.0) * 100) + "% completado.");
					}
				}
				
				bufferSalida = new PrintWriter(new File(fullOutPathEst));
				for(Par par : cantidadDeColores) {
					bufferSalida.println(par.cantidadDeColores + "," + par.frecuencia);
				}
				bufferSalida.close();
				
				fullOutPath = new StringBuffer().append(OUT_PATH + "/matula/")
						.append(nombreArchivo.replace(IN_EXTENSION, OUT_EXTENSION))
						.toString();
				
				fullOutPathEst = fullOutPath.replace(OUT_EXTENSION, EST_EXTENSION);
				
				cantidadDeColores = new HashSet<Par>();
				for (int i = 0; i < 10000; i++) {
					grafo.coloreoMatula(fullOutPath);
					Par nuevoPar = new Par(grafo.getNumeroCromatico());
					if(cantidadDeColores.add(nuevoPar) == false) {
						for(Par par : cantidadDeColores) {
							if(par.equals(nuevoPar)) {
								par.aumentarFrecuencia();
							}
						}
					}
					if(i % 100 == 0) {
						System.out.println("Matula: " + String.format("%.2f", (i / 10000.0) * 100) + "% completado.");
					}
				}
				
				bufferSalida = new PrintWriter(new File(fullOutPathEst));
				for(Par par : cantidadDeColores) {
					bufferSalida.println(par.cantidadDeColores + "," + par.frecuencia);
				}
				bufferSalida.close();
				
				fullOutPath = new StringBuffer().append(OUT_PATH + "/powell/")
						.append(nombreArchivo.replace(IN_EXTENSION, OUT_EXTENSION))
						.toString();
				
				fullOutPathEst = fullOutPath.replace(OUT_EXTENSION, EST_EXTENSION);
				
				cantidadDeColores = new HashSet<Par>();
				for (int i = 0; i < 10000; i++) {
					grafo.coloreoPowell(fullOutPath);
					Par nuevoPar = new Par(grafo.getNumeroCromatico());
					if(cantidadDeColores.add(nuevoPar) == false) {
						for(Par par : cantidadDeColores) {
							if(par.equals(nuevoPar)) {
								par.aumentarFrecuencia();
							}
						}
					}
					if(i % 200 == 0) {
						System.out.println("Powell: " + String.format("%.2f", (i / 10000.0) * 100) + "% completado.");
					}
				}
				
				bufferSalida = new PrintWriter(new File(fullOutPathEst));
				for(Par par : cantidadDeColores) {
					bufferSalida.println(par.cantidadDeColores + "," + par.frecuencia);
				}
				bufferSalida.close();

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
	
	private static class Par {
		private int cantidadDeColores;
		private int frecuencia;
		
		public Par(int cantidadDeColores) {
			this.cantidadDeColores =  cantidadDeColores;
			frecuencia = 1;
		}
		 
		public void aumentarFrecuencia() {
			frecuencia++;
		}
	
		@Override
		public String toString() {
			return cantidadDeColores + ": " + frecuencia;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + cantidadDeColores;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Par))
				return false;
			Par other = (Par) obj;
			if (cantidadDeColores != other.cantidadDeColores)
				return false;
			return true;
		}
		
	}
}
