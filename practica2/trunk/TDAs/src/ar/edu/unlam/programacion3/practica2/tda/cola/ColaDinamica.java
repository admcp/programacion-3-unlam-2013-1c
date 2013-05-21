package ar.edu.unlam.programacion3.practica2.tda.cola;

import java.nio.BufferUnderflowException;

public class ColaDinamica<T> implements Cola<T> {
	
	// MIEMBROS PRIVADOS
	private Nodo <T> primero;
	private Nodo <T> ultimo; 
	
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
		Nodo<T> nodo = new Nodo<T>(null, elemento, null);
		
		if (primero == null) {
			primero = nodo;
		} else {
			ultimo.siguiente = nodo;
		}
		ultimo = nodo;
	}

	@Override
	public T poll() {
		if (primero == null) {
			throw new BufferUnderflowException();
		}
		
		T elemento = primero.elemento;
		
		if (primero == ultimo) {
			primero = null;
			ultimo = null;
		} else {
			primero = primero.siguiente;
		}
		
		return elemento;
	}

	@Override
	public T peek() {
		if (primero == null) {
			throw new BufferUnderflowException();
		}
		
		return primero.elemento;
	}

	@Override
	public void clear() {
		while (primero != null) {
			if (primero == ultimo) {
				primero = null;
				ultimo = null;
			} else {
				primero = primero.siguiente;
			}			
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
