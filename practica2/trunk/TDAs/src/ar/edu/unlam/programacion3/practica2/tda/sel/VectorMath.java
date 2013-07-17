package ar.edu.unlam.programacion3.practica2.tda.sel;

import java.util.Arrays;

import ar.edu.unlam.programacion3.practica2.tda.sel.exceptions.DimensionInvalidaException;
import ar.edu.unlam.programacion3.practica2.tda.sel.exceptions.RangoInvalidoException;

public class VectorMath implements Cloneable {

	private double[] coeficientes;
	private int cantidadComponentes;

	// CONSTRUCTORES

	public VectorMath(int cantidadComponentes) {
		validarRangoDimension(cantidadComponentes, 1, Integer.MAX_VALUE);

		this.cantidadComponentes = cantidadComponentes;
		this.coeficientes = new double[cantidadComponentes];
	}

	public VectorMath(double[] vector) {
		validarReferencia(vector);
		validarRangoDimension(vector.length, 1, Integer.MAX_VALUE);

		this.cantidadComponentes = vector.length;
		this.coeficientes = new double[vector.length];
		for (int i = 0; i < vector.length; i++) {
			coeficientes[i] = vector[i];
		}
	}

	public VectorMath(VectorMath vector) {
		this(vector.obtenerComoVector());
	}

	// GETTERS/SETTERS

	public int getCantidadComponentes() {
		return cantidadComponentes;
	}

	public double getValorEn(int indice) {
		validarRangoDimension(indice, 0, cantidadComponentes - 1);

		return coeficientes[indice];
	}

	public void setValorEn(int indice, double valor) {
		validarRangoDimension(indice, 0, cantidadComponentes - 1);

		coeficientes[indice] = valor;
	}

	// MÉTODOS DE CONVERSIÓN

	public double[] obtenerComoVector() {
		double[] copiaCoeficientes = new double[cantidadComponentes];

		for (int i = 0; i < cantidadComponentes; i++) {
			copiaCoeficientes[i] = coeficientes[i];
		}

		return copiaCoeficientes;
	}

	public void inicializarConVector(double[] vector) {
		validarReferencia(vector);
		validarDimension(vector.length, cantidadComponentes);

		for (int i = 0; i < cantidadComponentes; i++) {
			this.coeficientes[i] = vector[i];
		}
	}

	// OPERACIONES ALGEBRÁICAS

	public void sumar(VectorMath operando) {
		validarReferencia(operando);
		validarDimension(cantidadComponentes, operando.cantidadComponentes);

		for (int i = 0; i < cantidadComponentes; i++) {
			coeficientes[i] += operando.coeficientes[i];
		}
	}

	public static VectorMath sumar(VectorMath operando1, VectorMath operando2) {
		validarReferencia(operando1);
		validarReferencia(operando2);
		validarDimension(operando1.cantidadComponentes, operando2.cantidadComponentes);
		validarDimension(operando1.cantidadComponentes, operando2.cantidadComponentes);

		VectorMath aux = new VectorMath(operando1.cantidadComponentes);
		for (int i = 0; i < operando1.cantidadComponentes; i++) {
			aux.coeficientes[i] = operando1.coeficientes[i] + operando2.coeficientes[i];
		}

		return aux;
	}

	public void restar(VectorMath operando) {
		validarReferencia(operando);
		validarDimension(cantidadComponentes, operando.cantidadComponentes);

		for (int i = 0; i < cantidadComponentes; i++) {
			coeficientes[i] -= operando.coeficientes[i];
		}
	}

	public static VectorMath restar(VectorMath operando1, VectorMath operando2) {
		validarReferencia(operando1);
		validarReferencia(operando2);
		validarDimension(operando1.cantidadComponentes, operando2.cantidadComponentes);
		validarDimension(operando1.cantidadComponentes, operando2.cantidadComponentes);

		VectorMath aux = new VectorMath(operando1.cantidadComponentes);
		for (int i = 0; i < operando1.cantidadComponentes; i++) {
			aux.coeficientes[i] = operando1.coeficientes[i] - operando2.coeficientes[i];
		}

		return aux;
	}

	public void productoEscalar(double escalar) {
		for (int i = 0; i < cantidadComponentes; i++) {
			coeficientes[i] *= escalar;
		}
	}

	public static VectorMath productoEscalar(VectorMath operando, double escalar) {
		validarReferencia(operando);
		validarReferencia(escalar);

		VectorMath aux = new VectorMath(operando.cantidadComponentes);
		for (int i = 0; i < operando.cantidadComponentes; i++) {
			aux.coeficientes[i] = operando.coeficientes[i] * escalar;
		}

		return aux;
	}

	public static VectorMath producto(VectorMath operando1, VectorMath operando2) {
		validarReferencia(operando1);
		validarReferencia(operando2);
		validarDimension(operando1.cantidadComponentes, operando2.cantidadComponentes);

		double sumatoria = 0;
		VectorMath aux = new VectorMath(1);
		for (int i = 0; i < operando1.cantidadComponentes; i++) {
			sumatoria += operando1.coeficientes[i] * operando2.coeficientes[i];
		}

		aux.setValorEn(0, sumatoria);

		return aux;
	}

	public double normaUno() {

		double norma = 0;
		for (int i = 0; i < this.getCantidadComponentes(); i++)
			norma += Math.abs(this.getValorEn(i));

		return norma;
	}

	public double normaDos() {
		double norma = 0;
		for (int i = 0; i < this.getCantidadComponentes(); i++)
			norma += Math.pow(Math.abs(this.getValorEn(i)), 2);

		return Math.sqrt(norma);
	}

	public double normaInfinito() {
		double norma = 0;
		for (int i = 0; i < this.getCantidadComponentes(); i++)
			if (Math.abs(this.getValorEn(i)) > norma)
				norma = Math.abs(this.getValorEn(i));

		return norma;
	}

	// MÉTODOS UTILITARIOS

	private static void validarReferencia(Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException("El argumento no puede ser nulo");
		}
	}

	private static void validarRangoDimension(int valor, int valorMinimo, int valorMaximo) {
		if (valor < valorMinimo || valor > valorMaximo) {
			throw new RangoInvalidoException(valor, valorMinimo, valorMaximo);
		}
	}

	private static void validarDimension(int valor1, int valor2) {
		if (valor1 != valor2) {
			throw new DimensionInvalidaException(valor1, valor2);
		}
	}

	// MÉTODOS HEREDADOS DE OBJECT

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		for (int i = 0; i < cantidadComponentes; i++) {
			builder.append(String.format("% 13.7f", coeficientes[i]) + ", ");
		}
		builder.append("}");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidadComponentes;
		result = prime * result + Arrays.hashCode(coeficientes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VectorMath))
			return false;
		VectorMath other = (VectorMath) obj;
		if (cantidadComponentes != other.cantidadComponentes)
			return false;
		if (!Arrays.equals(coeficientes, other.coeficientes))
			return false;
		return true;
	}

	@Override
	public VectorMath clone() {
		VectorMath clon = null;
		try {
			clon = (VectorMath) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new AssertionError();
		}

		for (int i = 0; i < clon.cantidadComponentes; i++) {
			clon.coeficientes[i] = coeficientes[i];
		}

		return clon;
	}

	// MAIN PARA EL TEST
	public static void main(String[] args) {
		VectorMath vec1, vec2, aux;
		vec1 = new VectorMath(2);
		vec2 = new VectorMath(2);

		vec1.setValorEn(0, 1);
		vec1.setValorEn(1, 1);

		vec2.setValorEn(0, 2);
		vec2.setValorEn(1, 2);

		System.out.println(vec1);
		System.out.println(vec2);

		for (int i = 0; i < vec1.getCantidadComponentes(); i++) {
			System.out.println(vec1.getValorEn(i));
		}

		for (int i = 0; i < vec2.getCantidadComponentes(); i++) {
			System.out.println(vec2.getValorEn(i));
		}

		System.out.println("Suma");
		aux = new VectorMath(sumar(vec1, vec2));

		for (int i = 0; i < aux.getCantidadComponentes(); i++) {
			System.out.println(aux.getValorEn(i));
		}

		System.out.println("Resta");
		aux = restar(vec1, vec2);

		for (int i = 0; i < aux.getCantidadComponentes(); i++) {
			System.out.println(aux.getValorEn(i));
		}

		System.out.println("Producto escalar x7");
		aux = productoEscalar(vec1, 7);
		for (int i = 0; i < aux.getCantidadComponentes(); i++) {
			System.out.println(aux.getValorEn(i));
		}
	}

}
