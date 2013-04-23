package ar.edu.unlam.programacion3.practica2.tda.lista;

import java.util.NoSuchElementException;

public class ListaEnlazada<T> implements Lista<T> {

	private int size = 0;
	private Nodo<T> primerNodo;
	private Nodo<T> ultimoNodo;
	
	public ListaEnlazada() {
	}

	//-----------------------------------------
	// Comportamiento propio de Lista Enlazada
	//-----------------------------------------
	
	/**
	 * Obtener el primer nodo.
	 * @return primer nodo de la lista (si la lista está vacía: excepción).
	 */
	public T getFirst() {
		Nodo<T> nodo = this.primerNodo;
		if(nodo == null) {
			throw new NoSuchElementException();
		}
		return nodo.elemento;
	}
	
	/**
	 * Obtener el último nodo.
	 * @return último nodo de la lista (si la lista está vacía: excepción).
	 */
	public T getLast() {
		Nodo<T> nodo = this.ultimoNodo;
		if(nodo == null) {
			throw new NoSuchElementException();
		}
		return nodo.elemento;
	}
	
	/**
	 * Quitar el primer nodo.
	 * @return nodo quitado de la lista (si la lista está vacía: excepción).
	 */
	public T removeFirst() {
		Nodo<T> aux = primerNodo;
        if (aux == null)
            throw new NoSuchElementException();
		return unlinkFirst(aux);
	}
	
	/**
	 * Obtener el último nodo.
	 * @return nodo quitado de la lista (si la lista está vacía: excepción).
	 */
	public T removeLast() {
		Nodo<T> aux = ultimoNodo;
        if (aux == null)
            throw new NoSuchElementException();
		return unlinkLast(aux);
	}
	
    /**
     * Insertar al principio de la lista.
     *
     * @param elemento es el elemento a insertar.
     */
	public void addFirst(T elemento) {
		linkFirst(elemento);
	}
	
    /**
     * Insertar al final de la lista. Es equivalente a {@link #add}.
     *
     * @param elemento es el elemento a insertar.
     */
	public void addLast(T elemento) {
		linkLast(elemento);
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
		return size() == 0;
	}

	@Override
	public boolean add(T elemento) {
		linkLast(elemento);
		return true;
	}

	@Override
	public boolean remove(Object elemento) {
		if(elemento == null) {
			for(Nodo<T> nodo = primerNodo; nodo != null; nodo = nodo.siguiente) {
				if(nodo.elemento == null) {
					unlink(nodo);
					return true;
				}
			}
		} else {
			for(Nodo<T> nodo = primerNodo; nodo != null; nodo = nodo.siguiente) {
				if(elemento.equals(nodo.elemento)) {
					unlink(nodo);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void reverse() {
		Nodo<T> aux = primerNodo;
		primerNodo = ultimoNodo;
		ultimoNodo = aux;
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
		checkElementIndex(index);
		return node(index).elemento;
	}

	@Override
	public T set(int index, T elemento) {
		checkElementIndex(index);
		Nodo<T> aux = node(index);
		T contenidoViejo = aux.elemento;
		aux.elemento = elemento;
		return contenidoViejo;
	}

	@Override
	public void add(int index, T elemento) {
		checkPositionIndex(index);
		if(index == size) {
			linkLast(elemento);
		} else {
			linkBefore(elemento, node(index));
		}
	}

	@Override
	public T remove(int index) {
		checkElementIndex(index);
		return unlink(node(index));
	}

	@Override
	public int indexOf(Object elemento) {
		int index = 0;
		if(elemento == null) {
			for(Nodo<T> aux = primerNodo; aux != null; aux = aux.siguiente) {
				if(aux.elemento == null) {
					return index;
				}
				index++;
			}
		} else {
			for(Nodo<T> aux = primerNodo; aux != null; aux = aux.siguiente) {
				if(elemento.equals(aux.elemento)) {
					return index;
				}
				index++;
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object elemento) {
		int index = size;
		if(elemento == null) {
			for(Nodo<T> aux = ultimoNodo; aux != null; aux = aux.anterior) {
				index--;
				if(aux.elemento == null) {
					return index;
				}
			}
		} else {
			for(Nodo<T> aux = ultimoNodo; aux != null; aux = aux.anterior) {
				index--;
				if(elemento.equals(aux.elemento)) {
					return index;
				}
			}
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
	
	//------------------------
	// Métodos de uso interno
	//------------------------
	
	private void linkFirst(T elemento) {
		Nodo<T> aux = primerNodo;
		Nodo<T> nodoNuevo = new Nodo<T>(null, elemento, aux);
		primerNodo = nodoNuevo;
		if(aux == null) {
			ultimoNodo = nodoNuevo;
		} else {
			aux.anterior = nodoNuevo;
		}
		size++;
	}
	
	private void linkLast(T elemento) {
		Nodo<T> aux = ultimoNodo;
		Nodo<T> nodoNuevo = new Nodo<T>(aux, elemento, null);
		ultimoNodo = nodoNuevo;
		if(aux == null) {
			primerNodo = nodoNuevo;
		} else {
			aux.siguiente = nodoNuevo;
		}
		size++;
		
	}
	
	private T unlinkFirst(Nodo<T> nodo) {
		T elemento = nodo.elemento;
		Nodo<T> nodoSiguiente = nodo.siguiente;
		nodo.elemento = null;
		nodo.siguiente = null;
		primerNodo = nodoSiguiente;
		if(nodoSiguiente == null) {
			ultimoNodo = null;
		} else {
			nodoSiguiente.anterior = null;
		}
		size--;
		return elemento;
	}
	
	private T unlinkLast(Nodo<T> nodo) {
        T elemento = nodo.elemento;
        Nodo<T> nodoAnterior = nodo.anterior;
        nodo.elemento = null;
        nodo.anterior = null;
        ultimoNodo = nodoAnterior;
        if (nodoAnterior == null) {
            primerNodo = null;
        } else {
            nodoAnterior.siguiente = null;
        }
        size--;
        return elemento;
	}
	
	private T unlink(Nodo<T> nodo) {
        T elemento = nodo.elemento;
        Nodo<T> nodoSiguiente = nodo.siguiente;
        Nodo<T> nodoAnterior = nodo.anterior;

        if (nodoAnterior == null) {
            primerNodo = nodoSiguiente;
        } else {
            nodoAnterior.siguiente = nodoSiguiente;
            nodo.anterior = null;
        }

        if (nodoSiguiente == null) {
            ultimoNodo = nodoAnterior;
        } else {
            nodoSiguiente.anterior = nodoAnterior;
            nodo.siguiente = null;
        }

        nodo.elemento = null;
        size--;
        return elemento;
    }
	
	private void linkBefore(T elemento, Nodo<T> nodo) {
		Nodo<T> nodoAnterior = nodo.anterior;
		Nodo<T> nodoNuevo = new Nodo<T>(nodoAnterior, elemento, nodo);
		nodo.anterior = nodoNuevo;
		if(nodoAnterior == null) {
			primerNodo = nodoNuevo;
		} else {
			nodoAnterior.siguiente = nodoNuevo;
		}
		size++;
	}

    private Nodo<T> node(int index) {
    	if(index < (size / 2)) {
    		Nodo<T> aux = primerNodo;
    		for(int i = 0; i < index; i++) {
    			aux = aux.siguiente;
    		}
    		return aux;
    	} else {
    		Nodo<T> aux = ultimoNodo;
    		for(int i = size - 1; i > index; i--) {
    			aux = aux.anterior;
    		}
    		return aux;
    	}
    }
	
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }
	
    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException("Idx: " + index + " - Tam: " + size);
    }
    
    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }
	
    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException("Idx: " + index + " - Tam: " + size);
    }
  
}
