package ar.edu.unlam.programacion3.practica2.tda.tests.cola;

import ar.edu.unlam.programacion3.practica2.tda.cola.Cola;
import ar.edu.unlam.programacion3.practica2.tda.cola.ColaDinamica;

public class TestColaDinamica {

	public static void main(String[] args) {
		testRegular();
		testRendimiento();
	}
	
	public static void testRegular() {
		ColaDinamica<String> cola = new ColaDinamica<String>();
		
		System.out.println("Encolando...");
		for(int i = 0; i < 10; i++) {
			System.out.println(i + " - offer");
			cola.offer(Integer.toString(i));
		}
		
		System.out.println();
		
		System.out.println(cola.peek() + " - Peek");
		
		System.out.println();
		
		System.out.println(cola.poll() + " - poll");
		
		System.out.println();
		
		System.out.println("Nuevo elemento - offer");
		
		cola.offer("Nuevo elemento");
		
		System.out.println(cola.peek() + " - Peek");
		
		System.out.println();
		
		System.out.println("Desencolando...");
		while (!cola.isEmpty()) {
			System.out.println(cola.poll() + " - poll");
		}

		System.out.println();
		
		System.out.println("Encolando...");
		for(int i = 0; i < 10; i++) {
			System.out.println(i + " - offer");
			cola.offer(Integer.toString(i));
		}
		
		System.out.println();
		
		System.out.println(cola.peek() + " - Peek");
		
		System.out.println("clear");
		cola.clear();
		
		System.out.println();
		
		System.out.println(cola.isEmpty() + " - isEmpty");
	
	}
	
	private static void testRendimiento() {
		long tiempoInicial = 0;
		long tiempoFinal = 0;
		final int dimension = 1000000;

		Cola<Integer> colaMillon = new ColaDinamica<Integer>();

		System.out.println("Rendimiento de ColaDinamica con " + dimension + " de elementos.");

		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			colaMillon.offer(i);
		}
		tiempoFinal = System.currentTimeMillis();

		System.out.println("Tiempo Total (offer): " + (tiempoFinal - tiempoInicial) + " ms");

		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			colaMillon.poll();
		}
		tiempoFinal = System.currentTimeMillis();

		System.out.println("Tiempo Total (poll): " + (tiempoFinal - tiempoInicial) + " ms");

		colaMillon = null;
	}
}
