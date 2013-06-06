package ar.edu.unlam.programacion3.practica3.jerarquias.cola;

import ar.edu.unlam.programacion3.practica2.tda.cola.Cola;
import ar.edu.unlam.programacion3.practica2.tda.lista.ListaEnlazada;

public class ColaCL<T> implements Cola<T> {

	private ListaEnlazada<T> cola;

	public ColaCL() {
		cola = new ListaEnlazada<T>();
	}

	@Override
	public boolean isEmpty() {
		return cola.isEmpty();
	}

	@Override
	public void offer(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}

		cola.add(elemento);
	}

	@Override
	public T poll() {
		if (cola.isEmpty()) {
			return null;
		}

		T elemento = cola.removeFirst();

		return elemento;
	}

	@Override
	public T peek() {
		if (cola.isEmpty()) {
			return null;
		}

		return cola.getFirst();
	}

	@Override
	public void clear() {
		cola.clear();
	}
	
}
