package ar.edu.unlam.programacion3.practicaadhoc.tda.monticulo;

public class MonticuloMinimo<T extends Comparable<T>> implements Monticulo<T> {
	
	private T[] monticulo;
	private int size = 0;
	
	public MonticuloMinimo() {
		this(10);
	}
	
	@SuppressWarnings("unchecked")
	public MonticuloMinimo(int initialCapacity) {
		if (initialCapacity <= 0) {
			throw new IllegalArgumentException();
		}
		monticulo = (T[]) new Comparable[initialCapacity + 1];
	}
	
	@Override
   public boolean isEmpty() {
	   return size == 0;
   }
		
	@Override
   public void add(T item) {
	   if	(item == null) {
	   	throw new NullPointerException();
	   }
	   
	   monticulo[++size] = item;
	   ordenarAdd(size);	   
   }

	@Override
   public T remove() {
	   if (size == 0) {
	   	throw new IllegalStateException();
	   }
	   
	   T item = monticulo[1];
	   intercambiar(1, size);
	   monticulo[size] = null;
	   size--;
	   
	   ordenarRemove(1);
	   
	   if (size > 0 && size == monticulo.length / 4) {
	   	resize(monticulo.length / 2);
	   }
	   
	   return item;
   }

	@Override
   public T peek() {
	   if (size == 0) {
	   	throw new IllegalStateException();
	   }
	   
	   return monticulo[1];
   }

	private void ordenarAdd(int indiceActual) {
		while (indiceActual > 1 && esMayor(indicePadre(indiceActual), indiceActual)) {
			intercambiar(indicePadre(indiceActual), indiceActual);
			indiceActual = indicePadre(indiceActual); 
		}
	}
	
	private void ordenarRemove(int indiceActual) {
		while (indiceHijoIzquierdo(indiceActual) <= size ) {
			int indice = indiceHijoIzquierdo(indiceActual);
			if (indice < size && esMayor(indice, indiceHijoDerecho(indiceActual))) {
				indice = indiceHijoDerecho(indiceActual);
			}
			
			if(esMenor(indiceActual, indice) || esIgual(indiceActual, indice)) {
				// ya esta organizado del monticulo.
				break;
			}
			
			intercambiar(indiceActual, indice);
			indiceActual = indice;
		}
	}
	
	private int indiceHijoIzquierdo(int indicePadre) {
		return indicePadre * 2;
	}
	
	private boolean esMayor(int indice1, int indice2) {
		return monticulo[indice1].compareTo(monticulo[indice2]) > 0;
	}

	private boolean esMenor(int indice1, int indice2) {
		return monticulo[indice1].compareTo(monticulo[indice2]) < 0;
	}

	private boolean esIgual(int indice1, int indice2) {
		return monticulo[indice1].compareTo(monticulo[indice2]) == 0;
	}
	
	private int indiceHijoDerecho(int indicePadre){
		return (indicePadre * 2) +1;
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
		Monticulo<Integer> monticuloMin = new MonticuloMinimo<Integer>();
		monticuloMin.add(20);
		monticuloMin.add(7);
		monticuloMin.add(15);
		monticuloMin.add(6);
		monticuloMin.add(2);
		monticuloMin.add(8);
		monticuloMin.add(9);
		monticuloMin.add(5);
		
		while (!monticuloMin.isEmpty()) {
			System.out.println(monticuloMin);
			monticuloMin.remove();
		}
	}
}
