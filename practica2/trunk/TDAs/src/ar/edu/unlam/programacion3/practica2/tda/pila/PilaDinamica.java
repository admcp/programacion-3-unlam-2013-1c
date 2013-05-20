package ar.edu.unlam.programacion3.practica2.tda.pila;

public class PilaDinamica<T> implements Pila<T> {

	@Override
	public boolean isEmpty() {
		// TODO PilaDinamica: boolean isEmpty()
		return false;
	}

	@Override
	public void push(T elemento) {
		// TODO PilaDinamica: void push(T elemento)
		
	}

	@Override
	public T pop() {
		// TODO PilaDinamica: T pop()
		return null;
	}

	@Override
	public T peek() {
		// TODO PilaDinamica: T peek()
		return null;
	}

	@Override
	public void clear() {
		// TODO PilaDinamica: void clear()
		
	}
	
	//------------------------------
	// Clase interna para los nodos
	//------------------------------
	
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
