package ar.edu.unlam.programacion3.practica2.tda.lista;

public interface Lista<T> {

    //------------------------
	// Consultando a la Lista
	//------------------------
	
	/**
	 * Devuelve el número de elementos en la lista.
	 * @return número de elementos en la lista.
	 */
    public int size();
    
    /**
     * Verifica si la lista está vacía o no.
     * @return <tt>true</tt> si la lista está vacía.
     */
	public boolean isEmpty();
    
	//------------------------
    // Modificando a la Lista
	//------------------------
	
	/**
	 * Agregar elemento al final de la lista.
	 * @param elemento es el elemento a ser insertado.
	 * @return <tt>true</tt>.
	 */
    public boolean add(T elemento);
    
    /**
     * Quitar la primer ocurrencia del elemento de la lista.
     * @param elemento es el elemento a ser quitado (si existe).
     * @return <tt>true</tt> si el elemento existe en la lista.
     */
    public boolean remove(T elemento);
    
    //------------------------------------
    // Modificando masivamente a la Lista
    //------------------------------------
    
    /**
     * Invertir la lista.
     */
    public void reverse();
    
    /**
     * Quitar todos los elementos de la lista.
     */
    public void clear();
    
    //------------------------------
    // Acceso posicional a la Lista
    //------------------------------
    
    /**
     * Obtener el elemento ubicado en la posición dada.
     * @param index es la posición del elemento buscado.
     * @return elemento ubicado en la posición dada.
     */
    public T get(int index);
    
    /**
     * Reemplazar el elemento en la posición dada por el elemento ofrecido.
     * @param index es la posición del elemento en cuestión.
     * @param elemento es el elemento nuevo que servirá de reemplazo al ubicado en la posición dada.
     * @return elemento ubicado en dicha posición antes del reemplazo.
     */
    public T set(int index, T elemento);
    
	/**
	 * Inserta el elemento dado en la posición dada en la lista. Desplaza el elemento
	 * ubicado actualmente en dicha posición (si hay alguno) y los elementos subsiguientes
	 * hacia la derecha.
	 * @param index es la posición deseada para la inserción.
	 * @param elemento elemento a ser insertado.
	 */
    public void add(int index, T elemento);
    
	/**
	 * Elimina el elemento en la posición dada en la lista. Elimina el elemento
	 * ubicado actualmente en dicha posición (si hay alguno) y desplaza los elementos
	 * subsiguientes hacia la izquierda.
	 * @param index es la posición del elemento a eliminar.
	 * @return el elemento que estaba ubicado en dicha posición.
	 */
    public T remove(int index);
    
    //----------------------
    // Búsqueda en la Lista
    //----------------------
    
    /**
     * Devuelve el índice de la primera aparición del elemento dado, o -1 si no se
     * encuentra dicho elemento en la lista.
     * @param elemento es el elemento a buscar.
     * @return índice de dicho elemento o -1 si no existe.
     */
    public int indexOf(T elemento);
    
    /**
     * Devuelve el índice de la última aparición del elemento dado, o -1 si no se
     * encuentra dicho elemento en la lista.
     * @param elemento es el elemento a buscar.
     * @return índice de dicho elemento o -1 si no existe.
     */
    public int lastIndexOf(T elemento);
    
}
