package ar.edu.unlam.programacion3.practica3.jerarquias.tests.cola;

import ar.edu.unlam.programacion3.practica2.tda.cola.Cola;
import ar.edu.unlam.programacion3.practica3.jerarquias.cola.ColaHL;

public class TestColaHL {

	public static void main(String[] args) {
		testRegular();
		testRendimiento();
	}
	
	private static void testRegular() {
		Cola<Integer> cola = new ColaHL<Integer>();

		for (int i = 0; i < 10; i++) {
			System.out.println("Encolado: " + i);
			cola.offer(i);
		}

		System.out.println("Espiando cola (elemento listo para salir): " + cola.peek());

		for (int i = 0; i < 5; i++) {
			System.out.println("Desencolado: " + cola.poll());
		}

		for (int i = 10; i < 15; i++) {
			System.out.println("Encolado: " + i);
			cola.offer(i);
		}

		System.out.println("Cola vacia: " + (cola.isEmpty() == true ? "Si" : "No"));

		System.out.println("Vaciando cola.");
		cola.clear();

		System.out.println("Cola vacia: " + (cola.isEmpty() == true ? "Si" : "No"));
		
		cola = null;
	}
	
	private static void testRendimiento() {
		long tiempoInicial = 0;
		long tiempoFinal = 0;
		final int dimension = 1000000;
		
		Cola<Integer> colaMillon = new ColaHL<Integer>();
		
		System.out.println("Rendimiento de ColaHL con " + dimension + " de elementos.");
		
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
