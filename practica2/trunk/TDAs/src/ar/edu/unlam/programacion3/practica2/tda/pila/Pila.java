package ar.edu.unlam.programacion3.practica2.tda.pila;

public interface Pila<T> {
	
	public boolean empty();
	public void push(T elemento);
	public T pop();
	public T peek();
	public void clear();
	
}
