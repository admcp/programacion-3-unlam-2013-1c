package ar.edu.unlam.programacion3.practica2.tda.tests.cola;

import ar.edu.unlam.programacion3.practica2.tda.cola.ColaCL;

public class TestColaCL {

	public static void main(String[] args) {
		ColaCL<Integer> cola = new ColaCL<Integer>();
		
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
