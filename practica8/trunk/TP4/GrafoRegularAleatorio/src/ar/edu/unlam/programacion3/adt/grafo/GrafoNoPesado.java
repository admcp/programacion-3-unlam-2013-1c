package ar.edu.unlam.programacion3.adt.grafo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GrafoNoPesado implements Cloneable {

	private boolean[][] matrizDeAdyacencia;
	private int cantidadDeNodos;
	
	private Nodo[] conjuntoDeNodos;
	private int numeroCromatico;

	public GrafoNoPesado(int cantidadDeNodos) {
		if (cantidadDeNodos <= 0) {
			throw new IllegalArgumentException("La cantidad de nodos debe ser mayor que cero.");
		}

		this.cantidadDeNodos = cantidadDeNodos;
		matrizDeAdyacencia = new boolean[cantidadDeNodos][cantidadDeNodos];
		for (int i = 0; i < cantidadDeNodos; i++) {
			for (int j = 0; j < cantidadDeNodos; j++) {
				matrizDeAdyacencia[i][j] = false;
			}
		}
		conjuntoDeNodos = new Nodo[cantidadDeNodos];
		for(int i = 0; i < cantidadDeNodos; i++) {
			conjuntoDeNodos[i] = new Nodo(i);
		}
		
	}
	
	public int cantidadDeNodos() {
		return cantidadDeNodos;
	}
	
	public int cantidadDeArcos() {
		int cantidadDeArcos = 0;
		for(int i = 0; i < cantidadDeNodos; i++) {
			for(int j = i; j < cantidadDeNodos; j++) {
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
		conjuntoDeNodos[nodoOrigen].incrementarGrado();
		conjuntoDeNodos[nodoDestino].incrementarGrado();
	}
	
	public void quitarArco(int nodoOrigen, int nodoDestino) {
		verificarNodo(nodoOrigen, "El nodo origen no es válido.");
		verificarNodo(nodoDestino, "El nodo destino no es válido.");
		
		matrizDeAdyacencia[nodoOrigen][nodoDestino] = false;
		matrizDeAdyacencia[nodoDestino][nodoOrigen] = false;
		conjuntoDeNodos[nodoOrigen].decrementarGrado();
		conjuntoDeNodos[nodoDestino].decrementarGrado();
	}
	
	public List<Arco> conjuntoArcos() {
		List<Arco> arcos = new ArrayList<Arco>();
		for(int i = 0; i < cantidadDeNodos; i++) {
			for(int j = i; j < cantidadDeNodos; j++) {
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
		for (int i = 0; i < cantidadDeNodos; i++) {
			if(matrizDeAdyacencia[nodo][i] == true) {
				grado++;
			}
		}
		
		return grado;
	}
	
	public boolean esRegular() {
		int[] grados = new int[cantidadDeNodos];
		
		for (int i = 0; i < cantidadDeNodos; i++) {
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
		for (int i = 0; i < cantidadDeNodos; i++) {
			if(grado(i) != grado) {
				return false;
			}
		}
		
		return true;
	}

	public boolean sonAdyacentes(int nodoOrigen, int nodoDestino) {
		verificarNodo(nodoOrigen, "El nodo origen no es válido.");
		verificarNodo(nodoDestino, "El nodo destino no es válido.");
		
		return matrizDeAdyacencia[nodoOrigen][nodoDestino];
	}
	
	public void reinicializarGrafo() {
		for (int i = 0; i < cantidadDeNodos; i++) {
			for (int j = 0; j < cantidadDeNodos; j++) {
				matrizDeAdyacencia[i][j] = false;
			}
		}
	}
	
	public int getNumeroCromatico() {
		return numeroCromatico;
	}

	public void setNumeroCromatico(int numeroCromatico) {
		this.numeroCromatico = numeroCromatico;
	}
	
	public Nodo[] getNodos() {
		return conjuntoDeNodos;
	}
	
	public Nodo getNodo(int nodo) {
		verificarNodo(nodo, "El nodo no es válido");
		
		return conjuntoDeNodos[nodo];
	}
	
	public Nodo[] ordenar(int tipoDeOrdenamiento) {
		List<Nodo> listaNodos = new ArrayList<Nodo>(Arrays.asList(getNodos()));
		
		switch (tipoDeOrdenamiento) {
			case 0:
				break;
			case 1:
				Collections.sort(listaNodos);
				break;
			case 2:
				Collections.sort(listaNodos);
				Collections.reverse(listaNodos);
				break;
			default:
				System.err.println("Orden incorrecto.");
				break;
		}
		return listaNodos.toArray(new Nodo[0]);
	}
	
	private void verificarNodo(int nodo, String msg) {
		if(nodo < 0 || nodo >= cantidadDeNodos) {
			throw new IllegalArgumentException(msg);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();

		// Calculo de padding
		int padding = String.valueOf(cantidadDeNodos).length();
		
		// Strings para booleans
		String trueValue = String.format("%" + padding + "d ", 1);
		String falseValue = String.format("%" + padding + "d ", 0);
		
		// Encabezado
		for(int i = 0; i < padding + 1; i++) {
			strBuilder.append(" ");
		}
		for (int i = 0; i < cantidadDeNodos; i++) {
			strBuilder.append(String.format("%" + padding + "d", i + 1) + " ");
		}
		strBuilder.append(System.getProperty("line.separator"));
		
		// Matriz de Adyacencia
		for (int i = 0; i < cantidadDeNodos; i++) {
			// # de nodo al principio 
			strBuilder.append(String.format("%" + padding + "d", i + 1) + " ");
			for (int j = 0; j < cantidadDeNodos; j++) {
				strBuilder.append(matrizDeAdyacencia[i][j] == true ? trueValue : falseValue);
			}
			if (i + 1 != cantidadDeNodos) {
				strBuilder.append(System.getProperty("line.separator"));
			}
		}
		
		return strBuilder.toString();
	}
	
	@Override
	public GrafoNoPesado clone() {
		GrafoNoPesado clon = null;
		try {
			clon = (GrafoNoPesado) super.clone();
		} catch(CloneNotSupportedException ex) {
			throw new AssertionError();
		}
		
		clon.cantidadDeNodos = cantidadDeNodos;
		
		clon.matrizDeAdyacencia = new boolean[cantidadDeNodos][cantidadDeNodos];
		
		for(int i = 0; i < cantidadDeNodos; i++) {
			for(int j = 0; j < cantidadDeNodos; j++) {
				clon.matrizDeAdyacencia[i][j] = matrizDeAdyacencia[i][j];
			}
		}
		
		clon.numeroCromatico = numeroCromatico;
		
		clon.conjuntoDeNodos = new Nodo[cantidadDeNodos];
		
		for(int i = 0; i < clon.cantidadDeNodos; i++) {
			clon.conjuntoDeNodos[i] = conjuntoDeNodos[i].clone();
		}
		
		return clon;
	}

}
