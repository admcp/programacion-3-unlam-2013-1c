package ar.edu.unlam.programacion3.practica2.tda.sel;

public class VectorFila extends VectorMath {

	public VectorFila(int cantidadComponentes) {
		validarRangoDimension(cantidadComponentes, 1, Integer.MAX_VALUE);

		this.cantidadFilas = 1;
		this.cantidadColumnas = cantidadComponentes;
		coeficientes = new double[cantidadFilas][cantidadColumnas];
	}

	public VectorFila(MatrizMath matriz) {
		cantidadFilas = 1;
		cantidadColumnas = matriz.getCantidadColumnas();
		coeficientes = new double[cantidadFilas][cantidadColumnas];
		for (int i = 0; i < cantidadFilas; i++) {
			for (int j = 0; j < cantidadColumnas; j++) {
				coeficientes[i][j] = matriz.coeficientes[i][j];
			}
		}
	}

	@Override
	public int getCantidadComponentes() {
		return cantidadColumnas;
	}

	@Override
	public double getValorEn(int indice) {
		validarRangoDimension(indice, 0, cantidadColumnas - 1);

		return coeficientes[0][indice];
	}

	@Override
	public void setValorEn(int indice, double valor) {
		validarRangoDimension(indice, 0, cantidadColumnas - 1);

		coeficientes[0][indice] = valor;
	}

	// Operaciones Sumar, Restar, Producto Escalar

	public static VectorFila sumar(VectorFila operando1, VectorFila operando2) {
		return new VectorFila(MatrizMath.sumar(operando1, operando2));
	}

	public static VectorFila restar(VectorFila operando1, VectorFila operando2) {
		return new VectorFila(MatrizMath.restar(operando1, operando2));
	}

	public static VectorFila productoEscalar(VectorFila operando, double escalar) {
		return new VectorFila(MatrizMath.productoEscalar(operando, escalar));
	}

	@Override
	public double normaUno() {
		double norma = 0;
		for (int i = 0; i < cantidadColumnas; i++) {
			norma += Math.abs(coeficientes[0][i]);
		}
		return norma;
	}

	@Override
	public double normaDos() {
		double norma = 0;
		for (int i = 0; i < cantidadColumnas; i++) {
			norma += Math.pow(coeficientes[0][i], 2);
		}
		return Math.sqrt(norma);
	}

	@Override
	public double normaInfinito() {
		double norma = 0;
		for (int i = 0; i < cantidadColumnas; i++) {
			if (coeficientes[0][i] > norma) {
				norma = Math.abs(coeficientes[0][i]);
			}
		}
		return norma;
	}

	// MAIN PARA HACER TEST

	// MAIN PARA EL TEST
	public static void main(String[] args) {
		VectorFila vec1, vec2, aux;
		vec1 = new VectorFila(2);
		vec2 = new VectorFila(2);

		vec1.setValorEn(0, 1);
		vec1.setValorEn(1, 1);

		vec2.setValorEn(0, 2);
		vec2.setValorEn(1, 2);

		for (int i = 0; i < vec1.getCantidadComponentes(); i++) {
			System.out.println(vec1.getValorEn(i));
		}

		for (int i = 0; i < vec2.getCantidadComponentes(); i++) {
			System.out.println(vec2.getValorEn(i));
		}

		System.out.println("Suma");
		aux = new VectorFila(sumar(vec1, vec2));

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
