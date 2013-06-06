package ar.edu.unlam.programacion3.practicaadhoc.tda.tests.cola;

import ar.edu.unlam.programacion3.practica2.tda.cola.Cola;
import ar.edu.unlam.programacion3.practicaadhoc.tda.cola.ColaPrioridadPorMaximo;
import ar.edu.unlam.programacion3.practicaadhoc.tda.cola.ColaPrioridadPorMinimo;

public class TestColaPrioridad {
	
	public static void main(String[] args) {
		testColaPorMaximo();
		testColaPorMinimo();
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
	
	private static void testColaPorMinimo() {
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
			System.out.println("Cola vacía");
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
