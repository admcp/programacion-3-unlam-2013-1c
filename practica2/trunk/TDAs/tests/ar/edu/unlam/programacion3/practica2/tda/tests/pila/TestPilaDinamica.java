package ar.edu.unlam.programacion3.practica2.tda.tests.pila;

import ar.edu.unlam.programacion3.practica2.tda.pila.Pila;
import ar.edu.unlam.programacion3.practica2.tda.pila.PilaDinamica;

public class TestPilaDinamica {

	public static void main(String[] args) {
		testRegular();
		testRendimiento();
	}
	
	private static void testRegular() {
		PilaDinamica<String> pila = new PilaDinamica<String>();
		
		System.out.println("Apilando...");
		for(int i = 0; i < 10; i++) {
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
		for(int i = 0; i < 10; i++) {
			System.out.println(i + " - Push");
			pila.push(Integer.toString(i));
		}
		
		System.out.println();
		
		System.out.println(pila.peek() + " - Peek");
		
		System.out.println("clear");
		pila.clear();
		
		System.out.println();
		
		System.out.println(pila.isEmpty() + " - isEmpty");
	}

	private static void testRendimiento() {
		long tiempoInicial = 0;
		long tiempoFinal = 0;
		final int dimension = 1000000;
		
		Pila<Integer> pilaMillon = new PilaDinamica<Integer>();
		
		System.out.println("Rendimiento de PilaDinamica con " + dimension + " de elementos.");
		
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
