package ar.edu.unlam.programacion3.practica3.jerarquias.pila;

import ar.edu.unlam.programacion3.practica2.tda.lista.ListaEnlazada;
import ar.edu.unlam.programacion3.practica2.tda.pila.Pila;

public class PilaCL<T> implements Pila<T> {

	private ListaEnlazada<T> pila;

	// CONTRUCTORES
	public PilaCL() {
		pila = new ListaEnlazada<T>();
	}

	// METODOS
	@Override
	public boolean isEmpty() {
		return pila.isEmpty();
	}

	@Override
	public void push(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}
		pila.addFirst(elemento);
	}

	@Override
	public T pop() {
		if (pila.isEmpty()) {
			return null;
		}

		T elemento = pila.removeFirst();

		return elemento;
	}

	@Override
	public T peek() {
		if (pila.isEmpty()) {
			return null;
		}

		return pila.getFirst();
	}

	@Override
	public void clear() {
		pila.clear();
	}
	
}
