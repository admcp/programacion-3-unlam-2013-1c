package ar.edu.unlam.programacion3.practica2.tda.sel;


public class VectorColumna extends VectorMath {

	public VectorColumna(int cantidadComponentes) {
		validarRangoDimension(cantidadComponentes, 1, Integer.MAX_VALUE);
			
		this.cantidadFilas = cantidadComponentes;
		this.cantidadColumnas = 1;
		coeficientes = new double[cantidadFilas][cantidadColumnas];
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

	@Override
   public double normaUno() {
	   double norma = 0;
		for(int i = 0; i < cantidadFilas; i++) {
			norma += Math.abs(coeficientes[i][0]);
	   }
	   return norma;
   }

	@Override
   public double normaDos() {
	   double norma = 0;
		for(int i = 0; i < cantidadFilas; i++) {
			norma += Math.pow(coeficientes[i][0], 2);
	   }
	   return Math.sqrt(norma);
   }

	@Override
   public double normaInfinito() {
		double norma = 0;
		for(int i = 0; i < cantidadFilas; i++) {
			if (coeficientes[i][0] > norma) {
				norma = Math.abs(coeficientes[0][i]);
			}
		}		
	   return norma;
   }

}
