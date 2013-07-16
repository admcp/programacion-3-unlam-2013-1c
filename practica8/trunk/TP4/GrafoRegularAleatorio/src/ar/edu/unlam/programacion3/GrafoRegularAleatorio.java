package ar.edu.unlam.programacion3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ar.edu.unlam.programacion3.adt.grafo.Arco;
import ar.edu.unlam.programacion3.adt.grafo.GrafoNoPesado;

public class GrafoRegularAleatorio {

	private static final boolean DEBUG = true;

	public static void main(String[] args) {

		// Para el archivo de salida
		File file = null;
		PrintWriter printWriter = null;
		String fileName = null;

		// Para la lectura por consola
		InputStreamReader inputStreamReader = null;
		BufferedReader inputBufferedReader = null;
		
		int nodos = -1;
		int grado = -1;
		
		if(args.length != 0) {
			// LECTURA DE PARÁMETROS DESDE MAIN
			
			nodos = Integer.parseInt(args[0]);
			grado = Integer.parseInt(args[1]);
			fileName = args[2];
			
		} else {
			// LECTURA DE PARÁMETROS DESDE LA CONSOLA
			
			try {
				// Abrimos la consola
				inputStreamReader = new InputStreamReader(System.in);
				inputBufferedReader = new BufferedReader(inputStreamReader);
	
				String buffer = null;
	
				// Leemos la cantidad de nodos
				while (nodos <= 0) {
					System.out.print("Ingrese la cantidad de nodos: ");
					buffer = inputBufferedReader.readLine();
					nodos = Integer.parseInt(buffer);
					if (nodos <= 0) {
						System.out.println("La cantidad de nodos debe ser un entero positivo.");
					}
				}
	
				// Leemos el grado
				while (grado < 0) {
					System.out.print("Ingrese el grado: ");
					buffer = inputBufferedReader.readLine();
					grado = Integer.parseInt(buffer);
					if (grado < 0) {
						System.out.println("El grado debe ser un entero positivo.");
					}
				}
	
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
			} finally {
				if (inputBufferedReader != null) {
					try {
						inputBufferedReader.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		// CREACIÓN DEL GRAFO

		// Condiciones para que G(N, k) sea k-regular:
		// 1. k + 1 <= N.
		// 2. N * k par.

		if (grado + 1 > nodos) {
			System.err.println("k + 1 debe ser menor o igual que N");
			System.exit(-1);
		}
		if (nodos * grado % 2 != 0) {
			System.err.println("N * k debe ser par");
			System.exit(-1);
		}

		GrafoNoPesado grafo = new GrafoNoPesado(nodos);

		int cantidadReintentos = 0;
		long tiempoInicial = 0;
		long tiempoFinal = 0;

		boolean primeraVez = true;
		tiempoInicial = System.currentTimeMillis();
		while (!grafo.esKRegular(grado)) {
			grafo.reinicializarGrafo();

			if (DEBUG) {
				if (primeraVez) {
					primeraVez = false;
				} else {
					cantidadReintentos++;
					System.out.println("Reintentando...");
				}
			}

			// Generamos una lista de nodos n * k: 0, 1, 2, .., n, 0, 1, 2, ...
			List<Integer> listaNodos = new ArrayList<Integer>(nodos * grado);

			int j = 0;
			for (int i = 0; i < nodos * grado; i++) {
				listaNodos.add(j++);
				if (j == nodos) {
					j = 0;
				}
			}

			// Creamos la lista de arcos posibles
			List<Arco> listaArcosPosibles = new ArrayList<Arco>(nodos * grado / 2);

			// Empezamos a armar el grafo
			Random random = new Random(System.currentTimeMillis());

			// Criterio arbitrario de reintento: si pasó un 1 seg sin cambios, reintentar.
			boolean reintentarPorAgotamiento = false;
			long t0 = System.currentTimeMillis();
			boolean huboCambios = false;

			// Mientras la lista de nodos universal no esté vacía
			while (!listaNodos.isEmpty() && !reintentarPorAgotamiento) {
				// Tomamos dos índices distintos al azar de dicha lista
				int idx1 = random.nextInt(listaNodos.size());
				int idx2 = random.nextInt(listaNodos.size());
				while (idx1 == idx2) { // Descartamos la elección del mismo
										// índice
					idx1 = random.nextInt(listaNodos.size());
					idx2 = random.nextInt(listaNodos.size());
				}

				// Con dichos índices obtenemos un par de nodos
				int nodoOrigen = listaNodos.get(idx1);
				int nodoDestino = listaNodos.get(idx2);

				// Con dichos nodos creamos un arco
				Arco arco = new Arco(nodoOrigen, nodoDestino);

				// Si dicho arco no es un bucle y nuestra lista no lo contiene
				if (nodoOrigen != nodoDestino && !listaArcosPosibles.contains(arco)) {
					// Quitamos dichos nodos de la lista de nodos
					if (idx1 < idx2) {
						listaNodos.remove(idx1);
						listaNodos.remove(idx2 - 1);
					} else {
						listaNodos.remove(idx1);
						listaNodos.remove(idx2);
					}

					// Lo agregamos a la lista de arcos
					listaArcosPosibles.add(arco);

					// Creamos dicho arco en el grafo
					grafo.agregarArco(nodoOrigen, nodoDestino);

					huboCambios = true;
					t0 = System.currentTimeMillis();

					if (DEBUG) {
						// Mostramos el progreso para grafos grandes
						if (listaArcosPosibles.size() % 1000 == 0) {
							System.out.println(String.format("%.2f",
									(listaArcosPosibles.size() / ((nodos * grado) / 2.0)) * 100)
									+ "% completo ("
									+ String.format("%.2f", (System.currentTimeMillis() - tiempoInicial) / 1000.0)
									+ " seg corriendo). ");
						}
					}
				} else if (nodoOrigen == nodoDestino || listaArcosPosibles.contains(arco)) {
					huboCambios = false;
				}

				// Si no hubo cambios durante 1 seg.
				if (!huboCambios && System.currentTimeMillis() - t0 >= 1000) {
					reintentarPorAgotamiento = true;
				}

			}
		}
		tiempoFinal = System.currentTimeMillis();

		// ESCRITURA DEL GRAFO A UN ARCHIVO

		try {
			file = new File(fileName == null ? "grafoRegular.out" : fileName);
			printWriter = new PrintWriter(file);

			// Escribimos el encabezado
			int cantidadDeNodos = nodos;
			int cantidadDeArcos = grafo.cantidadDeArcos();
			double porcentajeDeAdyacencia = cantidadDeArcos / ((nodos * (nodos - 1)) / 2.0);
			int gradoMax = grado;
			int gradoMin = grado;

			printWriter.println(cantidadDeNodos + " " +
							    cantidadDeArcos + " " +
							    porcentajeDeAdyacencia + " " +
							    gradoMax + " " +
							    gradoMin);

			List<Arco> conjuntoDeArcos = grafo.conjuntoArcos();
			for (Arco arco : conjuntoDeArcos) {
				printWriter.println(arco.getNodoOrigen() + " " + arco.getNodoDestino());
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}

		if (DEBUG) {
			// Mostrar estadísticas
			System.out.println("El algoritmo tuvo que ser corrido " + (cantidadReintentos + 1)
					+ ((cantidadReintentos + 1) == 1 ? " vez." : " veces."));
			System.out.println("Se encontró un grafo " + grado + "-regular de " + nodos + " nodos.");
			System.out.println("Dicho grafo requirió " + grafo.cantidadDeArcos() + " arcos.");
			System.out.println("Y tomó " + (tiempoFinal - tiempoInicial) / 1000.0 + " seg en encontrarse una solución.");
		}

	}
}
