package ar.edu.unlam.programacion3.practica2.tda.tests.pila;

import ar.edu.unlam.programacion3.practica2.tda.pila.PilaHL;

public class TestPilaHL {

	public static void main(String[] args) {
		PilaHL<String> pila = new PilaHL<String>();
		
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
}
