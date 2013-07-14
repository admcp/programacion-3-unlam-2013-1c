package ar.edu.unlam.programacion3.adt.grafo;

import java.util.ArrayList;
import java.util.List;

public class GrafoNoPesado {

	private boolean[][] matrizDeAdyacencia;

	public GrafoNoPesado(int cantidadDeNodos) {
		if (cantidadDeNodos <= 0) {
			throw new IllegalArgumentException("La cantidad de nodos debe ser mayor que cero.");
		}

		matrizDeAdyacencia = new boolean[cantidadDeNodos][cantidadDeNodos];
		for (int i = 0; i < cantidadDeNodos; i++) {
			for (int j = 0; j < cantidadDeNodos; j++) {
				matrizDeAdyacencia[i][j] = false;
			}
		}
	}
	
	public int cantidadDeNodos() {
		return matrizDeAdyacencia.length;
	}
	
	public int cantidadDeArcos() {
		int cantidadDeArcos = 0;
		for(int i = 0; i < matrizDeAdyacencia.length; i++) {
			for(int j = i; j < matrizDeAdyacencia.length; j++) {
				if(matrizDeAdyacencia[i][j] == true) {
					cantidadDeArcos++;
				}
			}
		}
		return cantidadDeArcos;
	}
	
	public void agregarArco(int nodoOrigen, int nodoDestino) {
		verificarNodo(nodoOrigen, "El nodo origen no es válido.");
		verificarNodo(nodoDestino, "El nodo destino no es válido.");
		
		matrizDeAdyacencia[nodoOrigen][nodoDestino] = true;
		matrizDeAdyacencia[nodoDestino][nodoOrigen] = true;
	}
	
	public void quitarArco(int nodoOrigen, int nodoDestino) {
		verificarNodo(nodoOrigen, "El nodo origen no es válido.");
		verificarNodo(nodoDestino, "El nodo destino no es válido.");
		
		matrizDeAdyacencia[nodoOrigen][nodoDestino] = false;
		matrizDeAdyacencia[nodoDestino][nodoOrigen] = false;
	}
	
	public List<Arco> conjuntoArcos() {
		List<Arco> arcos = new ArrayList<Arco>();
		for(int i = 0; i < matrizDeAdyacencia.length; i++) {
			for(int j = i; j < matrizDeAdyacencia.length; j++) {
				if(matrizDeAdyacencia[i][j] == true) {
					arcos.add(new Arco(i, j));
				}
			}
		}
		return arcos;
	}
	
	public int grado(int nodo) {
		verificarNodo(nodo, "El nodo no es válido");
		
		int grado = 0;
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			if(matrizDeAdyacencia[nodo][i] == true) {
				grado++;
			}
		}
		
		return grado;
	}
	
	public boolean esRegular() {
		int[] grados = new int[matrizDeAdyacencia.length];
		
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			grados[i] = grado(i);
		}
		
		for (int i = 0; i < grados.length; i++) {
			if(grados[0] != grados[i]) {
				return false;
			}
		}		
		
		return true;
	}
	
	public boolean esKRegular(int grado) {
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			if(grado(i) != grado) {
				return false;
			}
		}
		
		return true;
	}
	
	public void reinicializarGrafo() {
		for (int i = 0; i < matrizDeAdyacencia.length; i++) {
			for (int j = 0; j < matrizDeAdyacencia.length; j++) {
				matrizDeAdyacencia[i][j] = false;
			}
		}
	}
	
	private void verificarNodo(int nodo, String msg) {
		if(nodo < 0 || nodo >= matrizDeAdyacencia.length) {
			throw new IllegalArgumentException(msg);
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

}
