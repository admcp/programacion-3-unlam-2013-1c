package ar.edu.unlam.programacion3.adt.grafos.ma;

import java.util.ArrayDeque;
import java.util.Deque;

public class DigrafoSimple {

	protected boolean[][] matrizDeAdyacencia;

	public DigrafoSimple(int cantidadNodos) {
		if (cantidadNodos < 0) {
			throw new IllegalArgumentException("La cantidad de nodos debe ser un valor positivo.");
		}
		
		matrizDeAdyacencia = new boolean[cantidadNodos][cantidadNodos];
		
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			for (int j = 0; j < matrizDeAdyacencia.length; j++) {
				matrizDeAdyacencia[i][j] = false;
			}
		}
	}

	public int dimension() {
		return matrizDeAdyacencia.length;
	}

	public boolean estaVacio() {
		return matrizDeAdyacencia.length == 0;
	}

	public void vaciar() {
		
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			for (int j = 0; j < matrizDeAdyacencia.length; j++) {
				matrizDeAdyacencia[i][j] = false;
			}
		}
		
		matrizDeAdyacencia = null;
		matrizDeAdyacencia = new boolean[0][0];
	}

	public void agregarNodo() {
		boolean[][] matrizDeAdyacenciaAux = new boolean[matrizDeAdyacencia.length + 1][matrizDeAdyacencia.length + 1];
		
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			for (int j = 0; j < matrizDeAdyacencia.length; j++) {
				matrizDeAdyacenciaAux[i][j] = matrizDeAdyacencia[i][j];
			}
		}
		
		for (int i = 0; i < matrizDeAdyacencia.length + 1; i++) {
			matrizDeAdyacenciaAux[matrizDeAdyacencia.length][i] = false;
			matrizDeAdyacenciaAux[i][matrizDeAdyacencia.length] = false;
		}
		
		matrizDeAdyacencia = matrizDeAdyacenciaAux;
	}

	public void quitarNodo(int nodo) {
		verificarIndice(nodo, "El nodo no existe");
		
		boolean[][] matrizDeAdyacenciaAux = new boolean[matrizDeAdyacencia.length - 1][matrizDeAdyacencia.length - 1];
		for (int i = 0, r = 0; i < matrizDeAdyacencia.length; i++) {
			if (i == nodo - 1) {
				continue;
			}
			for (int j = 0, s = 0; j < matrizDeAdyacencia.length; j++) {
				if (j == nodo - 1) {
					continue;
				}
				matrizDeAdyacenciaAux[r][s] = matrizDeAdyacencia[i][j];
				s++;
			}
			r++;
		}
		
		matrizDeAdyacencia = matrizDeAdyacenciaAux;
	}

	public boolean existeNodo(int nodo) {
		return nodo > 0 && nodo <= matrizDeAdyacencia.length;
	}

	public boolean sonAdyacentes(int nodoOrigen, int nodoDestino) {
		verificarIndice(nodoOrigen, "El nodo origen no existe");
		verificarIndice(nodoDestino, "El nodo origen no existe");
		
		return matrizDeAdyacencia[nodoOrigen - 1][nodoDestino - 1] == true;
	}

	public int[] getConjuntoAdyacentes(int nodoOrigen) {
		verificarIndice(nodoOrigen, "El nodo origen no existe");
		
		int cantidadAdyacentes = 0;
		
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			if (matrizDeAdyacencia[nodoOrigen - 1][i] == true) {
				cantidadAdyacentes++;
			}
		}
		
		int[] conjunto = new int[cantidadAdyacentes];
		
		int index = 0;
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			if (matrizDeAdyacencia[nodoOrigen - 1][i] == true) {
				conjunto[index] = i + 1;
				index++;
			}
		}
		
		return cantidadAdyacentes == 0 ? null : conjunto;
	}

	public void agregarArco(int nodoOrigen, int nodoDestino) {
		verificarIndice(nodoOrigen, "El nodo origen no existe");
		verificarIndice(nodoDestino, "El nodo origen no existe");
		
		matrizDeAdyacencia[nodoOrigen - 1][nodoDestino - 1] = true;
	}

	public void quitarArco(int nodoOrigen, int nodoDestino) {
		verificarIndice(nodoOrigen, "El nodo origen no existe");
		verificarIndice(nodoDestino, "El nodo origen no existe");
		
		matrizDeAdyacencia[nodoOrigen - 1][nodoDestino - 1] = false;
	}

	public boolean existeArco(int nodoOrigen, int nodoDestino) {
		verificarIndice(nodoOrigen, "El nodo origen no existe");
		verificarIndice(nodoDestino, "El nodo origen no existe");
		
		return matrizDeAdyacencia[nodoOrigen - 1][nodoDestino - 1] == true;
	}

	public int[] getConjuntoNodos() {
		int[] conjunto = new int[matrizDeAdyacencia.length];
		
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			conjunto[i] = i + 1;
		}
		
		return matrizDeAdyacencia.length == 0 ? null : conjunto;
	}

	public Arco[] getConjuntoArcos() {
		
		int cantidadArcos = 0;
		
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			for (int j = 0; j < matrizDeAdyacencia.length; j++) {
				if (matrizDeAdyacencia[i][j] == true) {
					cantidadArcos++;
				}
			}
		}
		
		Arco[] conjunto = new Arco[cantidadArcos];
		
		int index = 0;
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			for (int j = 0; j < matrizDeAdyacencia.length; j++) {
				if (matrizDeAdyacencia[i][j] == true) {
					conjunto[index] = new Arco(i + 1, j + 1);
					index++;
				}
			}
		}
		
		return cantidadArcos == 0 ? null : conjunto;
	}

	// Profundidad = DFS TODO: No funciona con grafos no conexos
	public int[] recorrerEnProfundidad(int nodoOrigen) {
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

		// Creamos la pila para visitar nodos
		Deque<Integer> pila = new ArrayDeque<Integer>();

		// Creamos el array que contiene el recorrido
		int index = 0;
		int[] dfs = new int[cantidadNodos];

		// Visitamos el nodo origen, lo ponemos en el recorrido y lo apilamos
		nodosVisitados[nodoOrigen - 1] = true;
		dfs[index++] = nodoOrigen;
		pila.push(nodoOrigen);

		// Empezamos DFS
		while (!pila.isEmpty()) {
			
			// Obtenemos los nodos adyacentes al que está en tope de pila 
			int[] nodosAdyacentes = getConjuntoAdyacentes(pila.peek());
			
			// Si no es el conjunto vacío...
			int i = 0;
			if (nodosAdyacentes != null) {
				// Mientras haya nodos adyacentes, y esten visitados: los ignoramos
				while (i < nodosAdyacentes.length && nodosVisitados[nodosAdyacentes[i] - 1] == true) {
					// Si encontramos un nodo adyacente no visitado
					i++;
				}
				if (i < nodosAdyacentes.length) {
					// Lo visitamos, lo ponemos en el recorrido y lo apilamos
					nodosVisitados[nodosAdyacentes[i] - 1] = true;
					dfs[index++] = nodosAdyacentes[i];
					pila.push(nodosAdyacentes[i]);
				} else {
					// Caso contrario desapilamos el nodo puesto que: o no tiene adyacentes
					// o todos sus adyacentes están visitados
					pila.pop();
				}
			}
		}

		// Devolvemos el recorrido
		return dfs;
	}

	// Anchura = BFS TODO: No funciona con grafos no conexos
	public int[] recorrerEnAnchura(int nodoOrigen) {
		verificarIndice(nodoOrigen, "El nodo origen no existe");

		int cantidadNodos = matrizDeAdyacencia.length;
		
		// Si el grafo está vacio no hay recorrido
		if(cantidadNodos == 0) {
			return null;
		}
		
		// Creamos e inicializamos el array de distancias
		int diametro = 0;
		int[] distancias = new int[cantidadNodos];
		for (int i = 0; i < cantidadNodos; i++) {
			distancias[i] = -1;
		}

		// Creamos la cola para visitar nodos
		Deque<Integer> cola = new ArrayDeque<Integer>();

		// Creamos el array que contiene el recorrido
		int index = 0;
		int[] bfs = new int[cantidadNodos];
		
		// Visitamos el nodo origen, lo ponemos en el recorrido y lo encolamos
		distancias[nodoOrigen - 1] = diametro;
		bfs[index++] = nodoOrigen;
		cola.offer(nodoOrigen);
		diametro++;
		
		// Empezamos BFS
		while(!cola.isEmpty()) {
			// Recalculamos el diámetro
			diametro = distancias[cola.peek() - 1] + 1;

			// Obtenemos los nodos adyacentes al que está en tope de cola 
			int[] nodosAdyacentes = getConjuntoAdyacentes(cola.peek());
			
			// Quitamos el nodo en tope de cola
			cola.poll();

			// Si no es el conjunto vacío...
			if (nodosAdyacentes != null) {
				// Mientras haya nodos adyacentes los visitamos todos
				for (int i = 0; i < nodosAdyacentes.length; i++) {
					// Si el nodo todavía no fue visitado: 
					// lo visitamos, lo ponemos en el recorrido y lo encolamos
					if(distancias[nodosAdyacentes[i] - 1] == -1) {
						distancias[nodosAdyacentes[i] - 1] = diametro;
						bfs[index++] = nodosAdyacentes[i];
						cola.offer(nodosAdyacentes[i]);
					}
				}	
			}
		}
	
		// Devolvemos el recorrido
		return bfs;
	}

	protected void verificarIndice(int nodo, String msj) {
		if (nodo <= 0 || nodo >= matrizDeAdyacencia.length + 1) {
			throw new IllegalArgumentException(msj);
		}
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();

		// Calculo de padding
		int padding = String.valueOf(matrizDeAdyacencia.length).length();
		
		// Strings para booleans
		String trueValue = String.format("%" + padding + "d ", 1);
		String falseValue = String.format("%" + padding + "d ", 0);
		
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
				strBuilder.append(matrizDeAdyacencia[i][j] == true ? trueValue : falseValue);
			}
			if (i + 1 != matrizDeAdyacencia.length) {
				strBuilder.append(System.getProperty("line.separator"));
			}
		}
		
		return strBuilder.toString();
	}

	public static void main(String[] args) {
		
		final boolean STRESS_TEST = false;
		
		DigrafoSimple grafo = new DigrafoSimple(10);
		grafo.agregarArco(1, 2);
		grafo.agregarArco(2, 1);
		grafo.agregarArco(2, 3);
		grafo.agregarArco(2, 4);
		grafo.agregarArco(3, 2);
		grafo.agregarArco(3, 5);
		grafo.agregarArco(3, 6);
		grafo.agregarArco(3, 9);
		grafo.agregarArco(4, 2);
		grafo.agregarArco(4, 5);
		grafo.agregarArco(4, 8);
		grafo.agregarArco(5, 3);
		grafo.agregarArco(5, 4);
		grafo.agregarArco(5, 6);
		grafo.agregarArco(5, 7);
		grafo.agregarArco(6, 3);
		grafo.agregarArco(6, 5);
		grafo.agregarArco(6, 7);
		grafo.agregarArco(7, 5);
		grafo.agregarArco(7, 6);
		grafo.agregarArco(8, 4);
		grafo.agregarArco(9, 3);
		grafo.agregarArco(9, 10);
		grafo.agregarArco(10, 9);
		System.out.println(grafo);
		
		int[] dfs = grafo.recorrerEnProfundidad(6);
		for (int i = 0; i < dfs.length; i++) {
			System.out.print(dfs[i]);
			if (i != dfs.length - 1) {
				System.out.print(" -> ");
			}
		}
		System.out.println();
		
		int[] bfs = grafo.recorrerEnAnchura(6);
		for (int i = 0; i < bfs.length; i++) {
			System.out.print(bfs[i]);
			if (i != bfs.length - 1) {
				System.out.print(" -> ");
			}
		}
		System.out.println();
		
		if(STRESS_TEST) {
			// Test de stress 1: grafo denso y grande (completo)
			int cantidadNodos = 10000;
			grafo = new DigrafoSimple(cantidadNodos);
			for(int i = 0; i < cantidadNodos; i++) {
				for(int j = 0; j < cantidadNodos; j++) {
					grafo.agregarArco(i + 1, j + 1);
					grafo.agregarArco(j + 1, i + 1);
				}
			}
			
			dfs = grafo.recorrerEnProfundidad(1);
			for (int i = 0; i < dfs.length; i++) {
				System.out.print(dfs[i]);
				if (i != dfs.length - 1) {
					System.out.print(" -> ");
				}
			}
			System.out.println();
			
			bfs = grafo.recorrerEnAnchura(1);
			for (int i = 0; i < bfs.length; i++) {
				System.out.print(bfs[i]);
				if (i != bfs.length - 1) {
					System.out.print(" -> ");
				}
			}
			System.out.println();
			
			// Test de stress 2: grafo poco denso y grande (lineal)
			grafo = new DigrafoSimple(cantidadNodos);
			for(int i = 0; i < cantidadNodos; i++) {
				for(int j = 0; j < cantidadNodos; j++) {
					if(i - j == 1) {
						grafo.agregarArco(i + 1, j + 1);
						grafo.agregarArco(j + 1, i + 1);
					}
				}
			}
					
			dfs = grafo.recorrerEnProfundidad(1);
			for (int i = 0; i < dfs.length; i++) {
				System.out.print(dfs[i]);
				if (i != dfs.length - 1) {
					System.out.print(" -> ");
				}
			}
			System.out.println();
			
			bfs = grafo.recorrerEnAnchura(1);
			for (int i = 0; i < bfs.length; i++) {
				System.out.print(bfs[i]);
				if (i != bfs.length - 1) {
					System.out.print(" -> ");
				}
			}
			System.out.println();
		}
	}

}