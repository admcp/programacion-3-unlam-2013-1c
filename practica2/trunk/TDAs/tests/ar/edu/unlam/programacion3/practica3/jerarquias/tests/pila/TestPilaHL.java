package ar.edu.unlam.programacion3.practica3.jerarquias.tests.pila;

import ar.edu.unlam.programacion3.practica2.tda.pila.Pila;
import ar.edu.unlam.programacion3.practica3.jerarquias.pila.PilaHL;

public class TestPilaHL {

	public static void main(String[] args) {
		testRegular();
		testRendimiento();
	}
	
	private static void testRegular() {
		Pila<String> pila = new PilaHL<String>();

		System.out.println("Apilando...");
		for (int i = 0; i < 10; i++) {
			System.out.println(i + " - Push");
			pila.push(Integer.toString(i));
		}

		System.out.println();

		System.out.println(pila.peek() + " - Peek");

		System.out.println();

		System.out.println(pila.pop() + " - Pop");

		System.out.println();

		System.out.println("Nuevo elemento - Push");

		pila.push("Nuevo elemento");

		System.out.println(pila.peek() + " - Peek");

		System.out.println();

		System.out.println("Desapilando...");
		while (!pila.isEmpty()) {
			System.out.println(pila.pop() + " - Pop");
		}

		System.out.println();

		System.out.println("Apilando...");
		for (int i = 0; i < 10; i++) {
			System.out.println(i + " - Push");
			pila.push(Integer.toString(i));
		}

		System.out.println();

		System.out.println(pila.peek() + " - Peek");

		System.out.println("clear");
		pila.clear();

		System.out.println();

		System.out.println(pila.isEmpty() + " - isEmpty");
		
		pila = null;
	}
	
	private static void testRendimiento() {
		long tiempoInicial = 0;
		long tiempoFinal = 0;
		final int dimension = 1000000;
		
		Pila<Integer> pilaMillon = new PilaHL<Integer>();
		
		System.out.println("Rendimiento de PilaHL con " + dimension + " de elementos.");
		
		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			pilaMillon.push(i);
		}
		tiempoFinal = System.currentTimeMillis();
		
		System.out.println("Tiempo Total (push): " + (tiempoFinal - tiempoInicial) + " ms");
		
		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			pilaMillon.pop();			
		}
		tiempoFinal = System.currentTimeMillis();
		
		System.out.println("Tiempo Total (pop): " + (tiempoFinal - tiempoInicial) + " ms");
		
		pilaMillon = null;
	}
	
}
