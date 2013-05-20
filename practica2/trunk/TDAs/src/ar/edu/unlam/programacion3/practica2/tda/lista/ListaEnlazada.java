package ar.edu.unlam.programacion3.practica2.tda.lista;

public class ListaEnlazada<T> implements Lista<T> {

	private int size = 0;
	private Nodo<T> primerNodo;
	private Nodo<T> ultimoNodo;
	
	public ListaEnlazada() {
		// TODO ListaEnlazada: constructor
	}

	//-----------------------------------------
	// Comportamiento propio de Lista Enlazada
	//-----------------------------------------
	
	/**
	 * Obtener el primer nodo.
	 * @return primer nodo de la lista (si la lista está vacía: excepción).
	 */
	public T getFirst() {
		// TODO ListaEnlazada: T getFirst()
		return null;
	}
	
	/**
	 * Obtener el último nodo.
	 * @return último nodo de la lista (si la lista está vacía: excepción).
	 */
	public T getLast() {
		// TODO ListaEnlazada: T getLast()
		return null;
	}
	
	/**
	 * Quitar el primer nodo.
	 * @return nodo quitado de la lista (si la lista está vacía: excepción).
	 */
	public T removeFirst() {
		// TODO ListaEnlazada: T removeFirst()
		return null;
	}
	
	/**
	 * Obtener el último nodo.
	 * @return nodo quitado de la lista (si la lista está vacía: excepción).
	 */
	public T removeLast() {
		// TODO ListaEnlazada: T removeLast()
		return null;
	}
	
    /**
     * Insertar al principio de la lista.
     *
     * @param elemento es el elemento a insertar.
     */
	public void addFirst(T elemento) {
		// TODO ListaEnlazada: void addFirst(T elemento)
		
	}
	
    /**
     * Insertar al final de la lista. Es equivalente a {@link #add}.
     *
     * @param elemento es el elemento a insertar.
     */
	public void addLast(T elemento) {
		// TODO ListaEnlazada: addLast(T elemento)
		
	}
	
	//----------------------------------------------------
	// Comportamiento derivado del contrato con <<Lista>>
	//----------------------------------------------------
	
	@Override
	public int size() {
		// TODO ListaEnlazada: int size()
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO ListaEnlazada: boolean isEmpty()
		return false;
	}

	@Override
	public boolean add(T elemento) {
		// TODO ListaEnlazada: boolean add(T elemento)
		return false;
	}

	@Override
	public boolean remove(Object elemento) {
		// TODO ListaEnlazada: boolean remove(Object elemento)
		return false;
	}

	@Override
	public void reverse() {
		// TODO ListaEnlazada: void reverse()
		
	}

	@Override
	public void clear() {
		// TODO ListaEnlazada: void clear()
		
	}

	@Override
	public T get(int index) {
		// TODO ListaEnlazada: T get(int index)
		return null;
	}

	@Override
	public T set(int index, T elemento) {
		// TODO ListaEnlazada: T set(int index, T elemento)
		return null;
	}

	@Override
	public void add(int index, T elemento) {
		// TODO ListaEnlazada: void add(int index, T elemento)
		
	}

	@Override
	public T remove(int index) {
		// TODO ListaEnlazada: T remove(int index)
		return null;
	}

	@Override
	public int indexOf(Object elemento) {
		// TODO ListaEnlazada: int indexOf(Object elemento)
		return 0;
	}

	@Override
	public int lastIndexOf(Object elemento) {
		// TODO ListaEnlazada: int lastIndexOf(Object elemento)
		return 0;
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
