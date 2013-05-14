package ar.edu.unlam.programacion3.practica2.tda.sel;

public class VectorColumna extends VectorMath {

	public VectorColumna(int cantidadComponentes) {
		validarRangoDimension(cantidadComponentes, 1, Integer.MAX_VALUE);

		this.cantidadFilas = cantidadComponentes;
		this.cantidadColumnas = 1;
		coeficientes = new double[cantidadFilas][cantidadColumnas];
	}

	private VectorColumna(MatrizMath matriz) {
		cantidadFilas = matriz.cantidadFilas;
		cantidadColumnas = 1;
		coeficientes = new double[cantidadFilas][cantidadColumnas];
		for (int i = 0; i < matriz.cantidadFilas; i++) {
			for (int j = 0; j < 1; j++) {
				coeficientes[i][j] = matriz.coeficientes[i][j];
			}
		}
	}

	public VectorColumna(double[] vector) {
		validarReferencia(vector);
		validarRangoDimension(vector.length, 1, Integer.MAX_VALUE);
		
		cantidadFilas = vector.length;
		cantidadColumnas = 1;
		coeficientes = new double[cantidadFilas][cantidadColumnas];
		for (int i = 0; i < vector.length; i++) {
			coeficientes[i][0] = vector[i];
		}
	}

	@Override
	public int getCantidadComponentes() {
		return cantidadFilas;
	}

	@Override
	public double getValorEn(int indice) {
		validarRangoDimension(indice, 0, cantidadFilas - 1);

		return coeficientes[indice][0];
	}

	public void setValorEn(int indice, double valor) {
		validarRangoDimension(indice, 0, cantidadFilas - 1);

		coeficientes[indice][0] = valor;
	}

	// Suma, Resta, Multiplicacion
	public static VectorColumna sumar(VectorColumna operando1, VectorColumna operando2) {
		return new VectorColumna(MatrizMath.sumar(operando1, operando2));
	}

	public static VectorColumna restar(VectorColumna operando1, VectorColumna operando2) {
		return new VectorColumna(MatrizMath.restar(operando1, operando2));
	}

	public static VectorColumna productoEscalar(VectorColumna operando, double escalar) {
		return new VectorColumna(MatrizMath.productoEscalar(operando, escalar));
	}

	// MAIN PARA EL TEST
	public static void main(String[] args) {
		VectorColumna vec1, vec2, aux;
		vec1 = new VectorColumna(2);
		vec2 = new VectorColumna(2);

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
		aux = new VectorColumna(sumar(vec1, vec2));

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
