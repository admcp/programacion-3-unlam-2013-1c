package ar.edu.unlam.programacion3.adt.grafo;

public class Coloreo {
	private static void blanquear(Grafo gColoreado) {
		for (int i = 0; i < gColoreado.getNodos().length; i++) {
			gColoreado.getNodo(i).setColor(0);
		}
		gColoreado.setNumCroma(0);
	}

	public static Grafo multicolor(Grafo g) {
		Grafo gColoreado = g.clone();
		for (int i = 0; i < gColoreado.getNodos().length; i++) {
			gColoreado.getNodo(i).setColor(i + 1);
			gColoreado.setNumCroma(i);
		}
		return gColoreado;
	}

	public static void secuencial(Grafo gColoreado) {
		blanquear(gColoreado);
		colorear(gColoreado, gColoreado.ordenar(0));
	}

	public static void matula(Grafo gColoreado) {
		blanquear(gColoreado);
		colorear(gColoreado, gColoreado.ordenar(1));
	}

	public static Grafo matulaEstadistico(Grafo grafo, int cantidadDeCorridas) {
		Grafo[] grafos = new Grafo[cantidadDeCorridas];
		for (int i = 0; i < cantidadDeCorridas; i++) {
			grafos[i].batir();
			Coloreo.matula(grafos[i]);
		}

		int posMenor = 0;
		System.out.print(grafos[0].getNumCroma() + " ");
		for (int i = 1; i < cantidadDeCorridas; i++) {
			if (grafos[i].getNumCroma() < grafos[posMenor].getNumCroma())
				posMenor = i;
			System.out.print(grafos[i].getNumCroma() + " ");
		}

		System.out.println();
		System.out.println("Matula e: " + grafos[posMenor].getNumCroma());

		return grafos[posMenor];

	}

	public static Grafo powellEstadistico(Grafo grafo, int cantidadDeCorridas) {
		Grafo[] grafos = new Grafo[cantidadDeCorridas];
		for (int i = 0; i < cantidadDeCorridas; i++) {
			grafos[i].batir();
			Coloreo.powell(grafos[i]);
		}

		int posMenor = 0;
		System.out.print(grafos[0].getNumCroma() + " ");
		for (int i = 1; i < cantidadDeCorridas; i++) {
			if (grafos[i].getNumCroma() < grafos[posMenor].getNumCroma())
				posMenor = i;
			System.out.print(grafos[i].getNumCroma() + " ");
		}

		System.out.println();
		System.out.println("Powell e: " + grafos[posMenor].getNumCroma());
		return grafos[posMenor];
	}

	public static void powell(Grafo gColoreado) {
		blanquear(gColoreado);
		colorear(gColoreado, gColoreado.ordenar(2));
	}

	public static void powellMatula(Grafo gColoreado) {
		blanquear(gColoreado);
		gColoreado.ordenar(1);

	}

	private static void colorear(Grafo gColoreado, Nodo[] nodos) {
		int cantColoreados = 0;
		int cantNodos = gColoreado.getNodos().length;
		for (int i = 0; i < cantNodos && cantColoreados < cantNodos; i++) {
			int j = i;
			while (nodos[j].getColor() != 0) {
				j++;
			}
			nodos[j].setColor(i + 1);
			gColoreado.getNodo(nodos[j].getNombre()).setColor(i + 1);
			cantColoreados++;

			for (int k = j + 1; k < cantNodos; k++) {
				if (nodos[k].getColor() == 0
						&& !gColoreado.sonAdyacentes(nodos[j].getNombre(),
								nodos[k].getNombre())) {
					boolean colorear = true;
					for (int x = 0; x < k; x++) {
						if (nodos[x].getColor() == (i + 1)
								&& gColoreado.sonAdyacentes(
										nodos[k].getNombre(),
										nodos[x].getNombre())) {
							colorear = false;
						}
					}
					if (colorear) {
						nodos[k].setColor(i + 1);
						gColoreado.getNodo(nodos[k].getNombre())
								.setColor(i + 1);
						cantColoreados++;
					}
				}
			}
			gColoreado.setNumCroma(i + 1);
		}
	}
}
