package ar.edu.unlam.programacion3.practica2.tda.cola;

public interface Cola<T> {
	
	public boolean empty();
	public void offer(T elemento);
	public T poll();
	public T peek();
	public void clear();
	
}
