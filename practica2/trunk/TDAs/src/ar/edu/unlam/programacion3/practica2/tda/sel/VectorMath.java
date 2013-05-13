package ar.edu.unlam.programacion3.practica2.tda.sel;

public abstract class VectorMath extends MatrizMath {

	public abstract int getCantidadComponentes();
	public abstract double getValorEn(int indice);
	public abstract void setValorEn(int indice, double valor);
	public abstract double normaUno();
	public abstract double normaDos();
	public abstract double normaInfinito();
}
