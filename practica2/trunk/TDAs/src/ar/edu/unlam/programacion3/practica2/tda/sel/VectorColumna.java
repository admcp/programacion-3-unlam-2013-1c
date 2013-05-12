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

}
