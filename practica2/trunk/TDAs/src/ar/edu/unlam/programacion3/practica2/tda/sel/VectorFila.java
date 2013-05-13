package ar.edu.unlam.programacion3.practica2.tda.sel;


public class VectorFila extends VectorMath {

	public VectorFila(int cantidadComponentes) {
		validarRangoDimension(cantidadComponentes, 1, Integer.MAX_VALUE);
		
		this.cantidadFilas = 1;
		this.cantidadColumnas = cantidadComponentes;
		coeficientes = new double[cantidadFilas][cantidadColumnas];
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

	@Override
   public double normaUno() {
	   double norma = 0;
		for(int i = 0; i < cantidadColumnas; i++) {
			norma += Math.abs(coeficientes[0][i]);
	   }
	   return norma;
   }
	
	@Override
   public double normaDos() {
	   double norma = 0;
		for(int i = 0; i < cantidadColumnas; i++) {
			norma += Math.pow(coeficientes[0][i], 2);
	   }
	   return Math.sqrt(norma);
   }
	
	@Override
   public double normaInfinito() {
		double norma = 0;
		for(int i = 0; i < cantidadColumnas; i++) {
			if (coeficientes[0][i] > norma) {
				norma = Math.abs(coeficientes[0][i]);
			}
		}		
	   return norma;
   }
}
