package ar.edu.unlam.programacion3.practica2.tda.pila;

import java.nio.*;

public class PilaEstatica<T> implements Pila<T> {

	// Miembros Privados.
	private int dimension;
	private int tope;
	private T pila [];
	
	@SuppressWarnings("unchecked")
   public PilaEstatica(int dimension) {
	   this.dimension = dimension;
	   pila = (T[]) new Object [dimension];	
	   tope = -1;
   }

	@Override
	public boolean isEmpty() {		
		return (tope == -1);
	}

	// TODO Hay que ver como manejamos los casos en los que queremos apilar yesta llena
	// o cuando queremos desapilar y esta vacia. Antes estos metodos devolvian un entero
	// indicando si se pudo apilar o desapilar con exito.

	@Override
	public void push(T elemento) {
		if (tope == dimension) {
			throw new BufferOverflowException();
		}

		pila[++tope] = elemento;
	}

	@Override
	public T pop() {
		if	(tope == -1) {
			throw new BufferUnderflowException();
		}
		T elemento = pila[tope]; 
		pila[tope--] = null;
		return elemento;
	}

	@Override
	public T peek() {
		return pila[tope];
	}

	@Override
	public void clear() {
		while (tope != -1) { 
			pila[tope--] = null;			
		}
	}

}
