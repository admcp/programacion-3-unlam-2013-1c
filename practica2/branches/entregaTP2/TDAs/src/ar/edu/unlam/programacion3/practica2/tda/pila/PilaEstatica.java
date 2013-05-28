package ar.edu.unlam.programacion3.practica2.tda.pila;

public class PilaEstatica<T> implements Pila<T> {

	// Miembros Privados.
	private int dimension;
	private int tope;
	private T pila[];

	@SuppressWarnings("unchecked")
	public PilaEstatica(int dimension) {
		if (dimension <= 0) {
			throw new IllegalArgumentException();
		}
		this.dimension = dimension;
		pila = (T[]) new Object[dimension];
		tope = -1;
	}

	@Override
	public boolean isEmpty() {
		return (tope == -1);
	}

	// TODO Hay que ver como manejamos los casos en los que queremos apilar
	// y esta llena o cuando queremos desapilar y esta vacia. Antes estos
	// metodos devolvian un entero indicando si se pudo apilar o desapilar
	// con exito.

	@Override
	public void push(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}
		if (tope == dimension) {
			throw new IllegalStateException();
		}

		pila[++tope] = elemento;
	}

	@Override
	public T pop() {
		if (tope == -1) {
			return null;
		}
		T elemento = pila[tope];
		pila[tope--] = null;
		return elemento;
	}

	@Override
	public T peek() {
		if (tope == -1) {
			return null;
		}

		return pila[tope];
	}

	@Override
	public void clear() {
		while (tope != -1) {
			pila[tope--] = null;
		}
	}

	public static void main(String[] args) {
		Pila<String> pila = new PilaEstatica<String>(10);

		System.out.println("Apilando...");
		for (int i = 0; i < 10; i++) {
			System.out.println(i + " - Push");
			pila.push(Integer.toString(i));
		}

		System.out.println();

		System.out.println(pila.peek() + " - Peek");

		System.out.println();

		System.out.println(pila.pop() + " - Pop");

		System.out.println();

		System.out.println("Nuevo elemento - Push");

		pila.push("Nuevo elemento");

		System.out.println(pila.peek() + " - Peek");

		System.out.println();

		System.out.println("Desapilando...");
		while (!pila.isEmpty()) {
			System.out.println(pila.pop() + " - Pop");
		}

		System.out.println();

		System.out.println("Apilando...");
		for (int i = 0; i < 10; i++) {
			System.out.println(i + " - Push");
			pila.push(Integer.toString(i));
		}

		System.out.println();

		System.out.println(pila.peek() + " - Peek");

		System.out.println("clear");
		pila.clear();

		System.out.println();

		System.out.println(pila.isEmpty() + " - isEmpty");
	}
}
