package ar.edu.unlam.programacion3.practica2.tda.pila;

public interface Pila<T> {
	
    /**
     * Verifica si la pila está vacía o no.
     * @return <tt>true</tt> si la pila está vacía.
     */
	public boolean isEmpty();
	
    /**
     * Apilar un elemento en la pila.
     * @param elemento es el elemento a ser apilado.
     */
	public void push(T elemento);
	
	/**
	 * Desapilar un elemento de la pila.
	 * @return elemento desapilado.
	 */
	public T pop();
	
	/**
	 * Ver elemento en la posición superior de la pila.
	 * @return elemento en la posición superior de la pila.
	 */
	public T peek();
	
    /**
     * Quitar todos los elementos de la pila.
     */
	public void clear();
	
}
