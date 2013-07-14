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

	public static void main(String[] args) {
		
		// Para el archivo de salida
		File file = null;
		PrintWriter printWriter = null;

		// Para la lectura por consola
		InputStreamReader inputStreamReader = null;
		BufferedReader inputBufferedReader = null;

		
		// LECTURA DE PARÁMETROS DESDE LA CONSOLA
		
		int nodos = -1;
		int grado = -1;

		try {
			// Abrimos la consola
			inputStreamReader = new InputStreamReader(System.in);
			inputBufferedReader = new BufferedReader(inputStreamReader);

			String buffer = null;

			// Leemos la cantidad de nodos
			while(nodos <= 0) {
				System.out.print("Ingrese la cantidad de nodos: ");
				buffer = inputBufferedReader.readLine();
				nodos = Integer.parseInt(buffer);
				if(nodos <= 0) {
					System.out.println("La cantidad de nodos debe ser un entero positivo.");
				}
			}
			
			// Leemos el grado
			while(grado < 0) {
				System.out.print("Ingrese el grado: ");
				buffer = inputBufferedReader.readLine();
				grado = Integer.parseInt(buffer);
				if(grado < 0) {
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
		
		boolean primeraVez = true;
		while(!grafo.esKRegular(grado)) {
			grafo.reinicializarGrafo();
			
			if(primeraVez) {
				primeraVez = false;
			} else {
				System.out.println("Reintentando...");
			}
			
			// Generamos una lista de nodos n * k: 0, 1, 2, .., n, 0, 1, 2, ...
			List<Integer> listaNodos = new ArrayList<Integer>(nodos * grado);
	
			int j = 0;
			for(int i = 0; i < nodos * grado; i++) {
				listaNodos.add(j++);
				if(j == nodos) {
					j = 0;
				}
			}
			
			// Convertimos la lista de nodos a una lista de arcos
			List<Arco> listaArcosPosibles = new ArrayList<Arco>(nodos * grado / 2);
			
			for(int i = 0; i < listaNodos.size(); i += 2) {
				listaArcosPosibles.add(new Arco(listaNodos.get(i), listaNodos.get(i + 1)));
			}
			
			// Empezamos a armar el grafo
			Random random = new Random(System.currentTimeMillis());
			
			// Mientras la lista de nodos universal no esté vacía
			while(!listaNodos.isEmpty()) {
				// Tomamos dos índices distintos al azar de dicha lista
				int idx1 = random.nextInt(listaNodos.size());
				int idx2 = random.nextInt(listaNodos.size());
				while(idx1 == idx2) {
					idx1 = random.nextInt(listaNodos.size());
					idx2 = random.nextInt(listaNodos.size());
				}
				
				// Con dichos índices obtenemos un par de nodos
				int nodoOrigen = listaNodos.get(idx1);
				int nodoDestino = listaNodos.get(idx2);
				
				// Si dichos nodos no forman un arco en los arcos aleatorios creados:
				// Lo agregamos al grafo y quitamos los nodos de la lista de nodos
				// NOTA: Si ya no hay forma de agregar el arco, empezamos de cero.
				Arco arco = new Arco(nodoOrigen, nodoDestino);

				if(!listaArcosPosibles.contains(arco) && nodoOrigen != nodoDestino) {
					if(idx1 < idx2) {
						listaNodos.remove(idx1);
						listaNodos.remove(idx2 - 1);
					} else {
						listaNodos.remove(idx1);
						listaNodos.remove(idx2);
					}
					grafo.agregarArco(nodoOrigen, nodoDestino);
				} else if(listaNodos.size() == 2 && listaArcosPosibles.contains(arco)) {
					listaNodos.clear();
				}
			}
		}
	
		// ESCRITURA DEL GRAFO A UN ARCHIVO
		
		try {
			file = new File("grafoRegular.out");
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
			for(Arco arco : conjuntoDeArcos) {
				printWriter.println(arco.getNodoOrigen() + " " + arco.getNodoDestino());
			}
			
		} catch(FileNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			if(printWriter != null) {
				printWriter.close();
			}
		}

	}

}
