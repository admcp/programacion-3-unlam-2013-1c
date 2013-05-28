package ar.edu.unlam.programacion3.practica2.tda.cola;

public interface Cola<T> {
	
    /**
     * Verifica si la cola está vacía o no.
     * @return <tt>true</tt> si la cola está vacía.
     */
	public boolean isEmpty();
	
    /**
     * Encolar un elemento en la cola.
     * @param elemento es el elemento a ser encolado.
     */
	public void offer(T elemento);
	
	/**
	 * Desencolar un elemento de la cola.
	 * @return elemento desencolado.
	 */
	public T poll();
	
	/**
	 * Ver elemento en la primer posición de la cola.
	 * @return elemento en la primer posición de la cola.
	 */
	public T peek();
	
    /**
     * Quitar todos los elementos de la cola.
     */
	public void clear();
	
}
