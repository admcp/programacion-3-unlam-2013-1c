package ar.edu.unlam.programacion3.practica2.tda.pila;

public class PilaDinamica<T> implements Pila<T> {

	// MIEMBROS PRIVADOS
	private Nodo<T> tope;

	// CONSTRUCTORES
	public PilaDinamica() {
		tope = null;
	}

	// METODOS
	@Override
	public boolean isEmpty() {
		return (tope == null);
	}

	@Override
	public void push(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}

		Nodo<T> nodo = new Nodo<T>(null, elemento, null);

		if (tope != null) {
			nodo.anterior = tope;
		}

		tope = nodo;
	}

	@Override
	public T pop() {
		if (tope == null) {
			return null;
		}

		T elemento = tope.elemento;
		tope = tope.anterior;

		return elemento;
	}

	@Override
	public T peek() {
		if (tope == null) {
			return null;
		}

		return tope.elemento;
	}

	@Override
	public void clear() {
		while (tope != null) {
			tope = null;
		}
	}

	// ------------------------------
	// Clase interna para los nodos
	// ------------------------------

	private static class Nodo<T> {

		Nodo<T> anterior;
		T elemento;
		Nodo<T> siguiente;

		Nodo(Nodo<T> anterior, T elemento, Nodo<T> siguiente) {
			this.anterior = anterior;
			this.elemento = elemento;
			this.siguiente = siguiente;
		}

	}

}
