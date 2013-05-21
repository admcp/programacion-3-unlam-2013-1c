package ar.edu.unlam.programacion3.practica2.tda.pila;

import java.nio.BufferUnderflowException;

public class PilaDinamica<T> implements Pila<T> {
	
	// MIEMBROS PRIVADOS
	private Nodo<T> pila;
	
	// CONSTRUCTORES
	public PilaDinamica() {
		pila = null;
	}
	
	// METODOS
	@Override
	public boolean isEmpty() {
		return (pila == null);
	}

	@Override
	public void push(T elemento) {
		if (elemento == null) {
			throw new IllegalArgumentException();
		}
		
		Nodo<T> nodo = new Nodo<T>(null, elemento, null);
		
		if (pila != null) {
			nodo.anterior = pila;
		}
		
		pila = nodo;
	}

	@Override
	public T pop() {
		if (pila == null) {
			throw new BufferUnderflowException();
		}
		
		T elemento = pila.elemento;
		pila = pila.anterior;		
		
		return elemento;
	}

	@Override
	public T peek() {
		if	(pila == null) {
			throw new BufferUnderflowException();
		}
		
		return pila.elemento;
	}

	@Override
	public void clear() {
		while (pila != null) {
			pila = null;
		}
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
