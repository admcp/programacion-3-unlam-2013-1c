package ar.edu.unlam.programacion3.practica2.tda.pila;

import ar.edu.unlam.programacion3.practica2.tda.lista.ListaEnlazada;

public class PilaHL<T> extends ListaEnlazada<T> implements Pila<T>{

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}
	
	@Override
   public void push(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}
		
		addFirst(elemento);
   }

	@Override
   public T pop() {
	   if (super.isEmpty()) {
	   	return null;
	   }
	   
	   T elemento = removeFirst();
	   
		return elemento;
   }

	@Override
   public T peek() {
		if (super.isEmpty()) {
	   	return null;
	   }
		
		return getFirst();
   }

	@Override
	public void clear(){
		super.clear();
	}
}
