package ar.edu.unlam.programacion3.practica2.tda.pila;

import java.util.Arrays;

public class PilaEstatica<T> implements Pila<T> {

	// Miembros Privados.
	private int dimensionReal;
	private int tope;
	private T pila[];

	public PilaEstatica() {
		this(10);
	}
	
	@SuppressWarnings("unchecked")
	public PilaEstatica(int dimensionInicial) {
		if (dimensionInicial <= 0) {
			throw new IllegalArgumentException();
		}
		this.dimensionReal = 0;
		pila = (T[]) new Object[dimensionInicial];
		tope = -1;
	}

	@Override
	public boolean isEmpty() {
		return (tope == -1);
	}

	@Override
	public void push(T elemento) {
		if(elemento == null) {
			throw new NullPointerException();
		}
		if (dimensionReal == pila.length) {
			resize(pila.length * 2);
		}
		dimensionReal++;
		pila[++tope] = elemento;
	}

	@Override
	public T pop() {
		if (tope == -1) {
			return null;
		}
		T elemento = pila[tope];
		pila[tope--] = null;
		dimensionReal--;
		if(dimensionReal == pila.length / 4) {
			resize(pila.length / 2);
		}
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
		dimensionReal = 0;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(pila) + "(T= " + tope + ", " + dimensionReal + "/" + pila.length + ")";
	}

	@SuppressWarnings("unchecked")
	private void resize(int newSize) {
		T[] newArray = (T[]) new Object[newSize];
		for (int i = 0; i < dimensionReal; i++) {
			newArray[i] = pila[i];
		}
		pila = newArray;
	}

}
