package ar.edu.unlam.programacion3.practica2.tda.lista;

public class ListaArray<T> implements Lista<T> {

	private int size;	
	private int primero;
	private int ultimo;
	private T[] arrayElementos;
	
	@SuppressWarnings("unchecked")
	public ListaArray(int capacidadInicial) {
		if (capacidadInicial <= 0) {
			throw new IllegalArgumentException();
		}
		
		size = 0;
		primero = -1;
		ultimo = -1;
		arrayElementos = (T[]) new Object[capacidadInicial];
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
		return size == 0;
	}
	
	@Override
	public boolean add(T elemento) {
		if	(elemento == null) {
			throw new NullPointerException();
		}
		
		arrayElementos[size++] = elemento;
		return true;
	}

	@Override
	public boolean remove(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}

		for (int index = 0; index < size; index++) {
         if (elemento.equals(arrayElementos[index])) {
             borradoRapido(index);
             return true;
         }
		}
		return false;
	}

	private void borradoRapido(int index) {
      int cantidadMovidos = size - index - 1;      
      
      if (cantidadMovidos > 0) {
          System.arraycopy(arrayElementos, index+1, arrayElementos, index, cantidadMovidos);
      }
      
      arrayElementos[--size] = null;
   }

	@Override
	public void reverse() {
		// TODO ListaArray: void reverse()
      for (int i = 0; i < size; i++) {
         arrayElementos[i] = null;
      }
      
     size = 0;		
	}

	@Override
	public void clear() {
		// TODO ListaArray: void clear()
		for (int i = 0; i < size; i++) {
         arrayElementos[i] = null;
      }
      
     size = 0;		
	}

	@Override
	public T get(int index) {
      if (index < 0 || index >= size) {
         throw new IndexOutOfBoundsException();
      }
		
		return arrayElementos[index];
	}

	@Override
	public T set(int index, T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}
		
      if (index < 0 || index >= size) {
         throw new IndexOutOfBoundsException();
      }

		T AntiguoElemento = arrayElementos[index];
		arrayElementos[index] = elemento;
		return AntiguoElemento;
	}

	@Override
	public void add(int index, T elemento) {
		// TODO ListaArray: void add(int index, T elemento)
		
	}

	@Override
	public T remove(int index) {
		// TODO ListaArray: T remove(int index)
		return null;
	}

	@Override
	public int indexOf(T elemento) {
		// TODO ListaArray: int indexOf(Object elemento)
		return 0;
	}

	@Override
	public int lastIndexOf(T elemento) {
		// TODO ListaArray: int lastIndexOf(Object elemento)
		return 0;
	}
	
}
