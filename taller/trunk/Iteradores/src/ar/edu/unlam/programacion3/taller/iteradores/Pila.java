package ar.edu.unlam.programacion3.taller.iteradores;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * Para usar iteradores es necesario que la clase en cuestión implemente Iterable.
 * Iterable como interfaz obliga a que la clase tenga un método iterator(), que
 * devuelve un Iterator. Es decir, que una clase sea Iterable, es justamente que
 * tenga alguna forma de ser iterada/recorrida (sin importar su implementación). 
 * ¿Qué ventaja tiene? Que se puede usar el "for avanzado" o "foreach" con dicha 
 * clase. 
 * Me permite, por ejemplo, si tengo una lista de Strings: 
 * 		List<String> listaNombres = new ArrayList<String>()
 * hacer algo como:
 * 		for(String nombre : listaNombres) {
 * 			System.out.println(nombre);
 * 		}
 * Que se lee como "por cada nombre (String) en la lista listaNombres (List<String>)
 * hacer esto (imprimir su valor)".
 */
public class Pila<T> implements Iterable<T> {

	private List<T> stackArray;
	private int size;
	
	public Pila() {
		this(10);
	}
	
	public Pila(int initialCapacity) {
		stackArray = new ArrayList<T>(initialCapacity);
		size = 0;
	}
	
	public void push(T elemento) {
		if(elemento == null) {
			throw new IllegalArgumentException();
		}
		stackArray.add(elemento);
		size++;
	}
	
	public T pop() {
		if(size == 0) {
			return null;
		}
		T elemento = stackArray.get(size);
		stackArray.remove(size);
		size--;
		return elemento;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public T peek() {
		return stackArray.get(size);
	}
	
	@Override
	public Iterator<T> iterator() {
		return new PilaIterator();
	}
	
	/*
	 * Para crear un Iterator se necesita implementar Iterator, y por ende, implementar
	 * tres métodos:
	 * 		boolean hasNext: ¿existe otro elemento en la colección?
	 * 		T next(): obtener el siguiente elemento.
	 * 		remove(): quitar el elemento actual.
	 * Normalmente siendo que no tienen sentido fuera de la misma clase donde fueron
	 * creados son clases privadas, o clases internas.
	 */
	private class PilaIterator implements Iterator<T> {

		int iteratorIndex = size - 1;
		
		@Override
		public boolean hasNext() {
			return iteratorIndex >= 0;
		}

		@Override
		public T next() {
			if(hasNext()) {
				return stackArray.get(iteratorIndex--);
			} else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}
}
