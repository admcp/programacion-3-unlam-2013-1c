package ar.edu.unlam.programacion3.practica3.jerarquias.cola;

import ar.edu.unlam.programacion3.practica2.tda.cola.Cola;
import ar.edu.unlam.programacion3.practica2.tda.lista.ListaEnlazada;

public class ColaHL<T> extends ListaEnlazada<T> implements Cola<T> {

	// ------------------------------------------------------------------------
	// Comportamiento esperado de Cola (parte por la interfaz, parte heredado)
	// ------------------------------------------------------------------------

	@Override
	public void offer(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}

		super.addLast(elemento);
	}

	@Override
	public T poll() {
		if (this.isEmpty()) {
			return null;
		}
		return super.removeFirst();
	}

	@Override
	public T peek() {
		if (this.isEmpty()) {
			return null;
		}
		return super.getFirst();
	}

	/**
	 * Observar el elemento esperando a ser quitado de la cola. Es el
	 * equivalente de {@link peek}.
	 * 
	 * @return primer elemento de la cola (si la cola está vacía: excepción).
	 */
	@Override
	public T getFirst() {
		return this.peek();
	}

	/**
	 * Quitar de la cola. Es el equivalente de {@link poll}.
	 * 
	 * @return elemento quitado de la cola (si la cola está vacía: excepción).
	 */
	@Override
	public T removeFirst() {
		return this.poll();
	}

	/**
	 * Agregar elemento a la cola. Es el equivalente de {@link #offer}.
	 * 
	 * @param elemento
	 *            es el elemento a ser encolado.
	 */
	@Override
	public void addLast(T elemento) {
		this.offer(elemento);
	}

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}

	/**
	 * Agregar elemento a la cola. Es el equivalente de {@link #offer}, pero
	 * devuelve true.
	 * 
	 * @param elemento
	 *            es el elemento a ser encolado.
	 * @return <tt>true</tt>.
	 */
	@Override
	public boolean add(T elemento) {
		this.offer(elemento);
		return true;
	}

	@Override
	public void clear() {
		super.clear();
	}

	// ------------------------------------------------------------
	// Sobreescribiendo comportamiento libertino de Lista Enlazada
	// ------------------------------------------------------------

	/**
	 * Este método no corresponde a Cola. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public T getLast() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Cola. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public T removeLast() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Cola. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void addFirst(T elemento) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Cola. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Cola. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public boolean remove(T elemento) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Cola. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void reverse() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Cola. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public T get(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Cola. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public T set(int index, T elemento) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Cola. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void add(int index, T elemento) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Cola. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public T remove(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Cola. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public int indexOf(T elemento) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Este método no corresponde a Cola. Siempre arroja excepción si se trata
	 * de usar.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public int lastIndexOf(T elemento) {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		testRegular();
		testRendimiento();
	}
	
	private static void testRegular() {
		Cola<Integer> cola = new ColaHL<Integer>();

		for (int i = 0; i < 10; i++) {
			System.out.println("Encolado: " + i);
			cola.offer(i);
		}

		System.out.println("Espiando cola (elemento listo para salir): " + cola.peek());

		for (int i = 0; i < 5; i++) {
			System.out.println("Desencolado: " + cola.poll());
		}

		for (int i = 10; i < 15; i++) {
			System.out.println("Encolado: " + i);
			cola.offer(i);
		}

		System.out.println("Cola vacia: " + (cola.isEmpty() == true ? "Si" : "No"));

		System.out.println("Vaciando cola.");
		cola.clear();

		System.out.println("Cola vacia: " + (cola.isEmpty() == true ? "Si" : "No"));
		
		cola = null;
	}
	
	private static void testRendimiento() {
		long tiempoInicial = 0;
		long tiempoFinal = 0;
		final int dimension = 1000000;
		
		Cola<Integer> colaMillon = new ColaHL<Integer>();
		
		System.out.println("Rendimiento de ColaHL con " + dimension + " de elementos.");
		
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
