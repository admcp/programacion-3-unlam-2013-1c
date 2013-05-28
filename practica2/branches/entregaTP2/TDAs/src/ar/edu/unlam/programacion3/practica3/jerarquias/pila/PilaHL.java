package ar.edu.unlam.programacion3.practica3.jerarquias.pila;

import ar.edu.unlam.programacion3.practica2.tda.lista.ListaEnlazada;
import ar.edu.unlam.programacion3.practica2.tda.pila.Pila;

public class PilaHL<T> extends ListaEnlazada<T> implements Pila<T> {

	// ------------------------------------------------------------------------
	// Comportamiento esperado de Pila (parte por la interfaz, parte heredado)
	// ------------------------------------------------------------------------

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}

	@Override
	public void push(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}

		super.addFirst(elemento);
	}

	@Override
	public T pop() {
		if (super.isEmpty()) {
			return null;
		}

		T elemento = super.removeFirst();

		return elemento;
	}

	@Override
	public T peek() {
		if (super.isEmpty()) {
			return null;
		}

		return super.getFirst();
	}

	@Override
	public void clear() {
		super.clear();
	}

	/**
	 * Observar el elemento esperando a ser quitado de la pila. Es el
	 * equivalente de {@link peek}.
	 * 
	 * @return primer elemento de la pila (si la pila está vacía: excepción).
	 */
	@Override
	public T getFirst() {
		return this.peek();
	}

	/**
	 * Quitar de la pila. Es el equivalente de {@link pop}.
	 * 
	 * @return elemento quitado de la pila (si la pila está vacía: excepción).
	 */
	@Override
	public T removeFirst() {
		return this.pop();
	}

	/**
	 * Agregar elemento a la pila. Es el equivalente de {@link #push}.
	 * 
	 * @param elemento
	 *            es el elemento a ser apilado.
	 */
	@Override
	public void addFirst(T elemento) {
		this.push(elemento);
	}

	// ------------------------------------------------------------
	// Sobreescribiendo comportamiento libertino de Lista Enlazada
	// ------------------------------------------------------------

	/**
	 * Este método no corresponde a Pila. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public T getLast() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Pila. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public T removeLast() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Pila. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void addLast(T elemento) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Pila. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Pila. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public boolean add(T elemento) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Pila. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public boolean remove(T elemento) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Pila. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void reverse() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Pila. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public T get(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Pila. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public T set(int index, T elemento) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Pila. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void add(int index, T elemento) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Pila. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public T remove(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Pila. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public int indexOf(T elemento) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Pila. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public int lastIndexOf(T elemento) {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		Pila<String> pila = new PilaHL<String>();

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
