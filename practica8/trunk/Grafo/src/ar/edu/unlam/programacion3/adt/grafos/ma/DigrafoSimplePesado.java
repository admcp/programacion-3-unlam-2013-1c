package ar.edu.unlam.programacion3.adt.grafos.ma;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class DigrafoSimplePesado extends DigrafoSimple {
	
	private double[][] matrizDeValores;

	public DigrafoSimplePesado(int cantidadNodos) {
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
	}
	
	public void quitarArco(int nodoOrigen, int nodoDestino) {
		super.quitarArco(nodoOrigen, nodoDestino);
		matrizDeValores[nodoOrigen - 1][nodoDestino - 1] = 0;
	}
	
	public ArcoPesado getArco(int nodoOrigen, int nodoDestino) {
		verificarIndice(nodoOrigen, "El nodo origen no existe");
		verificarIndice(nodoDestino, "El nodo origen no existe");
		
		ArcoPesado arco = null;
		if(matrizDeAdyacencia[nodoOrigen - 1][nodoDestino - 1] == true) {
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
			
			// Si encontramos algún nodo, continuamos con dijkstra
			if(nodoActual != -1) {
				// Quitamos el nodo encontrado del conjunto universal
				conjuntoUniversal.remove(nodoActual);
	
				// PASO 2
				
				// Obtener todos los adyacentes del nodo encontrado
				conjuntoNodosAdyacentes = getConjuntoAdyacentes(nodoActual);
				
				if(conjuntoNodosAdyacentes != null) {
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
			} else {
				// Caso contrario, no hay más nodos que podamos calcular la distancia
				break;
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
		DigrafoSimplePesado grafo = new DigrafoSimplePesado(8);
		grafo.agregarArco(1, 2, 20);
		grafo.agregarArco(1, 4, 80);
		grafo.agregarArco(1, 7, 90);
		grafo.agregarArco(2, 6, 10);
		grafo.agregarArco(3, 4, 10);
		grafo.agregarArco(3, 6, 50);
		grafo.agregarArco(3, 8, 20);
		grafo.agregarArco(4, 3, 10);
		grafo.agregarArco(4, 7, 20);
		grafo.agregarArco(5, 2, 50);
		grafo.agregarArco(5, 7, 30);
		grafo.agregarArco(6, 3, 10);
		grafo.agregarArco(6, 4, 40);
		grafo.agregarArco(7, 1, 20);
		
		System.out.println(grafo);
		
		double[] menorCosto = grafo.dijkstra(1);
		for(double valor : menorCosto) {
			System.out.print((valor == Double.POSITIVE_INFINITY ? "\u221E" : valor) + " ");
		}
		System.out.println();
		
	}

}
