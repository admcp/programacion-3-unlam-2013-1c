package ar.edu.unlam.programacion3.practicaadhoc.tda.cola;

import java.util.Random;

import ar.edu.unlam.programacion3.practica2.tda.cola.Cola;
import ar.edu.unlam.programacion3.practicaadhoc.tda.monticulo.Monticulo;
import ar.edu.unlam.programacion3.practicaadhoc.tda.monticulo.MonticuloMaximo;

public class ColaPrioridadPorMaximo<T extends Comparable<T>> implements Cola<T> {

	private Monticulo<T> cola;
	private int size = 0;
	
	public ColaPrioridadPorMaximo() {
		this(10);
	}
	
	public ColaPrioridadPorMaximo(int initialCapacity) {
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
		size = 0;
	}
	
	public static void main(String[] args) {
		testColaPorMaximo();
		testRendimiento();
	}
	
	private static void testColaPorMaximo() {
		Cola<WeirdInteger> cola = new ColaPrioridadPorMaximo<WeirdInteger>();
		for(int i = 0; i < 100; i++) {
			// cola.offer(1, 100), cola.offer(2, 99): como usa montículo máximo, 1 tiene mayor prioridad que 2, etc.
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
			System.out.println("Cola vacía");
		}
	}
	
	private static void testRendimiento() {
		long tiempoInicial = 0;
		long tiempoFinal = 0;
		final int dimension = 1000000;
		Random random = new Random(System.currentTimeMillis());
		WeirdInteger array[] = new WeirdInteger[dimension];
		
		Cola<WeirdInteger> colaMillon = new ColaPrioridadPorMaximo<WeirdInteger>();
		
		for(int i = 0; i < dimension; i++) {
			array[i] = new WeirdInteger(random.nextInt(1000000), random.nextInt(1000000));
		}
		
		System.out.println("Rendimiento de ColaPrioridadPorMaximo con " + dimension + " de elementos.");
		
		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			colaMillon.offer(array[i]);
		}
		tiempoFinal = System.currentTimeMillis();
		
		System.out.println("Tiempo Total (offer): " + (tiempoFinal - tiempoInicial) + " ms");
		
		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			colaMillon.poll();			
		}
		tiempoFinal = System.currentTimeMillis();
		
		System.out.println("Tiempo Total (poll): " + (tiempoFinal - tiempoInicial) + " ms");
			
		colaMillon = null;
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
