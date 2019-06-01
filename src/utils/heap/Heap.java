package utils.heap;

import java.util.Comparator;

public abstract class Heap<E> {
		
	private final Comparator<E> comparator;
	protected int n;
	
	public Heap(Comparator<E> comparator) {		
		this.comparator = comparator;
	}
	
	public abstract void add(E item);		
	
	public abstract E get(int i);
	
	protected boolean l(E a, E b) {
		return this.comparator.compare(a, b) == -1;
	}
	
	protected boolean g(E a, E b) {
		return this.comparator.compare(a, b) == 1;
	}
	
}
