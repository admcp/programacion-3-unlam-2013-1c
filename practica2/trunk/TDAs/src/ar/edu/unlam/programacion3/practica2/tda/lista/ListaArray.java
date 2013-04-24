package ar.edu.unlam.programacion3.practica2.tda.lista;

import java.util.Arrays;
import java.util.Collections;

public class ListaArray<T> implements Lista<T> {

	private int size;
	private Object[] arrayElementos;
	
	public ListaArray(int capacidadInicial) {
		if(capacidadInicial < 0) {
			throw new IllegalArgumentException("El tamaño inicial no puede ser negativo");
		}
		this.arrayElementos = new Object[capacidadInicial];
	}
	
	public ListaArray() {
		this(10);
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
		asegurarCapacidadInterna(size + 1);
		arrayElementos[size++] = elemento;
		return true;
	}

	@Override
	public boolean remove(Object elemento) {
		if (elemento == null) {
            for (int index = 0; index < size; index++) {
                if (arrayElementos[index] == null) {
                    removeRapido(index);
                    return true;
                }
            }
        } else {
            for (int index = 0; index < size; index++) {
                if (elemento.equals(arrayElementos[index])) {
                    removeRapido(index);
                    return true;
                }
            }
        }
		return false;
	}

	@Override
	public void reverse() {
		Collections.reverse(Arrays.asList(arrayElementos));
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			arrayElementos[i] = null;
		}
		
        size = 0;
	}

	@Override
	public T get(int index) {
		chequearRango(index);

        return arrayElementos(index);
	}

	@Override
	public T set(int index, T elemento) {
        chequearRango(index);

        T valorViejo = arrayElementos(index);
        arrayElementos[index] = elemento;
        
        return valorViejo;
	}

	@Override
	public void add(int index, T elemento) {
		chequearRangoParaAdd(index);

        asegurarCapacidadInterna(size + 1);
        System.arraycopy(arrayElementos, index, arrayElementos, index + 1, size - index);
        arrayElementos[index] = elemento;
        size++;

	}

	@Override
	public T remove(int index) {
		chequearRango(index);
		T valorEliminado = arrayElementos(index);
        removeRapido(index);
		return valorEliminado;
	}

	@Override
	public int indexOf(Object elemento) {
        if (elemento == null) {
            for (int i = 0; i < size; i++) {
                if (arrayElementos[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (elemento.equals(arrayElementos[i])) {
                    return i;
                }
            }
        }
        return -1;
	}

	@Override
	public int lastIndexOf(Object elemento) {
        if (elemento == null) {
            for (int i = size-1; i >= 0; i--) {
                if (arrayElementos[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size-1; i >= 0; i--) {
                if (elemento.equals(arrayElementos[i])) {
                    return i;
                }
            }
        }
        return -1;
	}
	
	//------------------------
	// Métodos de uso interno
	//------------------------
	
    @SuppressWarnings("unchecked")
	private T arrayElementos(int index) {
        return (T) arrayElementos[index];
    }
	
	private void asegurarCapacidadInterna(int capacidadMinima) {
        if (capacidadMinima - arrayElementos.length > 0) {
            crecerArray(capacidadMinima);
        }
    }
	
	private void crecerArray(int capacidadMinima) {
        int capacidadAnterior = arrayElementos.length;
        int nuevaCapacidad = capacidadAnterior + (capacidadAnterior / 2);
        
        if (nuevaCapacidad - capacidadMinima < 0) {
            nuevaCapacidad = capacidadMinima;
        }
        
        if (nuevaCapacidad - Integer.MAX_VALUE - 8 > 0) {
        	nuevaCapacidad = granCapacidad(capacidadMinima);
        }
            
        arrayElementos = Arrays.copyOf(arrayElementos, nuevaCapacidad);
    }
	
    private static int granCapacidad(int capacidadMinima) {
        if (capacidadMinima < 0) { // overflow
            throw new OutOfMemoryError();
        }
        return (capacidadMinima > Integer.MAX_VALUE - 8) ? Integer.MAX_VALUE : Integer.MAX_VALUE - 8;
    }
    
    private void removeRapido(int index) {
        int numeroDeElementos = size - index - 1;
        
        if (numeroDeElementos > 0) {
            System.arraycopy(arrayElementos, index + 1, arrayElementos, index, numeroDeElementos);
        }
        
        arrayElementos[--size] = null;
    }

    private void chequearRango(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Idx: " + index + " - Tam: " + size);
        }
    }

    private void chequearRangoParaAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Idx: " + index + " - Tam: " + size);
        }
    }
    
}
