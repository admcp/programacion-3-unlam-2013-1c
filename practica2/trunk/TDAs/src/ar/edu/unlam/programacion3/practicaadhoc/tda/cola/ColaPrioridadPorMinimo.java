package ar.edu.unlam.programacion3.practicaadhoc.tda.cola;

import ar.edu.unlam.programacion3.practica2.tda.cola.Cola;
import ar.edu.unlam.programacion3.practicaadhoc.tda.monticulo.Monticulo;
import ar.edu.unlam.programacion3.practicaadhoc.tda.monticulo.MonticuloMinimo;

public class ColaPrioridadPorMinimo<T extends Comparable<T>> implements Cola<T> {

	private Monticulo<T> cola;
	private int size = 0;
	
	public ColaPrioridadPorMinimo() {
		this(10);
	}
	
	public ColaPrioridadPorMinimo(int initialCapacity) {
		cola = new MonticuloMinimo<T>(initialCapacity);
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
		size = 0;
	}
	
	public static void main(String[] args) {
		Cola<WeirdInteger> cola = new ColaPrioridadPorMinimo<WeirdInteger>();
		for(int i = 0; i < 100; i++) {
			// cola.offer(1, 100), cola.offer(2, 99): como usa montículo mínimo, 1 tiene menor prioridad que 2, etc.
			cola.offer(new WeirdInteger(i + 1, 100 - i));
		}
		for(int i = 0; i < 100; i++) {
			System.out.println(cola.poll());
		}
		System.out.println("Agregamos dos valores mas: 27 y 6");
		cola.offer(new WeirdInteger(27, 1));
		cola.offer(new WeirdInteger(6, 1));
		System.out.println("Vaciamos Cola de Prioridad");
		cola.clear();
		if(cola.isEmpty() == true){
			System.out.println("Cola vac�a");
		}
	}
	
	static class WeirdInteger implements Comparable<WeirdInteger> {
		Integer value;
		int priority;
		
		public WeirdInteger(Integer value, int priority) {
			this.value = value;
			this.priority = priority;
		}
		
		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public int getPriority() {
			return priority;
		}

		public void setPriority(int priority) {
			this.priority = priority;
		}
		
		@Override
		public String toString() {
			return value.toString();
		}

		@Override
		public int compareTo(WeirdInteger other) {
			return this.priority - other.priority;
		}

	}
	
}
