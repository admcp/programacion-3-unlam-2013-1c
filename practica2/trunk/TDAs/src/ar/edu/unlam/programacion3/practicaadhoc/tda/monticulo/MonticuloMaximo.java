package ar.edu.unlam.programacion3.practicaadhoc.tda.monticulo;

public class MonticuloMaximo<T extends Comparable<T>> implements Monticulo<T> {

	private T[] monticulo;
	private int size = 0;

	public MonticuloMaximo() {
		this(10);
	}

	@SuppressWarnings("unchecked")
	public MonticuloMaximo(int initialCapacity) {
		monticulo = (T[]) new Comparable[initialCapacity + 1];
	}

	@Override
   public boolean isEmpty() {
	   return size == 0;
   }

	@Override
	public void add(T item) {	
		if (item == null) { // Failfast en nulos
			throw new NullPointerException();
		}
		if (monticulo.length == size + 1) { // Redimiensionar si nos quedamos cortos
			resize(monticulo.length * 2);
		}
		monticulo[++size] = item; // Guardar el elemento en la posicion siguiente al ulitmo
		ordenarAdd(size); // Reorganizar "arbol" para que respete monticulo
	}

	private void ordenarAdd(int indiceActual) {
		while(indiceActual > 1 && // Mientras no sea la "raiz" y...
				esMenor(indicePadre(indiceActual), indiceActual)) { // ...padre < actual
			intercambiar(indicePadre(indiceActual), indiceActual); // intercambiarlos
			indiceActual = indicePadre(indiceActual); // continuar con actual = padre
		}
	}

	@Override
	public T remove() {
		if (size == 0) { // Failfast si ya no hay elementos
			throw new IllegalStateException();
		}
		
		T item = monticulo[1]; // Guardar el elemento "raiz"
		intercambiar(1, size--); // Intercambiar por el ultimo "nodo hoja" y disminuimos el tamaño
		monticulo[size + 1] = null; // Ayudar al GC
		ordenarRemove(1); // Reorganizar "arbol" para que respete monticulo
		
		if(size > 0 && size == monticulo.length / 4) { // Redimiensionar si estamos usando 1/4 de la capacidad
			resize(monticulo.length / 2);
		}
		
		return item; // Devolver el elemento que estaba en la "raiz"
	}
	
	private void ordenarRemove(int indiceActual) {
		while(indiceHijoIzquierdo(indiceActual) <= size) { // Mientras el hijo izquierdo no sea el último
			int indice = indiceHijoIzquierdo(indiceActual);  // Guardar indice del hijo izquierdo
			if(indice < size && // Si el indice izquierdo no es el último y ... 
					esMenor(indice, indiceHijoDerecho(indiceActual))) { // ... izquierdo < derecho 
				indice = indiceHijoDerecho(indiceActual); // Probar con el derecho.
			}
			if(esMayor(indiceActual, indice) || esIgual(indiceActual, indice)) { // Si el indice actual es mayor que el mayor de los hijos
				break; // Ya esta organizado el "arbol"
			}
			intercambiar(indiceActual, indice); // Intercambiar actual por el major de los hijos
			indiceActual = indice; // Empezar nuevamente desde dicha posicion
		}
	}
	

	@Override
	public T peek() {
		if (size == 0) { // Failfast si ya no hay elementos
			throw new IllegalStateException();
		}
		return monticulo[1];
	}

	private boolean esMenor(int indice1, int indice2) {
		return monticulo[indice1].compareTo(monticulo[indice2]) < 0;
	}
	
	private boolean esMayor(int indice1, int indice2) {
		return monticulo[indice1].compareTo(monticulo[indice2]) > 0;
	}

	private boolean esIgual(int indice1, int indice2) {
		return monticulo[indice1].compareTo(monticulo[indice2]) == 0;
	}
	
	private int indiceHijoIzquierdo(int indicePadre) {
		return indicePadre * 2;
	}

	private int indiceHijoDerecho(int indicePadre) {
		return (indicePadre * 2) + 1;
	}

	private int indicePadre(int indiceHijo) {
		return (int) Math.floor(indiceHijo / 2);
	}

	private void intercambiar(int indice1, int indice2) {
		T aux = monticulo[indice1];
		monticulo[indice1] = monticulo[indice2];
		monticulo[indice2] = aux;
	}

	@SuppressWarnings("unchecked")
	private void resize(int newSize) {
		T[] newArray = (T[]) new Comparable[newSize];
		for (int i = 0; i < size + 1; i++) {
			newArray[i] = monticulo[i];
		}
		monticulo = newArray;
	}

	@Override
	public String toString() {
		StringBuilder arrayPrint = new StringBuilder();
		for(int i = 1; i < size + 1; i++) {
			arrayPrint.append(monticulo[i] + " ");
		}
		return arrayPrint.toString();
	}

	public static void main(String[] args) {
		Monticulo<Integer> monticuloMax = new MonticuloMaximo<Integer>();
		monticuloMax.add(20);
		monticuloMax.add(7);
		monticuloMax.add(15);
		monticuloMax.add(6);
		monticuloMax.add(2);
		monticuloMax.add(8);
		monticuloMax.add(9);
		monticuloMax.add(5);
		
		while (!monticuloMax.isEmpty()) {
			System.out.println(monticuloMax);
			monticuloMax.remove();
		}
	}
}
