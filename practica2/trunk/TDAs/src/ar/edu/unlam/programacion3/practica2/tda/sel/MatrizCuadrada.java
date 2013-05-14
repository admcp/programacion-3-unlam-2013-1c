package ar.edu.unlam.programacion3.practica2.tda.sel;

public class MatrizCuadrada extends MatrizMath {
	
	public MatrizCuadrada(int dimension){
		super(dimension, dimension);
	}
	
	private MatrizCuadrada(MatrizMath matriz){
		validarReferencia(matriz);
		validarDimension(matriz.cantidadColumnas, matriz.cantidadFilas);
		
		cantidadFilas = matriz.cantidadFilas;
		cantidadColumnas = matriz.cantidadColumnas;
		coeficientes = new double[cantidadFilas][cantidadColumnas];
		for(int i = 0; i < cantidadFilas; i++){
			for(int j = 0; j < cantidadColumnas; j++){
				coeficientes[i][j] = matriz.coeficientes[i][j];
			}
		}
	}
	
	public static MatrizCuadrada sumar(MatrizCuadrada operando1, MatrizCuadrada operando2){
		return new MatrizCuadrada(MatrizMath.sumar(operando1, operando2));
	}
	
	public static MatrizCuadrada restar(MatrizCuadrada operando1, MatrizCuadrada operando2){
		return new MatrizCuadrada(MatrizMath.restar(operando1, operando2));
	}
	
	public static MatrizCuadrada productoEscalar(MatrizCuadrada operando, double escalar){
		return new MatrizCuadrada(MatrizMath.productoEscalar(operando, escalar));
	}
	
	public static MatrizCuadrada producto(MatrizCuadrada operando1, MatrizCuadrada operando2){
		return new MatrizCuadrada(MatrizMath.producto(operando1, operando2));
	}
	public static double determinante(MatrizCuadrada operando) {
		validarReferencia(operando);
		validarDimension(operando.cantidadFilas, operando.cantidadColumnas);
		
		double determinante = 0;
		int signo = 1;
		if (operando.cantidadFilas > 2){
			for (int i = 0; i < operando.cantidadColumnas; i++) {  
				determinante += signo * operando.coeficientes[0][i] * determinante(obtenerSubMatriz(operando,0,i));
				signo = signo * (-1);
			}
			return determinante;
		}
		
		return (operando.coeficientes[0][0] * operando.coeficientes[1][1]) - (operando.coeficientes[1][0] * operando.coeficientes[0][1]);
	}
	
	public static MatrizCuadrada obtenerSubMatriz(MatrizCuadrada operando, int ignorarFila, int ignorarColumna) {
		MatrizCuadrada subMatriz = new MatrizCuadrada(operando.cantidadFilas -1);
		int fila;
		int columna;
		
		fila = 0;
		for (int i = 0; i < operando.cantidadFilas; i++) {
			if (i != ignorarFila) {
				columna = 0;
				for (int j = 0; j < operando.cantidadColumnas; j++) {
					if (j != ignorarColumna) {
						subMatriz.coeficientes[fila][columna] = operando.coeficientes[i][j];
						columna++;
					}
				}
				fila++;
			}
		}
		return subMatriz;
	}

	public static void main(String[] args) {
		MatrizCuadrada matriz1 = new MatrizCuadrada(2);
		MatrizCuadrada matriz2 = new MatrizCuadrada(2);
		MatrizCuadrada aux;

		for (int i = 0; i < matriz1.getCantidadFilas(); i++) {
			for (int j = 0; j < matriz1.getCantidadColumnas(); j++) {
				matriz1.setValorEn(i, j, 2);
			}
		}

		for (int i = 0; i < matriz2.getCantidadFilas(); i++) {
			for (int j = 0; j < matriz2.getCantidadColumnas(); j++) {
				matriz2.setValorEn(i, j, 3);
			}
		}
		
		aux = sumar(matriz1, matriz2);
		
		for (int i = 0; i < aux.getCantidadFilas(); i++) {
			for (int j = 0; j < aux.getCantidadColumnas(); j++) {
				System.out.print(aux.getValorEn(i, j) + " ");
			}
			System.out.println();
		}
		
		aux = restar(matriz1, matriz2);
		
		for (int i = 0; i < aux.getCantidadFilas(); i++) {
			for (int j = 0; j < aux.getCantidadColumnas(); j++) {
				System.out.print(aux.getValorEn(i, j) + " ");
			}
			System.out.println();
		}
		
		aux = producto(matriz1, matriz2);
		
		for (int i = 0; i < aux.getCantidadFilas(); i++) {
			for (int j = 0; j < aux.getCantidadColumnas(); j++) {
				System.out.print(aux.getValorEn(i, j) + " ");
			}
			System.out.println();
		}
		
		aux = productoEscalar(matriz1, 2);
		
		for (int i = 0; i < aux.getCantidadFilas(); i++) {
			for (int j = 0; j < aux.getCantidadColumnas(); j++) {
				System.out.print(aux.getValorEn(i, j) + " ");
			}
			System.out.println();
		}
		
		
	}
	
}
