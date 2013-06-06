package ar.edu.unlam.programacion3.practica2.tda.cola;

import java.util.Arrays;

public class ColaEstatica<T> implements Cola<T> {

	// MIEMBROS PRIVADOS
	private int dimensionReal;
	private T cola[];
	private int primero;
	private int ultimo;

	public ColaEstatica() {
		this(10);
	}

	@SuppressWarnings("unchecked")
	public ColaEstatica(int dimensionInicial) {
		if (dimensionInicial <= 0) {
			throw new IllegalArgumentException();
		}

		this.dimensionReal = 0;
		cola = (T[]) new Object[dimensionInicial];
		primero = 0;
		ultimo = 0;
	}

	@Override
	public boolean isEmpty() {
		return dimensionReal == 0;
	}

	@Override
	public void offer(T elemento) {
		if (elemento == null) {
			throw new NullPointerException();
		}

		if (dimensionReal == cola.length) {
			resize(cola.length * 2);
		}

		cola[ultimo++] = elemento;
		if (ultimo == cola.length) {
			ultimo = 0;
		}
		dimensionReal++;

	}

	@Override
	public T poll() {
		if (dimensionReal == 0) {
			return null;
		}

		T elemento = cola[primero];
		cola[primero] = null;
		dimensionReal--;
		primero++;

		if (primero == cola.length) {
			primero = 0;
		}

		if (dimensionReal > 0 && dimensionReal == cola.length / 4) {
			resize(cola.length / 2);
		}

		return elemento;
	}

	@Override
	public T peek() {
		if (dimensionReal == 0) {
			return null;
		}

		return cola[primero];
	}

	@Override
	public void clear() {
		for (int i = 0; i < dimensionReal; i++) {
			cola[i] = null;
		}
		ultimo = 0;
		dimensionReal = 0;
	}

	@Override
	public String toString() {
		return Arrays.toString(cola) + "(P=" + primero + ", U=" + ultimo + ", " + dimensionReal + "/" + cola.length	+ ")";
	}

	@SuppressWarnings("unchecked")
	private void resize(int newSize) {
		T[] nuevaCola = (T[]) new Object[newSize];
		for (int i = 0; i < dimensionReal; i++) {
			nuevaCola[i] = cola[(primero + i) % cola.length];
		}
		cola = nuevaCola;
		primero = 0;
		ultimo = dimensionReal;
	}

}
