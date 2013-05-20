package ar.edu.unlam.programacion3.practica2.tda.cola;

public class ColaDinamica<T> implements Cola<T> {

	@Override
	public boolean isEmpty() {
		// TODO ColaDinamica: boolean empty()
		return false;
	}

	@Override
	public void offer(T elemento) {
		// TODO ColaDinamica: void offer(T elemento)
		
	}

	@Override
	public T poll() {
		// TODO ColaDinamica: T poll()
		return null;
	}

	@Override
	public T peek() {
		// TODO ColaDinamica: T peek()
		return null;
	}

	@Override
	public void clear() {
		// TODO ColaDinamica: void clear()
		
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
