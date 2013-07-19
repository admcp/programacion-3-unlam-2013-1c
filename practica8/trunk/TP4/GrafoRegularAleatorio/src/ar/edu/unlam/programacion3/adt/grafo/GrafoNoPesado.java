package ar.edu.unlam.programacion3.adt.grafo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrafoNoPesado {

	private boolean[][] matrizDeAdyacencia;
	private int cantidadDeNodos;
	
	private Nodo[] conjuntoDeNodos;
	private int numeroCromatico;
	
	// CONSTRUCTORES

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
	
	public GrafoNoPesado(String uriArchivo) throws FileNotFoundException, IOException, NumberFormatException {
		// Abrimos el archivo
		BufferedReader bufferEntrada = new BufferedReader(new FileReader(new File(uriArchivo)));
		
		String buffer;
		String[] splitBuffer;

		// Leemos el encabezado del grafo regular
		buffer = bufferEntrada.readLine();
		splitBuffer = buffer.split(" ");

		int cantidadDeNodos = Integer.parseInt(splitBuffer[0]);
		int cantidadDeArcos = Integer.parseInt(splitBuffer[1]);
		
		// Creamos la matriz de adyacencia e incializamos en false
		this.cantidadDeNodos = cantidadDeNodos;
		matrizDeAdyacencia = new boolean[cantidadDeNodos][cantidadDeNodos];
		for (int i = 0; i < cantidadDeNodos; i++) {
			for (int j = 0; j < cantidadDeNodos; j++) {
				matrizDeAdyacencia[i][j] = false;
			}
		}
		
		// Creamos el conjunto de nodos coloreados
		conjuntoDeNodos = new Nodo[cantidadDeNodos];
		for(int i = 0; i < cantidadDeNodos; i++) {
			conjuntoDeNodos[i] = new Nodo(i);
		}
		
		// Leemos los arcos y los vamos incorporando al grafo
		for (int i = 0; i < cantidadDeArcos; i++) {
			buffer = bufferEntrada.readLine();
			splitBuffer = buffer.split(" ");

			int nodoOrigen = Integer.parseInt(splitBuffer[0]);
			int nodoDestino = Integer.parseInt(splitBuffer[1]);

			matrizDeAdyacencia[nodoOrigen][nodoDestino] = true;
			matrizDeAdyacencia[nodoDestino][nodoOrigen] = true;
			conjuntoDeNodos[nodoOrigen].incrementarGrado();
			conjuntoDeNodos[nodoDestino].incrementarGrado();
		}
		
		// Cerramos el archivo
		bufferEntrada.close();
	}
	
	// GETTERS
	
	public int cantidadDeNodos() {
		return cantidadDeNodos;
	}
	
	public Nodo[] getNodos() {
		return conjuntoDeNodos;
	}
	
	public Nodo getNodo(int nodo) {
		verificarNodo(nodo, "El nodo no es válido");
		
		return conjuntoDeNodos[nodo];
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
	
	public int getNumeroCromatico() {
		return numeroCromatico;
	}
	
	// SETTERS
	
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
	
	// OPERADORES PARA GRADO
	
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
	
	public int gradoMaximo() {
		int gradoMaximo = Integer.MIN_VALUE;
		for(Nodo nodo : conjuntoDeNodos) {
			if(nodo.getGrado() > gradoMaximo) {
				gradoMaximo = nodo.getGrado();
			}
		}
		return gradoMaximo;
	}
	
	public int gradoMinimo() {
		int gradoMinimo = Integer.MAX_VALUE;
		for(Nodo nodo : conjuntoDeNodos) {
			if(nodo.getGrado() < gradoMinimo) {
				gradoMinimo = nodo.getGrado();
			}
		}
		return gradoMinimo;
	}
	
	// OTROS

	public boolean sonAdyacentes(int nodoOrigen, int nodoDestino) {
		verificarNodo(nodoOrigen, "El nodo origen no es válido.");
		verificarNodo(nodoDestino, "El nodo destino no es válido.");
		
		return matrizDeAdyacencia[nodoOrigen][nodoDestino];
	}
	
	public Nodo[] obtenerAdyacentes(int nodo) {
		verificarNodo(nodo, "El nodo no es válido.");
		
		List<Nodo> listaAdyacentes = new ArrayList<Nodo>();
		for(int i = 0; i < cantidadDeNodos; i++) {
			if(matrizDeAdyacencia[nodo][i] == true) {
				listaAdyacentes.add(new Nodo(i));
			}
		}
		
		return listaAdyacentes.toArray(new Nodo[0]);
		
	}
	
	public void reinicializarGrafo() {
		for (int i = 0; i < cantidadDeNodos; i++) {
			for (int j = 0; j < cantidadDeNodos; j++) {
				matrizDeAdyacencia[i][j] = false;
			}
		}
	}
	
	// COLOREO
	
	public void coloreoSecuencial() {
		blanquear();
		colorear(ordenar(0));
	}

	public void coloreoMatula() {
		blanquear();
		colorear(ordenar(1));
	}
	
	public void coloreoPowell() {
		blanquear();
		colorear(ordenar(2));
	}
	
	private void blanquear() {
		for (int i = 0; i < conjuntoDeNodos.length; i++) {
			conjuntoDeNodos[i].setColor(0);
		}
		numeroCromatico = 0;
	}
	
	private Nodo[] ordenar(int tipoDeOrdenamiento) {
		List<Nodo> listaNodos = new ArrayList<Nodo>();
		for(Nodo nodo : conjuntoDeNodos) {
			listaNodos.add(nodo);
		}
		
		switch (tipoDeOrdenamiento) {
			case 0:
				break; // Orden natural de los nodos
			case 1:
				Collections.sort(listaNodos); // Orden creciente por grado: Matula
				break;
			case 2:
				Collections.sort(listaNodos);
				Collections.reverse(listaNodos); // Orden decreciente de por grado: Powell
				break;
			default:
				System.err.println("Orden incorrecto.");
				break;
		}
		
		return listaNodos.toArray(new Nodo[0]);
	}

	private void colorear(Nodo[] nodos) {
		int cantColoreados = 0;
		for (int i = 0; i < cantidadDeNodos && cantColoreados < cantidadDeNodos; i++) {
			int j = i;
			while (nodos[j].getColor() != 0) {
				j++;
			}
			nodos[j].setColor(i + 1);
			conjuntoDeNodos[nodos[j].getNombre()].setColor(i + 1);
			cantColoreados++;

			for (int k = j + 1; k < cantidadDeNodos; k++) {
				if (nodos[k].getColor() == 0 && !sonAdyacentes(nodos[j].getNombre(), nodos[k].getNombre())) {
					boolean colorear = true;
					for (int x = 0; x < k; x++) {
						if (nodos[x].getColor() == (i + 1) && sonAdyacentes(nodos[k].getNombre(), nodos[x].getNombre())) {
							colorear = false;
						}
					}
					if (colorear) {
						nodos[k].setColor(i + 1);
						conjuntoDeNodos[nodos[k].getNombre()].setColor(i + 1);
						cantColoreados++;
					}
				}
			}
			numeroCromatico = i + 1;
		}
	}
	
	public void coloreoSecuencial(String uriArchivo) throws FileNotFoundException {
		// Coloreamos		
		coloreoSecuencial();
		
		// Abrimos el archivo
		PrintWriter bufferSalida = new PrintWriter(new File(uriArchivo));
		
		// Escribimos el encabezado
		bufferSalida.println(cantidadDeNodos + " " +
							 numeroCromatico + " " +
				             gradoMinimo() + " " +
							 gradoMaximo());

		// Escribimos los nodos y sus colores
		for (int i = 0; i < cantidadDeNodos; i++) {
			bufferSalida.println(getNodo(i).getNombre() + " " + 
							     getNodo(i).getColor());
		}
		
		// Cerramos el archivo
		bufferSalida.close();
	}
	
	public void coloreoMatula(String uriArchivo) throws FileNotFoundException {
		// Coloreamos		
		coloreoMatula();
		
		// Abrimos el archivo
		PrintWriter bufferSalida = new PrintWriter(new File(uriArchivo));
		
		// Escribimos el encabezado
		bufferSalida.println(cantidadDeNodos + " " +
							 numeroCromatico + " " +
				             gradoMinimo() + " " +
							 gradoMaximo());

		// Escribimos los nodos y sus colores
		for (int i = 0; i < cantidadDeNodos; i++) {
			bufferSalida.println(getNodo(i).getNombre() + " " + 
							     getNodo(i).getColor());
		}
		
		// Cerramos el archivo
		bufferSalida.close();
	}
	
	public void coloreoPowell(String uriArchivo) throws FileNotFoundException {
		// Coloreamos		
		coloreoPowell();
		
		// Abrimos el archivo
		PrintWriter bufferSalida = new PrintWriter(new File(uriArchivo));
		
		// Escribimos el encabezado
		bufferSalida.println(cantidadDeNodos + " " +
							 numeroCromatico + " " +
				             gradoMinimo() + " " +
							 gradoMaximo());

		// Escribimos los nodos y sus colores
		for (int i = 0; i < cantidadDeNodos; i++) {
			bufferSalida.println(getNodo(i).getNombre() + " " + 
							     getNodo(i).getColor());
		}
		
		// Cerramos el archivo
		bufferSalida.close();
	}
	
	// UTILITARIOS
	
	private void verificarNodo(int nodo, String msg) {
		if(nodo < 0 || nodo >= cantidadDeNodos) {
			throw new IllegalArgumentException(msg);
		}
	}
	
	// HEREDADOS
	
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

}
