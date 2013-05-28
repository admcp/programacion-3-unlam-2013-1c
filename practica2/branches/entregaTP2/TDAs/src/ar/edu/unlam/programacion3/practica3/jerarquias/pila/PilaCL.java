package ar.edu.unlam.programacion3.practica3.jerarquias.pila;

import ar.edu.unlam.programacion3.practica2.tda.lista.ListaEnlazada;
import ar.edu.unlam.programacion3.practica2.tda.pila.Pila;

public class PilaCL<T> implements Pila<T> {

	private ListaEnlazada<T> pila;

	// CONTRUCTORES
	public PilaCL() {
		pila = new ListaEnlazada<T>();
	}

	// METODOS
	@Override
	public boolean isEmpty() {
		return pila.isEmpty();
	}

	@Override
	public void push(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}
		pila.addFirst(elemento);
	}

	@Override
	public T pop() {
		if (pila.isEmpty()) {
			return null;
		}

		T elemento = pila.removeFirst();

		return elemento;
	}

	@Override
	public T peek() {
		if (pila.isEmpty()) {
			return null;
		}

		return pila.getFirst();
	}

	@Override
	public void clear() {
		pila.clear();
	}

	public static void main(String[] args) {
		Pila<String> pila = new PilaCL<String>();

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
