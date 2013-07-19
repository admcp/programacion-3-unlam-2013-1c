package ar.edu.unlam.programacion3.practica2.tda.sel;

public class MatrizIdentidad extends MatrizInvertible {
	
	public MatrizIdentidad(int dimension){
		super(dimension);
		for(int i = 0; i < dimension; i++){
			coeficientes[i][i] = 1;
		}
	}
}
