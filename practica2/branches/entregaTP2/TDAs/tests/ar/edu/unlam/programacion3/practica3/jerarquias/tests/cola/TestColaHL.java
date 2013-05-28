package ar.edu.unlam.programacion3.practica3.jerarquias.tests.cola;

import ar.edu.unlam.programacion3.practica3.jerarquias.cola.ColaHL;

public class TestColaHL {

	public static void main(String[] args) {
		ColaHL<Integer> cola = new ColaHL<Integer>();
		
		for(int i = 0; i < 10; i++) {
			System.out.println("Encolado: " + i);
			cola.offer(i);
		}
		
		System.out.println("Espiando cola (elemento listo para salir): " + cola.peek());
		
		for(int i = 0; i < 5; i++) {
			System.out.println("Desencolado: " + cola.poll());
		}
		
		for(int i = 10; i < 15; i++) {
			System.out.println("Encolado: " + i);
			cola.offer(i);
		}
		
		System.out.println("Cola vacia: " + (cola.isEmpty() == true ? "Si" : "No"));
		
		System.out.println("Vaciando cola.");
		cola.clear();
		
		System.out.println("Cola vacia: " + (cola.isEmpty() == true ? "Si" : "No"));
	}
	
}
