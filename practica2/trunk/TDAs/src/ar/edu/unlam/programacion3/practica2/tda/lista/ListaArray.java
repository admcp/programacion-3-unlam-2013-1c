package ar.edu.unlam.programacion3.practica2.tda.lista;

public class ListaArray<T> implements Lista<T> {

	private int size;
	private Object[] arrayElementos;
	
	public ListaArray(int capacidadInicial) {
		// TODO ListaArray: constructor con capacidad inicial
	}
	
	public ListaArray() {
		// TODO ListaArray: constructor
	}
	
	//----------------------------------------------------
	// Comportamiento derivado del contrato con <<Lista>>
	//----------------------------------------------------
	
	@Override
	public int size() {
		// TODO ListaArray: int size()
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO ListaArray: boolean isEmpty()
		return false;
	}

	@Override
	public boolean add(T elemento) {
		// TODO ListaArray: boolean add(T elemento)
		return false;
	}

	@Override
	public boolean remove(Object elemento) {
		// TODO ListaArray: boolean remove(Object elemento)
		return false;
	}

	@Override
	public void reverse() {
		// TODO ListaArray: void reverse()
		
	}

	@Override
	public void clear() {
		// TODO ListaArray: void clear()
		
	}

	@Override
	public T get(int index) {
		// TODO ListaArray: T get(int index)
		return null;
	}

	@Override
	public T set(int index, T elemento) {
		// TODO ListaArray: T set(int index, T elemento)
		return null;
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
	public int indexOf(Object elemento) {
		// TODO ListaArray: int indexOf(Object elemento)
		return 0;
	}

	@Override
	public int lastIndexOf(Object elemento) {
		// TODO ListaArray: int lastIndexOf(Object elemento)
		return 0;
	}
	
}
