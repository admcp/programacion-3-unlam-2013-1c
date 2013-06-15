package ar.edu.unlam.programacion3.practicaadhoc.tda.tests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AnalizadorRendimientoListas {

	public static void main(String[] args) {
		// Para medir los tiempos
		long tiempoInicial = 0;
		long tiempoFinal = 0;
		
		// Listas de valores
		final int dimension = 1000000;
		List<MyInteger> linkedList = null;
		List<MyInteger> arrayList = null;
		
		// Valores
		MyInteger arrayDeObjetos[] = new MyInteger[dimension];
		
		Random random = new Random();
		int[] indicesAleatorios = new int[dimension];
		
		for(int i = 0; i < dimension; i++) {
			indicesAleatorios[i] = random.nextInt(dimension - i);
		}
		
		// ACCESO POR INDICES (aleatorio)
		System.out.println("Add/Remove usando índices.\n");
		
		for (int i = 0; i < dimension; i++) {
			arrayDeObjetos[i] = new MyInteger(i);
		}
		
		linkedList = new LinkedList<MyInteger>();
		arrayList = new ArrayList<MyInteger>();
		
		System.out.println("Rendimiento de LinkedList con " + dimension + " de elementos.");
		
		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			linkedList.add(arrayDeObjetos[i]);
		}
		tiempoFinal = System.currentTimeMillis();

		System.out.println("Tiempo Total (add): " + (tiempoFinal - tiempoInicial) + " ms");
		
		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			linkedList.remove(indicesAleatorios[i]);
		}
		tiempoFinal = System.currentTimeMillis();

		System.out.println("Tiempo Total (remove): " + (tiempoFinal - tiempoInicial) + " ms");
		
		System.out.println("Rendimiento de ArrayList con " + dimension + " de elementos.");
		for (int i = 0; i < dimension; i++) {
			arrayDeObjetos[i] = new MyInteger(i);
		}

		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			arrayList.add(arrayDeObjetos[i]);
		}
		tiempoFinal = System.currentTimeMillis();

		System.out.println("Tiempo Total (add): " + (tiempoFinal - tiempoInicial) + " ms");
		
		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			arrayList.remove(indicesAleatorios[i]);
		}
		tiempoFinal = System.currentTimeMillis();

		System.out.println("Tiempo Total (remove): " + (tiempoFinal - tiempoInicial) + " ms");
		
		// ACCESO POR OBJETO
		System.out.println("\nAdd/Remove usando objetos.\n");

		for (int i = 0; i < dimension; i++) {
			arrayDeObjetos[i] = new MyInteger(i);
		}
		
		linkedList = new LinkedList<MyInteger>();
		arrayList = new ArrayList<MyInteger>();
		
		System.out.println("Rendimiento de LinkedList con " + dimension + " de elementos.");

		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			linkedList.add(arrayDeObjetos[i]);
		}
		tiempoFinal = System.currentTimeMillis();

		System.out.println("Tiempo Total (add): " + (tiempoFinal - tiempoInicial) + " ms");
		
		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			linkedList.remove(arrayDeObjetos[i]);
		}
		tiempoFinal = System.currentTimeMillis();

		System.out.println("Tiempo Total (remove): " + (tiempoFinal - tiempoInicial) + " ms");
		
		System.out.println("Rendimiento de ArrayList con " + dimension + " de elementos.");

		for (int i = 0; i < dimension; i++) {
			arrayDeObjetos[i] = new MyInteger(i);
		}

		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			arrayList.add(arrayDeObjetos[i]);
		}
		tiempoFinal = System.currentTimeMillis();

		System.out.println("Tiempo Total (add): " + (tiempoFinal - tiempoInicial) + " ms");
		
		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			arrayList.remove(arrayDeObjetos[i]);
		}
		tiempoFinal = System.currentTimeMillis();

		System.out.println("Tiempo Total (remove): " + (tiempoFinal - tiempoInicial) + " ms");
	}

}

class MyInteger {
	private Integer valor;
	
	public MyInteger(int valor) {
		this.valor = valor;
	}
	
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public int getValor() {
		return valor;
	}
}
