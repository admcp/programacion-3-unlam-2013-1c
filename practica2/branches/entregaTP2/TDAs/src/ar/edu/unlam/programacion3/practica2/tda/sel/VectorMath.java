package ar.edu.unlam.programacion3.practica2.tda.sel;

public abstract class VectorMath extends MatrizMath {

	public abstract int getCantidadComponentes();
	public abstract double getValorEn(int indice);
	public abstract void setValorEn(int indice, double valor);
	
	@Override
	public double normaUno() {
		
		double norma = 0;
		for(int i = 0; i < this.getCantidadComponentes(); i++)
			norma += Math.abs(this.getValorEn(i));
		
		return norma;
	}
	
	public double normaDos() {
		double norma = 0;
		for(int i = 0; i < this.getCantidadComponentes(); i++)
			norma += Math.pow(Math.abs(this.getValorEn(i)), 2);
		
		return Math.sqrt(norma);
	}
	
	@Override
	public double normaInfinito() {
		double norma = 0;
		for(int i = 0; i < this.getCantidadComponentes(); i++)
			if(Math.abs(this.getValorEn(i)) > norma) 
				norma = Math.abs(this.getValorEn(i));
		
		return norma;
	}
}
