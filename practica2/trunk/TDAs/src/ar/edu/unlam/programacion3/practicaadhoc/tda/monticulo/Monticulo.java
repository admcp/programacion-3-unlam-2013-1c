package ar.edu.unlam.programacion3.practicaadhoc.tda.monticulo;

public interface Monticulo<T extends Comparable<T>> {

	public void add(T item);
	public T remove();
	public T peek();
	
}
