package ar.edu.unlam.programacion3.practica2.tda.lista;

import java.util.NoSuchElementException;

public class ListaEnlazada<T> implements Lista<T> {

	private int size = 0;
	private Nodo<T> primerNodo;
	private Nodo<T> ultimoNodo;
	
	public ListaEnlazada() {
		primerNodo = null;
		ultimoNodo = null;
	}

	//-----------------------------------------
	// Comportamiento propio de Lista Enlazada
	//-----------------------------------------
	
	/**
	 * Obtener el primer nodo.
	 * @return primer nodo de la lista (si la lista está vacía: excepción).
	 */
	public T getFirst(){
		Nodo<T> aux = this.primerNodo;
		if(aux == null){
			throw new NoSuchElementException();
		}
		return aux.elemento;
	}
	
	/**
	 * Obtener el último nodo.
	 * @return último nodo de la lista (si la lista está vacía: excepción).
	 */
	public T getLast() {
		Nodo<T> aux = this.ultimoNodo;
		if(aux == null){
			throw new NoSuchElementException();
		}
		return aux.elemento;
	}
	
	/**
	 * Quitar el primer nodo.
	 * @return nodo quitado de la lista (si la lista está vacía: excepción).
	 */
	public T removeFirst() {
		Nodo<T> aux = this.primerNodo;
		if (aux == null){
			throw new NoSuchElementException();
		}
		T elemento = aux.elemento;
		if(aux.siguiente == null){
			this.primerNodo = null;
			this.ultimoNodo = null;
		} else {
			this.primerNodo = aux.siguiente;
			this.primerNodo.anterior = null;
		}
		aux.elemento = null;
		aux.siguiente = null;
		size --;
		return elemento;
	}
	
	/**
	 * Obtener el último nodo.
	 * @return nodo quitado de la lista (si la lista está vacía: excepción).
	 * @throws Exception 
	 */
	public T removeLast() {
		Nodo<T> aux = this.ultimoNodo;
		if (aux == null){
			throw new NoSuchElementException();
		}
		T elemento = aux.elemento;
		if(aux.anterior == null){
			this.primerNodo = null;
			this.ultimoNodo = null;
		} else {
			this.ultimoNodo = aux.anterior;
			this.ultimoNodo.siguiente = null;
		}
		aux.elemento = null;
		aux.anterior = null;
		size --;
		return elemento;
	}
	
    /**
     * Insertar al principio de la lista.
     *
     * @param elemento es el elemento a insertar.
     */
	public void addFirst(T elemento) {
		Nodo<T> nuevo = new Nodo<T>(null, elemento, primerNodo);
		if(primerNodo == null){
			ultimoNodo = nuevo;
		} else {
			primerNodo.anterior = nuevo;
		}
		primerNodo = nuevo;
		size++;
	}
	
    /**
     * Insertar al final de la lista. Es equivalente a {@link #add}.
     *
     * @param elemento es el elemento a insertar.
     */
	public void addLast(T elemento) {
		Nodo<T> nuevo = new Nodo<T>(ultimoNodo, elemento, null);
		if(ultimoNodo == null){
			primerNodo = nuevo;
		} else {
			ultimoNodo.siguiente = nuevo;
		}
		ultimoNodo = nuevo;
		size++;
	}
	
	//----------------------------------------------------
	// Comportamiento derivado del contrato con <<Lista>>
	//----------------------------------------------------
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean add(T elemento) {
		addLast(elemento);
		return true;
	}

	@Override
	public boolean remove(T elemento) {
		for(Nodo<T> nodo = primerNodo; nodo != null; nodo = nodo.siguiente){
			if(elemento.equals(nodo.elemento)){
				if(nodo.anterior == null || nodo.siguiente == null){
					if(nodo.anterior == null){
						primerNodo = nodo.siguiente;
					} 
					if(nodo.siguiente == null){
						ultimoNodo = nodo.anterior;
					}
				} else {
					nodo.anterior.siguiente = nodo.siguiente;
					nodo.siguiente.anterior = nodo.anterior;
				}
				nodo.elemento = null;
				nodo.anterior = null;
				nodo.siguiente = null;
				size--;
				return true;
			}
		}
		return false;
	}

	@Override
	public void reverse() {
		Nodo<T> primero = primerNodo;
		Nodo<T> ultimo = ultimoNodo;
		Nodo<T> nodo = primerNodo;
		Nodo<T> aux;
		for(int i = 0; i < size; i++){
			aux = nodo.siguiente;
			nodo.siguiente = nodo.anterior;
			nodo.anterior = aux;
			nodo = nodo.anterior;
		}
		primerNodo = ultimo;
		ultimoNodo = primero;
	}

	@Override
	public void clear() {
		for(Nodo<T> nodo = primerNodo; nodo != null; ) {
			Nodo<T> nodoSiguiente = nodo.siguiente;
			nodo.elemento = null;
			nodo.siguiente = null;
			nodo.anterior = null;
			nodo = nodoSiguiente;
		}
		primerNodo = ultimoNodo = null;
		size = 0;
	}

	@Override
	public T get(int index) {
		chequearIndice(index);
		if(index == 0){
			return primerNodo.elemento;
		}
		if(index == size){
			return ultimoNodo.elemento;
		}
		Nodo<T> aux = primerNodo;
		for(int i = 0; i < index; i++){
			aux = aux.siguiente;
		}
		return aux.elemento;
	}

	@Override
	public T set(int index, T elemento) {
		chequearIndice(index);
		T elementoActual;
		if(index == 0){
			elementoActual = primerNodo.elemento;
			primerNodo.elemento = elemento;
		}
		if(index == size){
			elementoActual = ultimoNodo.elemento;
			ultimoNodo.elemento = elemento;
		}
		Nodo<T> aux = primerNodo;
		for(int i = 0; i < index; i++){
			aux = aux.siguiente;
		}
		elementoActual = aux.elemento;
		aux.elemento = elemento;
		return elementoActual;
	}

	@Override
	public void add(int index, T elemento) {
		chequearIndice(index);
		if(index == 0 || index == size){
			if(index == 0){
				addFirst(elemento);
			}
			if(index == size){
				addLast(elemento);
			}
		} else {
			Nodo<T> aux = primerNodo;
			for(int i = 0; i < index; i++){
				aux = aux.siguiente;
			}
			Nodo<T> nuevo = new Nodo<T>(aux.anterior, elemento, aux);
			aux.anterior.siguiente = nuevo;
			aux.anterior = nuevo;
			size++;
		}
	}

	@Override
	public T remove(int index) {
		chequearIndice(index);
		Nodo<T> aux = primerNodo;
		T elementoActual;
		if(index == 0 || index == size){
			if(index == 0){
				aux.siguiente.anterior = null;
			}
			if(index == size){
				aux.anterior.siguiente = null;
			}
		} else {
			for(int i = 0; i < index; i++){
				aux = aux.siguiente;
			}
			aux.anterior.siguiente = aux.siguiente;
			aux.siguiente.anterior = aux.anterior;
		}
		elementoActual = aux.elemento;
		aux.elemento = null;
		size--;
		return elementoActual;
	}

	@Override
	public int indexOf(T elemento) {
		int index = 0;
		for(Nodo<T> nodo = primerNodo; nodo != null; nodo = nodo.siguiente){
			if(elemento.equals(nodo.elemento)){
				return index;
			}
			index++;
		}
		return -1;
	}

	@Override
	public int lastIndexOf(T elemento) {
		int index = size;
		for(Nodo<T> nodo = ultimoNodo; nodo != null; nodo = nodo.anterior){
			if(elemento.equals(nodo.elemento)){
				return index;
			}
			index--;
		}
		return -1;
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
	
	private boolean esIndice(int index) {
        return index >= 0 && index <= size;
    }
	
	private void chequearIndice(int index) {
        if (!esIndice(index))
            throw new IndexOutOfBoundsException("Idx: " + index + " - Tam: " + size);
    }
  
	
	public static void main(String[] args) {
		ListaEnlazada<String> lista = new ListaEnlazada<String>();
		//cargo la lista con 5 nombres
		lista.addFirst("Jose");
		lista.add("Marcos");
		lista.add("Raul");
		lista.add("Zaul");
		lista.add(1, "Gimena");
		
		// imprimo en pantalla la lista cargada
		for(int i = 0; i < lista.size(); i++){
			System.out.println(lista.get(i));
		}
		
		// invierto la lista
		lista.reverse();
		System.out.println("Lista invertida: ");
		
		// imprimo en pantalla la lista cargada
		for(int i = 0; i < lista.size(); i++){
			System.out.println(lista.get(i));
		}
		
		//Obtengo el indice de gimena y lo remuevo
		lista.remove(lista.indexOf("Gimena"));
		lista.removeFirst();
		lista.removeLast();
		lista.remove("Marcos");
		
		// imprimo en pantalla la lista tras eliminar
		System.out.println("Lista tras eliminar Gimena, El primero, el Ultimo y a Marcos: ");
		for(int i = 0; i < lista.size(); i++){
			System.out.println(lista.get(i));
		}
		
		//Seteo a Raul con el nombre Javier
		System.out.println("Seteo Javier al elemento Raul");
		lista.set(0, "Javier");
		
		//Uso getter para traer el elemento de la lista e imprimirlo
		System.out.println("El elemento de la posicion 0 es:" + lista.get(0));
		
		//Vacio la lista
		lista.clear();
		
		//Muestro la lista
		System.out.println("Listra tras hacer un clear:");
		for(int i = 0; i < lista.size(); i++){
			System.out.println(lista.get(i));
		}
		
		
	}
	
}
