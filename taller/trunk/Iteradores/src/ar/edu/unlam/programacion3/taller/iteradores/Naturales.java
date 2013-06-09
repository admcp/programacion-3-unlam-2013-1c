package ar.edu.unlam.programacion3.taller.iteradores;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Naturales implements Iterable<Integer> {

	private List<Integer> array;
	private int tipoIterador;
	public final static int IT_POS_PARES = 0;
	public final static int IT_POS_IMPARES = 1;
	public final static int IT_NORMAL = 3;
	
	public Naturales(int hastaDonde) {
		array = new LinkedList<Integer>();
		for(int i = 0; i < hastaDonde; i++) {
			array.add(new Integer(i));
		}
		tipoIterador = IT_NORMAL;
	}
	
	public void setIterator(int tipoIterador) {
		switch(tipoIterador) {
			case IT_NORMAL: case IT_POS_PARES: case IT_POS_IMPARES:
				this.tipoIterador = tipoIterador;
				break;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	@Override
	public Iterator<Integer> iterator() {
		if(tipoIterador == IT_POS_PARES) {
			return new PosicionParIterator();
		} else if(tipoIterador == IT_POS_IMPARES) {
			return new PosicionImparIterator();
		} else {
			return new NormalIterator();
		}
	}
		
	private class PosicionParIterator implements Iterator<Integer> {

		private int iteratorIndex = 0;

		@Override
		public boolean hasNext() {
			return iteratorIndex < array.size();
		}

		@Override
		public Integer next() {
			if(iteratorIndex < array.size()) {
				Integer returnValue = array.get(iteratorIndex);
				iteratorIndex += 2;
				return returnValue;
			} else { 
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	private class PosicionImparIterator implements Iterator<Integer> {

		private int iteratorIndex = 1;
		
		@Override
		public boolean hasNext() {
			return iteratorIndex < array.size();
		}

		@Override
		public Integer next() {
			if(iteratorIndex < array.size()) {
				Integer returnValue = array.get(iteratorIndex);
				iteratorIndex += 2;
				return returnValue;
			} else { 
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}
	
	private class NormalIterator implements Iterator<Integer> {

		private int iteratorIndex = 0;
		
		@Override
		public boolean hasNext() {
			return iteratorIndex < array.size();
		}

		@Override
		public Integer next() {
			if(iteratorIndex < array.size()) {
				Integer returnValue = array.get(iteratorIndex++);
				return returnValue;
			} else { 
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			array.remove(iteratorIndex - 1);
		}

	}
	
}
