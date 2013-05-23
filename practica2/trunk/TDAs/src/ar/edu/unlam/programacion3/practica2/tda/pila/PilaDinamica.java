package ar.edu.unlam.programacion3.practica2.tda.pila;

public class PilaDinamica<T> implements Pila<T> {

	// MIEMBROS PRIVADOS
	private Nodo<T> tope;

	// CONSTRUCTORES
	public PilaDinamica() {
		tope = null;
	}

	//--------------------------------------------------
	// Comportamiento derivado del contrato con <<Pila>>
	//--------------------------------------------------
	@Override
	public boolean isEmpty() {
		return (tope == null);
	}

	@Override
	public void push(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}

		Nodo<T> nodoNuevo = new Nodo<T>(null, elemento, null);

		if (tope != null) {
			nodoNuevo.anterior = tope;
			tope.siguiente = nodoNuevo;
		}

		tope = nodoNuevo;
	}

	@Override
	public T pop() {
		if (tope == null) {
			return null;
		}

		Nodo<T> nodoAnterior = tope.anterior;
		T elemento = tope.elemento;
		
		tope.elemento = null;
		tope.anterior = null;
		tope.siguiente = null;
		
		tope = nodoAnterior;

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
			Nodo<T> nodoAnterior = tope.anterior;
			tope.elemento = null;
			tope.anterior = null;
			tope.siguiente = null;
			tope = nodoAnterior;
		}
		tope = null;
	}

	// ------------------------------
	// Clase interna para los nodos
	// ------------------------------

	@SuppressWarnings("unused")
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
