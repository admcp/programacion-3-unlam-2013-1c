package ar.edu.unlam.programacion3.practica2.tda.cola;

public class ColaDinamica<T> implements Cola<T> {

	// MIEMBROS PRIVADOS
	private Nodo<T> primero;
	private Nodo<T> ultimo;

	// CONSTRUCTORES
	public ColaDinamica() {
		primero = null;
		ultimo = null;
	}

	// METODOS
	@Override
	public boolean isEmpty() {
		return (primero == null);
	}

	@Override
	public void offer(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}
		
		Nodo<T> nodoNuevo = new Nodo<T>(null, elemento, null);

		if (primero == null) {
			primero = nodoNuevo;
		} else {
			nodoNuevo.anterior = ultimo;
			ultimo.siguiente = nodoNuevo;
		}
		ultimo = nodoNuevo;
	}

	@Override
	public T poll() {
		if (primero == null) {
			return null;
		}

		Nodo<T> nodoSiguiente = primero.siguiente;
		T elemento = primero.elemento;

		primero.elemento = null;
		primero.siguiente = null;
		primero.anterior = null;
		
		if (primero == ultimo) {
			primero = null;
			ultimo = null;
		} else {
			primero = null;
			primero = nodoSiguiente;
		}

		return elemento;
	}

	@Override
	public T peek() {
		if (primero == null) {
			return null;
		}

		return primero.elemento;
	}

	@Override
	public void clear() {
		while (primero != null) {
			Nodo<T> nodoSiguiente = primero.siguiente;
			primero.elemento = null;
			primero.siguiente = null;
			primero.anterior = null;
			primero = nodoSiguiente;
		}
		primero = null;
		ultimo = null;
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
