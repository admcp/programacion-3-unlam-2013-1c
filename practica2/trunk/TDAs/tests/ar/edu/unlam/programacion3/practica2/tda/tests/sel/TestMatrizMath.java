package ar.edu.unlam.programacion3.practica2.tda.tests.sel;

import ar.edu.unlam.programacion3.practica2.tda.sel.MatrizMath;
import ar.edu.unlam.programacion3.practica2.tda.sel.VectorMath;

public class TestMatrizMath {
	
	private static MatrizMath matriz1;
	private static MatrizMath matriz2;
	private static MatrizMath matrizIdentidad;
	private static VectorMath vectorColumna;
	private static VectorMath vectorFila;

	public static void main(String[] args) {
		testEquals();
		testSumaMatrices();
		testRestaMatrices();
		testProductoMatrices();
		testProductoEscalar();
		testProductoVectorColumna();
		testProductoVectorFila();
		testNormaUno();
		testNormaInfinito();
		testTranspuesta();
	}
	
	private static void inicializar() {
		matriz1         = new MatrizMath( new double[][] { {-2, -2, -2}, {-2, -2, -2}, {-2, -2, -2} } );
		matriz2         = new MatrizMath( new double[][] { { 2,  2,  2}, { 2,  2,  2}, { 2,  2,  2} } );
		matrizIdentidad = new MatrizMath( new double[][] { { 1,  0,  0}, { 0,  1,  0}, { 0,  0,  1} } );
		vectorColumna   = new VectorMath( new double[] {2, 2, 2} );
		vectorFila      = new VectorMath( new double[] {2, 2, 2} );
	}
	
	private static void testEquals() {
		inicializar();
		
		MatrizMath clonMatriz1 = matriz1.clone();
		MatrizMath clonClonMatriz1 = clonMatriz1.clone();
		
		if(!matriz1.equals(matriz1)) {
			throw new AssertionError("Falla en el método equals(): reflexividad");
		} 
		
		if(matriz1.equals(clonMatriz1) && !clonMatriz1.equals(matriz1)) {
			throw new AssertionError("Falla en el método equals(): simetria");
		} 
		
		if(matriz1.equals(clonMatriz1) && clonMatriz1.equals(clonClonMatriz1)) {
			if(!matriz1.equals(clonClonMatriz1)) {
				throw new AssertionError("Falla en el método equals(): transitividad");
			}
		} 
		
		if(matriz1 != null && matriz1.equals(null) == true) {
			throw new AssertionError("Falla en el método equals(): nulos");
		}
		
		desinicializar();
	}
	
	private static void testSumaMatrices() {
		inicializar();
		
		MatrizMath resultado = MatrizMath.sumar(matriz1, matriz2);
		MatrizMath resultadoEsperado = new MatrizMath( new double[][] { {0, 0, 0}, {0, 0, 0}, {0, 0, 0} } );
		if(!resultado.equals(resultadoEsperado)) {
			throw new AssertionError("Falla en el método sumar()");
		}
		
		desinicializar();
	}
	
	private static void testRestaMatrices() {
		inicializar();
		
		MatrizMath resultado = MatrizMath.restar(matriz1, matriz2);
		MatrizMath resultadoEsperado = new MatrizMath( new double[][] { {-4, -4, -4}, {-4, -4, -4}, {-4, -4, -4} } );
		if(!resultado.equals(resultadoEsperado)) {
			throw new AssertionError("Falla en el método restar()");
		}
		
		desinicializar();
	}
	
	private static void testProductoMatrices() {
		inicializar();
		
		MatrizMath resultado = MatrizMath.producto(matriz1, matrizIdentidad);
		MatrizMath resultadoEsperado = new MatrizMath( matriz1.obtenerComoMatriz() );
		if(!resultado.equals(resultadoEsperado)) {
			throw new AssertionError("Falla en el método producto()");
		}
		
		desinicializar();
	}
	
	private static void testProductoEscalar() {
		inicializar();
		
		MatrizMath resultado = MatrizMath.productoEscalar(matriz1, 1);
		MatrizMath resultadoEsperado = new MatrizMath( matriz1.obtenerComoMatriz() );
		if(!resultado.equals(resultadoEsperado)) {
			throw new AssertionError("Falla en el método productoEscalar()");
		}
		
		desinicializar();
	}
	
	private static void testProductoVectorColumna() {
		inicializar();
		
		VectorMath resultado = MatrizMath.producto(matriz1, vectorColumna);
		VectorMath resultadoEsperado = new VectorMath( new double[] {-12, -12, -12} );
		if(!resultado.equals(resultadoEsperado)) {
			throw new AssertionError("Falla en el método producto(VectorColumna)");
		}
		
		desinicializar();
	}
	
	
	private static void testProductoVectorFila() {
		inicializar();
		
		VectorMath resultado = MatrizMath.producto(vectorFila, matriz1);
		VectorMath resultadoEsperado = new VectorMath( new double[] {-12, -12, -12} );
		if(!resultado.equals(resultadoEsperado)) {
			throw new AssertionError("Falla en el método producto(VectorFila)");
		}
		
		desinicializar();
	}
	
	private static void testNormaUno() {
		inicializar();
		
		double resultado = matriz2.normaUno();
		double resultadoEsperado = 6;
		if(resultado != resultadoEsperado) {
			throw new AssertionError("Falla en el método normaUno()");
		}
		
		desinicializar();
	}
	
	private static void testNormaInfinito() {
		inicializar();
		
		double resultado = matriz2.normaInfinito();
		double resultadoEsperado = 6;
		if(resultado != resultadoEsperado) {
			throw new AssertionError("Falla en el método normaInfinito()");
		}
		
		desinicializar();
	}
	
	private static void testTranspuesta() {
		inicializar();
		
		MatrizMath resultado = MatrizMath.transponer(matriz1);
		MatrizMath resultadoEsperado = matriz1;
		if(!resultado.equals(resultadoEsperado)) {
			throw new AssertionError("Falla en el método transponer()");
		}
		
		desinicializar();
	}
	
	private static void desinicializar() {
		matriz1         = null;
		matriz2         = null;
		matrizIdentidad = null;
		vectorColumna   = null;
		vectorFila      = null;
	}
	
}
