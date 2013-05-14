package ar.edu.unlam.programacion3.practica2.tda.sel;

import java.util.Arrays;

import ar.edu.unlam.programacion3.practica2.tda.sel.exceptions.DimensionInvalidaException;
import ar.edu.unlam.programacion3.practica2.tda.sel.exceptions.RangoInvalidoException;
import ar.edu.unlam.programacion3.practica2.tda.tests.sel.TestMatrizMath;

public class MatrizMath {

	protected double[][] coeficientes;
	protected int cantidadFilas;
	protected int cantidadColumnas;

	protected MatrizMath() {
		/*
		 * No queremos que el usuario pueda alterar la estructura interna de la
		 * matriz y controlar la creación desde el principio. Por ende, no se
		 * puede usar este constructor desde afuera.
		 */
	}

	// CONSTRUCTORES

	public MatrizMath(int cantidadFilas, int cantidadColumnas) {
		validarRangoDimension(cantidadFilas, 1, Integer.MAX_VALUE);
		validarRangoDimension(cantidadColumnas, 1, Integer.MAX_VALUE);

		this.cantidadFilas = cantidadFilas;
		this.cantidadColumnas = cantidadColumnas;
		coeficientes = new double[cantidadFilas][cantidadColumnas];
	}

	public MatrizMath(double[][] coeficientes) {
		validarReferencia(coeficientes);
		validarRangoDimension(coeficientes.length, 1, Integer.MAX_VALUE);
		validarRangoDimension(coeficientes[0].length, 1, Integer.MAX_VALUE);

		cantidadFilas = coeficientes.length;
		cantidadColumnas = coeficientes[0].length;
		this.coeficientes = new double[cantidadFilas][cantidadColumnas];

		for (int i = 0; i < cantidadFilas; i++) {
			for (int j = 0; j < cantidadColumnas; j++) {
				this.coeficientes[i][j] = coeficientes[i][j];
			}
		}
	}

	// GETTERS/SETTERS

	public double[][] obtenerComoMatriz() {
		double[][] copiaCoeficientes = new double[cantidadFilas][cantidadColumnas];

		for (int i = 0; i < cantidadFilas; i++) {
			for (int j = 0; j < cantidadColumnas; j++) {
				copiaCoeficientes[i][j] = coeficientes[i][j];
			}
		}

		return copiaCoeficientes;
	}

	public void inicializarConMatriz(double[][] coeficientes) {
		validarReferencia(coeficientes);
		validarRangoDimension(coeficientes.length, 1, cantidadFilas - 1);
		validarRangoDimension(coeficientes[0].length, 1, cantidadColumnas - 1);

		for (int i = 0; i < cantidadFilas; i++) {
			for (int j = 0; j < cantidadColumnas; j++) {
				this.coeficientes[i][j] = coeficientes[i][j];
			}
		}
	}

	public int getCantidadFilas() {
		return cantidadFilas;
	}

	public int getCantidadColumnas() {
		return cantidadColumnas;
	}

	public double getValorEn(int fila, int columna) {
		validarRangoDimension(fila, 0, cantidadFilas - 1);
		validarRangoDimension(columna, 0, cantidadColumnas - 1);

		return coeficientes[fila][columna];
	}

	public void setValorEn(int fila, int columna, double valor) {
		validarRangoDimension(fila, 0, cantidadFilas - 1);
		validarRangoDimension(columna, 0, cantidadColumnas - 1);

		coeficientes[fila][columna] = valor;
	}
	
	public VectorColumna getColumna(int columna) {
		validarRangoDimension(columna, 0, cantidadColumnas - 1);
		
		double[] vector = new double[cantidadFilas];
		
		for(int i = 0; i < cantidadFilas; i++) {
			vector[i] = coeficientes[i][columna];
		}
		
		return new VectorColumna(vector);
	}

	public void setColumna(VectorColumna vector, int columna) {
		validarReferencia(vector);
		validarDimension(vector.cantidadFilas, cantidadFilas);
		validarRangoDimension(columna, 0, cantidadColumnas - 1);

		for (int i = 0; i < cantidadFilas; i++) {
			coeficientes[i][columna] = vector.getValorEn(i);
		}
	}
	
	public VectorFila getFila(int fila) {
		validarRangoDimension(fila, 0, cantidadFilas - 1);
		
		double[] vector = new double[cantidadColumnas];
		
		for(int i = 0; i < cantidadColumnas; i++) {
			vector[i] = coeficientes[fila][i];
		}
		
		return new VectorFila(vector);
	}

	public void setFila(VectorFila vector, int fila) {
		validarReferencia(vector);
		validarDimension(vector.cantidadColumnas, cantidadColumnas);
		validarRangoDimension(fila, 0, cantidadFilas - 1);

		for (int i = 0; i < cantidadColumnas; i++) {
			coeficientes[fila][i] = vector.getValorEn(i);
		}
	}

	// MÉTODOS ALGEBRÁICOS

	public static MatrizMath sumar(MatrizMath operando1, MatrizMath operando2) {
		validarReferencia(operando1);
		validarReferencia(operando2);
		validarDimension(operando1.cantidadFilas, operando2.cantidadFilas);
		validarDimension(operando1.cantidadColumnas, operando2.cantidadColumnas);

		MatrizMath aux = new MatrizMath(operando1.cantidadFilas, operando1.cantidadColumnas);
		for (int i = 0; i < operando1.cantidadFilas; i++) {
			for (int j = 0; j < operando1.cantidadColumnas; j++) {
				aux.coeficientes[i][j] = operando1.coeficientes[i][j] + operando2.coeficientes[i][j];
			}
		}

		return aux;
	}

	public static MatrizMath restar(MatrizMath operando1, MatrizMath operando2) {
		validarReferencia(operando1);
		validarReferencia(operando2);
		validarDimension(operando1.cantidadFilas, operando2.cantidadFilas);
		validarDimension(operando1.cantidadColumnas, operando2.cantidadColumnas);

		MatrizMath aux = new MatrizMath(operando1.cantidadFilas, operando1.cantidadColumnas);
		for (int i = 0; i < operando1.cantidadFilas; i++) {
			for (int j = 0; j < operando1.cantidadColumnas; j++) {
				aux.coeficientes[i][j] = operando1.coeficientes[i][j] - operando2.coeficientes[i][j];
			}
		}

		return aux;
	}

	public static MatrizMath producto(MatrizMath operando1, MatrizMath operando2) {
		validarReferencia(operando1);
		validarReferencia(operando2);
		validarDimension(operando1.cantidadColumnas, operando2.cantidadFilas);

		double sumatoria = 0;
		MatrizMath aux = new MatrizMath(operando1.cantidadFilas, operando2.cantidadColumnas);
		for (int i = 0; i < operando1.cantidadFilas; i++) {
			for (int j = 0; j < operando2.cantidadColumnas; j++) {
				for (int k = 0; k < operando1.cantidadColumnas; k++) {
					sumatoria += operando1.coeficientes[i][k] * operando2.coeficientes[k][j];
				}
				aux.coeficientes[i][j] = sumatoria;
				sumatoria = 0;
			}
		}

		return aux;
	}

	public static VectorColumna producto(MatrizMath operando1, VectorColumna operando2) {
		validarReferencia(operando1);
		validarReferencia(operando2);
		validarDimension(operando1.cantidadColumnas, operando2.cantidadFilas);

		double sumatoria = 0;
		VectorColumna aux = new VectorColumna(operando2.getCantidadComponentes());
		for (int i = 0; i < operando1.cantidadFilas; i++) {
			for (int j = 0; j < operando2.cantidadColumnas; j++) {
				for (int k = 0; k < operando1.cantidadColumnas; k++) {
					sumatoria += operando1.coeficientes[i][k] * operando2.coeficientes[k][j];
				}
				aux.coeficientes[i][j] = sumatoria;
				sumatoria = 0;
			}
		}

		return aux;
	}

	public static VectorFila producto(MatrizMath operando1, VectorFila operando2) {
		validarReferencia(operando1);
		validarReferencia(operando2);
		validarDimension(operando1.cantidadColumnas, operando2.cantidadFilas);

		double sumatoria = 0;
		VectorFila aux = new VectorFila(operando2.getCantidadComponentes());
		for (int i = 0; i < operando1.cantidadFilas; i++) {
			for (int j = 0; j < operando2.cantidadColumnas; j++) {
				for (int k = 0; k < operando1.cantidadColumnas; k++) {
					sumatoria += operando1.coeficientes[i][k] * operando2.coeficientes[k][j];
				}
				aux.coeficientes[i][j] = sumatoria;
				sumatoria = 0;
			}
		}

		return aux;
	}

	public static MatrizMath productoEscalar(MatrizMath operando1, double escalar) {
		validarReferencia(operando1);

		MatrizMath aux = new MatrizMath(operando1.cantidadFilas, operando1.cantidadColumnas);
		for (int i = 0; i < operando1.cantidadFilas; i++) {
			for (int j = 0; j < operando1.cantidadColumnas; j++) {
				aux.coeficientes[i][j] = operando1.coeficientes[i][j] * escalar;
			}
		}

		return aux;
	}
	
	public static MatrizMath transponer(MatrizMath matriz) {
		validarReferencia(matriz);
		
		double[][] matrizTranspuesta = new double[matriz.cantidadColumnas][matriz.cantidadFilas];
		for(int i = 0; i < matriz.cantidadFilas; i++) {
			for(int j = 0; j < matriz.cantidadColumnas; j++) {
				matrizTranspuesta[j][i] = matriz.coeficientes[i][j];
			}
		}
		
		return new MatrizMath(matrizTranspuesta);
	}

	public double normaUno() {
		// Es la máxima suma absoluta de las columnas de la matriz.
		double norma = 0;
		double suma;

		for (int i = 0; i < cantidadColumnas; i++) {
			suma = 0;
			for (int j = 0; j < cantidadFilas; j++) {
				suma += Math.abs(coeficientes[j][i]);
			}

			if (suma > norma) {
				norma = suma;
			}
		}
		return norma;
	}

	/*
	public double normaDos() {
		double norma = 0;

		for (int i = 0; i < cantidadFilas; i++) {
			for (int j = 0; j < cantidadColumnas; j++) {
				norma += Math.pow(coeficientes[i][j], 2);
			}
		}
		return Math.sqrt(norma);
	}
	*/

	public double normaInfinito() {
		// Es la máxima suma absoluta de las filas de la matriz.
		double norma = 0;
		double suma;

		for (int i = 0; i < cantidadFilas; i++) {
			suma = 0;
			for (int j = 0; j < cantidadColumnas; j++) {
				suma += Math.abs(coeficientes[i][j]);
			}

			if (suma > norma) {
				norma = suma;
			}
		}
		return norma;
	}

	// MÉTODOS UTILITARIOS

	protected static void validarReferencia(Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException("El argumento no puede ser nulo");
		}
	}

	protected static void validarRangoDimension(int valor, int valorMinimo, int valorMaximo) {
		if (valor < valorMinimo || valor > valorMaximo) {
			throw new RangoInvalidoException(valor, valorMinimo, valorMaximo);
		}
	}

	protected static void validarDimension(int valor1, int valor2) {
		if (valor1 != valor2) {
			throw new DimensionInvalidaException(valor1, valor2);
		}
	}
	
	// MÉTODOS HEREDADOS DE OBJECT
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		for(int i = 0; i < cantidadFilas; i++) {
			if(i == 0)
				builder.append("{");
			else
				builder.append(" {");
			for(int j = 0; j < cantidadColumnas; j++) {
				// Formato lindo: 
				builder.append(String.format("% 13.7f", coeficientes[i][j]) + ", ");
				// Formato comun: builder.append(valores[i][j] + ", ");
			}
			builder.replace(builder.lastIndexOf(", "), builder.length(), "");
			builder.append("}, \n");
		}
		builder.replace(builder.lastIndexOf("}, "), builder.length(), "}");
		builder.append("}");
		builder.append(" (" + cantidadFilas + "x" + cantidadColumnas + ")");
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidadColumnas;
		result = prime * result + cantidadFilas;
		result = prime * result + Arrays.hashCode(coeficientes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MatrizMath))
			return false;
		MatrizMath other = (MatrizMath) obj;
		if (cantidadColumnas != other.cantidadColumnas)
			return false;
		if (cantidadFilas != other.cantidadFilas)
			return false;
		for(int i = 0; i < cantidadFilas; i++) {
			for(int j = 0; j < cantidadColumnas; j++) {
				if(coeficientes[i][j] != other.coeficientes[i][j]) {
					return false;
				}
			}
		}
		return true;
	}	

	// MAIN

	public static void main(String[] args) {
		// FIXME: Corregir ANTES de entregar.
		TestMatrizMath.main(null);
	}
}
