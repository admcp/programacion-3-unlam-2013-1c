package ar.edu.unlam.programacion3.practica2.tda.lista;


public class ListaArray<T> implements Lista<T> {

	private int size;
	private T[] arrayElementos;

	@SuppressWarnings("unchecked")
	public ListaArray(int capacidadInicial) {
		if (capacidadInicial <= 0) {
			throw new IllegalArgumentException();
		}

		size = 0;
		arrayElementos = (T[]) new Object[capacidadInicial];
	}

	public ListaArray() {
		this(10);
	}

	// ----------------------------------------------------
	// Comportamiento derivado del contrato con <<Lista>>
	// ----------------------------------------------------

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean add(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}

		arrayElementos[size++] = elemento;
		return true;
	}

	@Override
	public boolean remove(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}
		
		if(size == 0) { // Lista vacía
			throw new IllegalStateException();
		}

		for (int index = 0; index < size; index++) {
			if (elemento.equals(arrayElementos[index])) {
				borradoRapido(index);
				return true;
			}
		}
		
		return false;
	}

	private void borradoRapido(int index) {
		int cantidadMovidos = size - index - 1;

		if (cantidadMovidos > 0) {
			System.arraycopy(arrayElementos, index + 1, arrayElementos, index, cantidadMovidos);
		}

		arrayElementos[--size] = null;
	}

	@Override
	public void reverse() {
		for(int i = 0, j = size - 1; i < j; i++, j--) {
			T aux = arrayElementos[i];
			arrayElementos[i] = arrayElementos[j];
			arrayElementos[j] = aux;
		}
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			arrayElementos[i] = null;
		}

		size = 0;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		if(size == 0) { // Lista vacía
			throw new IllegalStateException();
		}

		return arrayElementos[index];
	}

	@Override
	public T set(int index, T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		if(size == 0) { // Lista vacía
			throw new IllegalStateException();
		}

		T antiguoElemento = arrayElementos[index];
		arrayElementos[index] = elemento;
		
		return antiguoElemento;
	}

	@Override
	public void add(int index, T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		if(size + 1 == arrayElementos.length) { // Lista llena
			throw new IllegalStateException();
		}
		
		System.arraycopy(arrayElementos, index, arrayElementos, index + 1, size);
		arrayElementos[index] = elemento;
		size++;
	}

	@Override
	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		if(size == 0) { // Lista vacía
			throw new IllegalStateException();
		}
		
		T elemento = arrayElementos[index];
		
		for(int i = index; i < size - 1; i++) {
			arrayElementos[i] = arrayElementos[i + 1];
		}
		
		arrayElementos[size - 1] = null;
		
		size--;
		
		return elemento;
	}

	@Override
	public int indexOf(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}
		
		if(size == 0) { // Lista vacía
			throw new IllegalStateException();
		}
		
		for(int i = 0; i < size; i++) {
			if(arrayElementos[i].equals(elemento)) {
				return i;
			}
		}
		
		return -1;
	}

	@Override
	public int lastIndexOf(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}
		
		if(size == 0) { // Lista vacía
			throw new IllegalStateException();
		}
		
		for(int i = size; i > 0; i--) {
			if(arrayElementos[i].equals(elemento)) {
				return i;
			}
		}
		
		return -1;
	}
	
public static void main(String[] args) {
		
		ListaArray<String> lista = new ListaArray<String>();
		// cargo la lista con 5 nombres
		lista.add("Jose");
		lista.add("Marcos");
		lista.add("Raul");
		lista.add("Zaul");
		lista.add(1, "Gimena");

		// imprimo en pantalla la lista cargada
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}

		// invierto la lista
		lista.reverse();
		System.out.println("Lista invertida: ");

		// imprimo en pantalla la lista cargada
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}

		// Obtengo el indice de gimena y lo remuevo
		lista.remove(lista.indexOf("Gimena"));
		lista.remove("Marcos");

		// imprimo en pantalla la lista tras eliminar
		System.out.println("Lista tras eliminar Gimena y a Marcos: ");
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}

		// Seteo a Raul con el nombre Javier
		System.out.println("Seteo Javier al elemento Raul");
		lista.set(0, "Javier");

		// Uso getter para traer el elemento de la lista e imprimirlo
		System.out.println("El elemento de la posicion 0 es:" + lista.get(0));

		// Vacio la lista
		lista.clear();

		// Muestro la lista
		System.out.println("Listra tras hacer un clear:");
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}

	}

}
