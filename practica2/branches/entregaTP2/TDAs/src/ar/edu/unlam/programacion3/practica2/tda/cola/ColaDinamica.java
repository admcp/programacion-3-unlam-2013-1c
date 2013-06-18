package ar.edu.unlam.programacion3.practica2.tda.cola;

public class ColaDinamica<T> implements Cola<T> {

	// MIEMBROS PRIVADOS
	private Nodo<T> primero;
	private Nodo<T> ultimo;

	// CONSTRUCTORES
	public ColaDinamica() {
		primero = null;
		ultimo = null;
	}

	// METODOS
	@Override
	public boolean isEmpty() {
		return (primero == null);
	}

	@Override
	public void offer(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}
		
		Nodo<T> nodoNuevo = new Nodo<T>(null, elemento, null);

		if (primero == null) {
			primero = nodoNuevo;
		} else {
			nodoNuevo.anterior = ultimo;
			ultimo.siguiente = nodoNuevo;
		}
		ultimo = nodoNuevo;
	}

	@Override
	public T poll() {
		if (primero == null) {
			return null;
		}

		Nodo<T> nodoSiguiente = primero.siguiente;
		T elemento = primero.elemento;

		primero.elemento = null;
		primero.siguiente = null;
		primero.anterior = null;
		
		if (primero == ultimo) {
			primero = null;
			ultimo = null;
		} else {
			primero = null;
			primero = nodoSiguiente;
		}

		return elemento;
	}

	@Override
	public T peek() {
		if (primero == null) {
			return null;
		}

		return primero.elemento;
	}

	@Override
	public void clear() {
		while (primero != null) {
			Nodo<T> nodoSiguiente = primero.siguiente;
			primero.elemento = null;
			primero.siguiente = null;
			primero.anterior = null;
			primero = nodoSiguiente;
		}
		primero = null;
		ultimo = null;
	}

	// ------------------------------
	// Clase interna para los nodos
	// ------------------------------

	@SuppressWarnings("unused")
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

	public static void main(String[] args) {
		testRegular();
		testRendimiento();
	}
	
	public static void testRegular() {
		ColaDinamica<String> cola = new ColaDinamica<String>();
		
		System.out.println("Encolando...");
		for(int i = 0; i < 10; i++) {
			System.out.println(i + " - offer");
			cola.offer(Integer.toString(i));
		}
		
		System.out.println();
		
		System.out.println(cola.peek() + " - Peek");
		
		System.out.println();
		
		System.out.println(cola.poll() + " - poll");
		
		System.out.println();
		
		System.out.println("Nuevo elemento - offer");
		
		cola.offer("Nuevo elemento");
		
		System.out.println(cola.peek() + " - Peek");
		
		System.out.println();
		
		System.out.println("Desencolando...");
		while (!cola.isEmpty()) {
			System.out.println(cola.poll() + " - poll");
		}

		System.out.println();
		
		System.out.println("Encolando...");
		for(int i = 0; i < 10; i++) {
			System.out.println(i + " - offer");
			cola.offer(Integer.toString(i));
		}
		
		System.out.println();
		
		System.out.println(cola.peek() + " - Peek");
		
		System.out.println("clear");
		cola.clear();
		
		System.out.println();
		
		System.out.println(cola.isEmpty() + " - isEmpty");
	
	}
	
	private static void testRendimiento() {
		long tiempoInicial = 0;
		long tiempoFinal = 0;
		final int dimension = 1000000;

		Cola<Integer> colaMillon = new ColaDinamica<Integer>();

		System.out.println("Rendimiento de ColaDinamica con " + dimension + " de elementos.");

		tiempoInicial = System.currentTimeMillis();
		for (int i = 0; i < dimension; i++) {
			colaMillon.offer(i);
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
	
}
