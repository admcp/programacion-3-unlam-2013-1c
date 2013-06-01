package ar.edu.unlam.programacion3.practicaadhoc.tda.cola;

import ar.edu.unlam.programacion3.practica2.tda.cola.Cola;
import ar.edu.unlam.programacion3.practicaadhoc.tda.monticulo.Monticulo;
import ar.edu.unlam.programacion3.practicaadhoc.tda.monticulo.MonticuloMaximo;

public class ColaPrioridad<T extends Comparable<T>> implements Cola<T> {

	private Monticulo<T> cola;
	private int size = 0;
	
	public ColaPrioridad() {
		this(10);
	}
	
	public ColaPrioridad(int initialCapacity) {
		cola = new MonticuloMaximo<T>(initialCapacity);
	}
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void offer(T elemento) {
		cola.add(elemento);
		size++;
	}

	@Override
	public T poll() {
		if(size == 0) {
			throw new IllegalStateException();
		}
		T item = cola.remove();
		size--;
		return item;
	}

	@Override
	public T peek() {
		if(size == 0) {
			throw new IllegalStateException();
		}
		return cola.peek();
	}

	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			cola.remove();
		}
	}
	
	public static void main(String[] args) {
		// TODO: Hacer un test
	}

}
