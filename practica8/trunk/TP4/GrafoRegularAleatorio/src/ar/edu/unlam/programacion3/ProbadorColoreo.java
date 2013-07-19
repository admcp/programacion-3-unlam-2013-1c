package ar.edu.unlam.programacion3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import ar.edu.unlam.programacion3.adt.grafo.GrafoNoPesado;
import ar.edu.unlam.programacion3.adt.grafo.Nodo;

public class ProbadorColoreo {
	
	private static final String IN_PATH = "lote/entradas/";
	private static final String[] OUT_PATH = { "lote/salidas/secuencial/", "lote/salidas/matula/", "lote/salidas/powell/" };
	private static final String IN_EXTENSION = ".in";
	private static final String OUT_EXTENSION = ".out";
	
	public static void main(String[] args) {
		File file;
		FileReader fileReader;
		BufferedReader bufferEntrada = null;
		
		GrafoNoPesado grafo = null;
		
		// Listamos archivos en directorio de entrada terminados en ".in".
		file = new File(IN_PATH);
		String[] archivosDeEntrada = file.list(new FiltroPorExtension(IN_EXTENSION));
		
		for (String nombreArchivo : archivosDeEntrada) {
			try {
				// Generamos un grafo con la entrada
				String fullInPath = new StringBuffer().append(IN_PATH)
						  							  .append(nombreArchivo)
						  							  .toString();
				
				grafo = new GrafoNoPesado(fullInPath);
				
				
				String buffer;
				String[] splitBuffer;
				
				// Generamos los paths de los archivos de coloreo correspondientes
				String[] fullOutPath = { 
						new StringBuffer().append(OUT_PATH[0]).append(nombreArchivo.replace(IN_EXTENSION, OUT_EXTENSION)).toString(),
						new StringBuffer().append(OUT_PATH[1]).append(nombreArchivo.replace(IN_EXTENSION, OUT_EXTENSION)).toString(),
						new StringBuffer().append(OUT_PATH[2]).append(nombreArchivo.replace(IN_EXTENSION, OUT_EXTENSION)).toString()
						};
				
				for(int i = 0; i < fullOutPath.length; i++) {
					file = new File(fullOutPath[i]);
					fileReader = new FileReader(file);
					bufferEntrada = new BufferedReader(fileReader);
					
					System.out.print("Chequeando " + fullOutPath[i]);
					
					// Leemos el encabezado
					buffer = bufferEntrada.readLine();
					splitBuffer = buffer.split(" ");
					
					int cantidadDeNodos = Integer.parseInt(splitBuffer[0]);
					int numeroCromatico = Integer.parseInt(splitBuffer[1]);
					int gradoMaximo = Integer.parseInt(splitBuffer[2]);
					int gradoMinimo = Integer.parseInt(splitBuffer[3]);
					
					// Chequeamos lo que podemos de momento: cantidad de nodos y grados.
					if(cantidadDeNodos != grafo.cantidadDeNodos()) {
						System.err.println("Cantidad de nodos incorrecta.");
						break;
					}
					if(gradoMaximo != grafo.gradoMaximo()) {
						System.err.println("Error en grado máximo.");
						break;
					}
					if(gradoMinimo != grafo.gradoMinimo()) {
						System.err.println("Error en grado mínimo.");
						break;
					}
					
					// Leemos los nodos y sus colores
					Nodo[] listaNodos = new Nodo[cantidadDeNodos];
					int colorMaximo = Integer.MIN_VALUE;
					
					for(int j = 0; j < cantidadDeNodos; j++) {
						buffer = bufferEntrada.readLine();
						splitBuffer = buffer.split(" ");
						
						int nodo = Integer.parseInt(splitBuffer[0]);
						int color = Integer.parseInt(splitBuffer[1]);
						
						if(color > colorMaximo) {
							colorMaximo = color;
						}
						
						listaNodos[j] = new Nodo(nodo);
						listaNodos[j].setColor(color);
					}
					
					// Verificamos el número cromático obtenido
					if(colorMaximo != numeroCromatico) {
						System.err.println("No coincide el número cromático con la cantidad de colores asignados.");
						break;
					}
					
					// Verificamos que TODOS los nodos estén coloreados
					for(Nodo nodo : listaNodos) {
						if(nodo.getColor() == 0) {
							System.err.println("Nodo " + nodo.getNombre() + " no coloreado.");
							break;
						}
					}
					
					// Verificamos que no haya dos nodos adyacentes con el mismo color
					for(Nodo nodo : listaNodos) {
						Nodo[] nodosAdyacentes = grafo.obtenerAdyacentes(nodo.getNombre());
						for(Nodo nodoAdy : nodosAdyacentes) {
							if(nodo.getColor() == nodoAdy.getColor()) {
								System.err.println("Nodo " + nodo.getNombre() + " tiene el mismo color que " + nodoAdy.getNombre() + ".");
								break;
							}
						}
					}
					
					System.out.println("  ...  OK");
					
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
