package ar.edu.unlam.programacion3.taller.iteradores.tests;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ar.edu.unlam.programacion3.taller.iteradores.Pila;

public class PilaTest {
	
	public static void main(String[] args) {
	
		// Creamos una pila
		Pila<Integer> pila = new Pila<Integer>();
		
		// La llenamos con los primeros 10 naturales
		for(int i = 0; i < 10; i++) {
			pila.push(i + 1);
		}
		
		// La mostramos como una pila
		for(int valorApilado : pila) {
			System.out.print(valorApilado + " ");
		}
		System.out.println();
		
		// Otra forma (más compleja para hacer lo mismo)
		Iterator<Integer> iteradorPila = pila.iterator();
		while(iteradorPila.hasNext()) {
			System.out.print(iteradorPila.next() + " ");
		}
		System.out.println();
		
		// Por supuesto no se puede ir más allá una vez recorrida la pila
		try {
			iteradorPila.next();
		} catch(NoSuchElementException ex) {
			System.out.println("No hay mas elementos para iterar.");
		}
				
		// Salvo que pidamos un nuevo iterador:
		iteradorPila = pila.iterator();
		
		// La razon por la cual no permitimos hacer un remove está
		// en esta segunda forma de usar el iterador: alguien podría hacer
		// remove de un elemento en el medio de la pila (y ya no sería una pila).		
		try {
			iteradorPila.remove();
		} catch(UnsupportedOperationException ex) {
			System.out.println("Utilice pop para quitar elementos de la pila.");
		}
		
	}
	
}
