package ar.edu.unlam.programacion3.practica2.tda.cola;

public class ColaEstatica<T> implements Cola<T> {

	// MIEMBROS PRIVADOS
	private int dimension;
	private T cola[];
	private int primero;
	private int ultimo;

	@SuppressWarnings("unchecked")
	public ColaEstatica(int dimension) {
		if (dimension <= 0) {
			throw new IllegalArgumentException();
		}

		this.dimension = dimension;
		cola = (T[]) new Object[dimension];
		primero = 0;
		ultimo = -1;
	}

	@Override
	public boolean isEmpty() {
		return (primero == 0) && (ultimo == -1);
	}

	@Override
	public void offer(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}

		if (primero == 0 && ultimo == (dimension - 1) || ultimo != -1 && (ultimo + 1) == primero) {
			throw new IllegalStateException();
		}

		ultimo = (ultimo + 1) % dimension;
		cola[ultimo] = elemento;
	}

	@Override
	public T poll() {
		if ((primero == 0) && (ultimo == -1)) {
			return null;
		}

		T elemento = cola[primero];
		cola[primero] = null;

		if (primero == ultimo) {
			primero = 0;
			ultimo = -1;
		} else {
			primero = (primero + 1) % dimension;
		}

		return elemento;
	}

	@Override
	public T peek() {
		if ((primero == 0) && (ultimo == -1)) {
			return null;
		}

		return cola[primero];
	}

	@Override
	public void clear() {
		while ((primero != 0) && (ultimo != -1)) {
			cola[primero] = null;

			if (primero == ultimo) {
				primero = 0;
				ultimo = -1;
			} else {
				primero = (primero + 1) % dimension;
			}
		}
	}

	public static void main(String[] args) {
		Cola<String> cola = new ColaEstatica<String>(10);

		System.out.println("Encolando...");
		for (int i = 0; i < 10; i++) {
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
		for (int i = 0; i < 10; i++) {
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
}