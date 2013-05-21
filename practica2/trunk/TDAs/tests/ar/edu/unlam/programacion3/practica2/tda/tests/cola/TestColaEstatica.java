package ar.edu.unlam.programacion3.practica2.tda.tests.cola;

import ar.edu.unlam.programacion3.practica2.tda.cola.ColaEstatica;

public class TestColaEstatica {

	public static void main(String[] args) {
		ColaEstatica<String> pila = new ColaEstatica<String>(10);
		
		System.out.println("Encolando...");
		for(int i = 0; i < 10; i++) {
			System.out.println(i + " - offer");
			pila.offer(Integer.toString(i));
		}
		
		System.out.println();
		
		System.out.println(pila.peek() + " - Peek");
		
		System.out.println();
		
		System.out.println(pila.poll() + " - poll");
		
		System.out.println();
		
		System.out.println("Nuevo elemento - offer");
		
		pila.offer("Nuevo elemento");
		
		System.out.println(pila.peek() + " - Peek");
		
		System.out.println();
		
		System.out.println("Desencolando...");
		while (!pila.isEmpty()) {
			System.out.println(pila.poll() + " - poll");
		}

		System.out.println();
		
		System.out.println("Encolando...");
		for(int i = 0; i < 10; i++) {
			System.out.println(i + " - offer");
			pila.offer(Integer.toString(i));
		}
		
		System.out.println();
		
		System.out.println(pila.peek() + " - Peek");
		
		System.out.println("clear");
		pila.clear();
		
		System.out.println();
		
		System.out.println(pila.isEmpty() + " - isEmpty");
	}
}
