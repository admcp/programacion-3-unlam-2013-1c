package ar.edu.unlam.programacion3.adt.grafos.ma;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import ar.edu.unlam.programacion3.adt.conjuntos.UnionBalanceadaRapida;

public class GrafoSimplePesado extends GrafoSimple {
	
	private double[][] matrizDeValores;

	public GrafoSimplePesado(int cantidadNodos) {
		super(cantidadNodos);
		
		if (cantidadNodos < 0) {
			throw new IllegalArgumentException("La cantidad de nodos debe ser un valor positivo.");
		}
		
		matrizDeValores = new double[cantidadNodos][cantidadNodos];
		for (int i = 0; i < matrizDeValores.length; i++) {
			for (int j = 0; j < matrizDeValores.length; j++) {
				matrizDeValores[i][j] = 0;
			}
		}
	}
	
	@Override
	public void quitarNodo(int nodo) {
		super.quitarNodo(nodo);
		double[][] matrizDeValoresAux = new double[matrizDeValores.length - 1][matrizDeValores.length - 1];
		for (int i = 0, r = 0; i < matrizDeValores.length; i++) {
			if (i == nodo - 1) {
				continue;
			}
			for (int j = 0, s = 0; j < matrizDeValores.length; j++) {
				if (j == nodo - 1) {
					continue;
				}
				matrizDeValoresAux[r][s] = matrizDeValores[i][j];
				s++;
			}
			r++;
		}
		
		matrizDeValores = matrizDeValoresAux;
	}
	
	public void agregarArco(int nodoOrigen, int nodoDestino, double valor) {
		super.agregarArco(nodoOrigen, nodoDestino);
		matrizDeValores[nodoOrigen - 1][nodoDestino - 1] = valor;
		matrizDeValores[nodoDestino - 1][nodoOrigen - 1] = valor;
	}
	
	public void quitarArco(int nodoOrigen, int nodoDestino) {
		super.quitarArco(nodoOrigen, nodoDestino);
		matrizDeValores[nodoOrigen - 1][nodoDestino - 1] = 0;
		matrizDeValores[nodoDestino - 1][nodoOrigen - 1] = 0;
	}
	
	public ArcoPesado getArco(int nodoOrigen, int nodoDestino) {
		verificarIndice(nodoOrigen, "El nodo origen no existe");
		verificarIndice(nodoDestino, "El nodo origen no existe");
		
		ArcoPesado arco = null;
		if(matrizDeAdyacencia[nodoOrigen - 1][nodoDestino - 1] == true ||
				matrizDeAdyacencia[nodoDestino - 1][nodoOrigen - 1] == true) {
			arco = new ArcoPesado(nodoOrigen, nodoDestino, matrizDeValores[nodoOrigen - 1][nodoDestino - 1]);
		}
		
		return arco;
	}
	
	public double getValorArco(int nodoOrigen, int nodoDestino) {
		verificarIndice(nodoOrigen, "El nodo origen no existe");
		verificarIndice(nodoDestino, "El nodo origen no existe");
		
		return matrizDeValores[nodoOrigen - 1][nodoDestino - 1];
	}
	
	@Override
	public ArcoPesado[] getConjuntoArcos() {
		int cantidadArcos = 0;
		
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			for (int j = 0; j < matrizDeAdyacencia.length; j++) {
				if (matrizDeAdyacencia[i][j] == true) {
					cantidadArcos++;
				}
			}
		}
		
		ArcoPesado[] conjunto = new ArcoPesado[cantidadArcos];
		
		int index = 0;
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			for (int j = 0; j < matrizDeAdyacencia.length; j++) {
				if (matrizDeAdyacencia[i][j] == true) {
					conjunto[index] = new ArcoPesado(i + 1, j + 1, matrizDeValores[i][j]);
					index++;
				}
			}
		}
		
		return cantidadArcos == 0 ? null : conjunto;
	}
	
	// TODO: No funciona con grafos no conexos
	public ArcoPesado[] prim(int nodoOrigen) {
		verificarIndice(nodoOrigen, "El nodo origen no existe");
		
		int cantidadNodos = matrizDeAdyacencia.length;
		
		// Si el grafo está vacio no hay recorrido
		if(cantidadNodos == 0) {
			return null;
		}

		// Creamos e inicializamos el array de visitados
		boolean[] nodosVisitados = new boolean[cantidadNodos];
		for (int i = 0; i < cantidadNodos; i++) {
			nodosVisitados[i] = false;
		}
		
		// Array auxiliar para nodos adyacentes
		int[] nodosAdyacentes = null;
	
		// El recorrido para devolver
		Deque<ArcoPesado> recorrido = new ArrayDeque<ArcoPesado>();
		
		// Cola de prioridad de arcos disponibles ordenados de menor a mayor por costo
		Queue<ArcoPesado> arcosDisponibles = new PriorityQueue<ArcoPesado>();
		
		// Visitamos el nodo origen 
		// Cargamos los arcos de los nodos adyacentes al nodo origen en la cola de prioridad de arcos disponibles
		nodosVisitados[nodoOrigen - 1] = true;
		nodosAdyacentes = getConjuntoAdyacentes(nodoOrigen);
		if(nodosAdyacentes != null) {
			for(int i = 0; i < nodosAdyacentes.length; i++) {
				if(nodosVisitados[nodosAdyacentes[i] - 1] == false) {
					arcosDisponibles.offer(getArco(nodoOrigen, nodosAdyacentes[i]));
				}
			}
		}
		
		// Mientras haya arcos disponibles
		while(!arcosDisponibles.isEmpty()) {
			
			// Obtener del conjunto de arcos disponibles el menor que no hayamos visitado
			ArcoPesado arcoMenor = arcosDisponibles.poll();
			while(nodosVisitados[arcoMenor.getNodoOrigen() - 1] == true &&
					nodosVisitados[arcoMenor.getNodoDestino() - 1] == true &&
					!arcosDisponibles.isEmpty()) {
				arcoMenor = arcosDisponibles.poll();
			}
			
			// Si no nos quedamos sin arcos disponibles el arco obtenido es el de menor costo
			if(nodosVisitados[arcoMenor.getNodoDestino() - 1] == false) {
			
				// Obtenemos el nodo destino de dicho arco
				int nodoDestino = arcoMenor.getNodoDestino();
				
				// Lo visitamos
				nodosVisitados[nodoDestino - 1] = true;
				
				// Agregamos este arco al recorrido
				recorrido.add(arcoMenor);
				
				// Y encolamos los arcos de los nodos adyacentes al nodo visitado 
				// en la cola de prioridad de arcos disponibles
				nodosAdyacentes = getConjuntoAdyacentes(nodoDestino);
				if(nodosAdyacentes != null) {
					for(int i = 0; i < nodosAdyacentes.length; i++) {
						if(nodosVisitados[nodosAdyacentes[i] - 1] == false) {
							arcosDisponibles.offer(getArco(nodoDestino, nodosAdyacentes[i]));
						}
					}
				}
			} 
			
		}
		
		// Convertimos nuestro recorrido (cola) a un array de arcos
		ArcoPesado[] arrayRecorrido = null;
		if(!recorrido.isEmpty()) {
			arrayRecorrido = new ArcoPesado[recorrido.size()];
			for(int i = 0; i < arrayRecorrido.length; i++) {
				arrayRecorrido[i] = recorrido.poll();
			}
		}
		
		return arrayRecorrido;	
	}
	
	// TODO: No funciona con grafos no conexos
	public ArcoPesado[] kruskal() {
		
		int cantidadNodos = matrizDeAdyacencia.length;
		
		// Si el grafo está vacio no hay recorrido
		if(cantidadNodos == 0) {
			return null;
		}
		
		// Conjuntos disjuntos de nodos
		UnionBalanceadaRapida conjuntosNodos = new UnionBalanceadaRapida(cantidadNodos);
		
		// El recorrido para devolver
		List<ArcoPesado> recorrido = new ArrayList<ArcoPesado>();
		
		// Cola de prioridad de arcos disponibles ordenados de menor a mayor por costo
		Queue<ArcoPesado> arcosDisponibles = new PriorityQueue<ArcoPesado>();
		
		// Pedimos todos los arcos y los ordenamos a medida que ingresan en la cola
		for(ArcoPesado arco : getConjuntoArcos()) {
			arcosDisponibles.offer(arco);
		}
		
		while(!arcosDisponibles.isEmpty() && recorrido.size() < cantidadNodos - 1) {
			// Obtenemos el menor arco disponible
			ArcoPesado arcoMenor = arcosDisponibles.poll();
			
			// Si dicho arco no forma un ciclo con los que ya están en el recorrido lo agregamos
			int nodoOrigen = arcoMenor.getNodoOrigen();
			int nodoDestino = arcoMenor.getNodoDestino();
			if(!conjuntosNodos.estanConectados(nodoOrigen - 1, nodoDestino - 1)) {
				conjuntosNodos.unir(nodoOrigen - 1, nodoDestino - 1);
				recorrido.add(arcoMenor);
			}
			
		}
		
		// Convertimos nuestro recorrido (cola) a un array de arcos
		ArcoPesado[] arrayRecorrido = null;
		if(!recorrido.isEmpty()) {
			arrayRecorrido = new ArcoPesado[recorrido.size()];
			for(int i = 0; i < arrayRecorrido.length; i++) {
				arrayRecorrido[i] = recorrido.get(i);
			}
		}
		
		return arrayRecorrido;	
	}
	
	// TODO: No funciona con grafos no conexos
	public double[] dijkstra(int nodoOrigen) {
		verificarIndice(nodoOrigen, "El nodo origen no existe");
		
		int cantidadNodos = matrizDeAdyacencia.length;
		// Si el grafo está vacio no hay recorrido
		
		if(cantidadNodos == 0) {
			return null;
		}
		
		// PASO 0
		
		// Creamos el conjunto universal (todos los nodos salvo el de origen)
		int[] conjuntoNodos = getConjuntoNodos();
		Set<Integer> conjuntoUniversal = new HashSet<Integer>();
		for(int i = 0; i < cantidadNodos; i++) {
			if(conjuntoNodos[i] != nodoOrigen) {
				conjuntoUniversal.add(conjuntoNodos[i]);
			}
		}
		
		// Creamos el vector de distancias para devolver y lo inicializamos en infinito
		double[] distancias = new double[cantidadNodos];
		for(int i = 0; i < cantidadNodos; i++) {
			distancias[i] = Double.POSITIVE_INFINITY;
		}
		
		// La distancia al nodo origen es 0
		distancias[nodoOrigen - 1] = 0;
				
		// Obtenemos los adyacentes al nodo origen para incializar el vector de distancias
		int[] conjuntoNodosAdyacentes = getConjuntoAdyacentes(nodoOrigen);
		for(int i = 0; i < conjuntoNodosAdyacentes.length; i++) {
			distancias[conjuntoNodosAdyacentes[i] - 1] = getValorArco(nodoOrigen, conjuntoNodosAdyacentes[i]);
		}
		
		while(!conjuntoUniversal.isEmpty()) {
			// PASO 1
			
			// Tomamos del vector de distancias aquel nodo que tiene la menor distancia,
			// y que se encuentre en en el conjunto universal
			double distancia = Double.POSITIVE_INFINITY;
			int nodoActual = -1;
			Iterator<Integer> it = conjuntoUniversal.iterator();
			while(it.hasNext()) {
				int nodo = it.next();
				// En cuanto encontramos el menor dejamos de recorrer el conjunto universal
				if(distancias[nodo - 1] < distancia) {
					nodoActual = nodo;
					break;
				}
			}
			
			// Quitamos el nodo encontrado del conjunto universal
			conjuntoUniversal.remove(nodoActual);

			// PASO 2
			
			// Obtener todos los adyacentes del nodo encontrado
			conjuntoNodosAdyacentes = getConjuntoAdyacentes(nodoActual);
			
			// Para cada adyacente, calculamos el minimo (y si fuese necesario actualizamos)
			double distanciaActual, distanciaNueva;
			for(int i = 0; i < conjuntoNodosAdyacentes.length; i++) {
				distanciaActual = distancias[conjuntoNodosAdyacentes[i] - 1];
				distanciaNueva = distancias[nodoActual - 1] + getValorArco(nodoActual, conjuntoNodosAdyacentes[i]);
				if(distanciaNueva < distanciaActual) {
					distancias[conjuntoNodosAdyacentes[i] - 1] = distanciaNueva;
				}
			}
			
			
		}
		
		return distancias;
	}
	
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();

		// Calculo de padding
		double max = Double.MIN_VALUE;
		for(int i = 0; i < matrizDeAdyacencia.length; i++) {
			for(int j = 0; j < matrizDeAdyacencia.length; j++) {
				if(matrizDeValores[i][j] > max) {
					max = matrizDeValores[i][j];
				}
			}
		}
		int padding = String.valueOf(max).length() + 3;
		
		// Encabezado
		for(int i = 0; i < padding + 1; i++) {
			strBuilder.append(" ");
		}
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			strBuilder.append(String.format("%" + padding + "d", i + 1) + " ");
		}
		strBuilder.append(System.getProperty("line.separator"));
		
		// Matriz de Adyacencia
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			// # de nodo al principio 
			strBuilder.append(String.format("%" + padding + "d", i + 1) + " ");
			for (int j = 0; j < matrizDeAdyacencia.length; j++) {
				strBuilder.append(String.format("%" + padding + ".2f ", matrizDeValores[i][j]));
			}
			if (i + 1 != matrizDeAdyacencia.length) {
				strBuilder.append(System.getProperty("line.separator"));
			}
		}
		return strBuilder.toString();
	}
	
	public static void main(String[] args) {
		GrafoSimplePesado grafo = new GrafoSimplePesado(7);
		grafo.agregarArco(1, 2, 3);
		grafo.agregarArco(1, 3, 10);
		grafo.agregarArco(2, 4, 10);
		grafo.agregarArco(2, 6, 8);
		grafo.agregarArco(3, 5, 3);
		grafo.agregarArco(3, 4, 4);
		grafo.agregarArco(4, 5, 4);
		grafo.agregarArco(4, 6, 7);
		grafo.agregarArco(4, 7, 9);
		grafo.agregarArco(5, 7, 10);
		grafo.agregarArco(6, 7, 3);
		System.out.println(grafo);
		
		ArcoPesado[] mst = grafo.prim(3);
		if(mst != null) {
			double menorCosto = 0;
			for(ArcoPesado arco : mst) {
				System.out.print(arco + " ");
				menorCosto += arco.getValor();
			}
			System.out.println();
			System.out.println(menorCosto);
		}
		
		mst = grafo.kruskal();
		if(mst != null) {
			double menorCosto = 0;
			for(ArcoPesado arco : mst) {
				System.out.print(arco + " ");
				menorCosto += arco.getValor();
			}
			System.out.println();
			System.out.println(menorCosto);
		}
		
		double[] menorCosto = grafo.dijkstra(4);
		for(double valor : menorCosto) {
			System.out.print((valor == Double.POSITIVE_INFINITY ? "\u221E" : valor) + " ");
		}
		System.out.println();
		
	}

}
