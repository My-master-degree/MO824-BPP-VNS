package utils.heap;

import java.util.Comparator;

public class ArrayHeap<E> extends Heap<E>{

	private final int DEFAULT_SIZE = 10; 	
	private Object[] items;
	
	public ArrayHeap(Comparator<E> comparator) {
		super(comparator);
		this.items = new Object[DEFAULT_SIZE];
		super.n = 0;
	}
	
	public ArrayHeap(Comparator<E> comparator, int size) {
		super(comparator);
		this.items = new Object[size];
		super.n = 0;
	}

	@Override
	public void add(E item) {
		if (n == this.items.length) {
			this.rellocate();
		}
		this.n++;
		int i, fatherIndex;
		for (i = this.n - 1, fatherIndex = fatherIndex(i); i > 0 && this.items[fatherIndex] != null && 
				super.l((E) this.items[fatherIndex], item); i = fatherIndex, fatherIndex = fatherIndex(fatherIndex)) {			
			this.items[i] = this.items[fatherIndex];
			this.items[fatherIndex] = item;			
		} 
		this.items[i] = item;
			
		
	}
	
	private void rellocate() {
		Object[] aux = new Comparable[2*this.items.length];
		for (int i = 0; i < this.items.length; i++) 
			aux[i] = this.items[i];				
		this.items = aux;
	}

	private int fatherIndex(int index) {		
		return (int) Math.ceil(index/2);
	}
	
	private int leftIndex(int index) {		
		return (index*2) + 1;
	}
	
	private int rightIndex(int index) {	
		return (index*2) + 2;
	}
	
	private boolean checkRange(int index) {
		return index >= 0 && index < this.n;
	}
	
	@Override
	public String toString() {
		if (this.n > 0) {
			for (int i = 0; i < items.length; i++) {
				System.out.println(items[i]);
			}
//			floor(log_2^n)
			
//			return getHeapString();
		}
		return "";
	}
	
	private String getHeapString() {	
		if (this.n > 0) {
			String tabs = "";
			int height = (int) Math.floor(Math.log(this.n)/Math.log(2));
			int numOfTabs = ((int) Math.pow(2, height) * height) / 2;
			for (int j = 0; j < numOfTabs; j++) {
				tabs += "\t";
			}		
			String part = "";
			for (int l = 0, r = 0; l < this.n; l = leftIndex(l), r = rightIndex(r)) {				
//				tabs
//				part += tabs.substring(0, tabs.length()/l);	
//				values
				for (int j = l; j <= r && j < this.n; j++) {
//					part += this.items[j] + tabs.substring(0, tabs.length()/l);
					part += this.items[j];
				}
				part += "\n";	
//				if (!tabs.isEmpty()) 
//					tabs = tabs.substring(0, tabs.length() - 1);
			}
			return part;
		}
		return "";
		
		
//		for (int i = b; i < e && i < this.n; i++) {
//			part += this.items[i];
//			for (int j = 0; j < shift - 1; j++) {
//				part += "\t";
//			}
//		}
//		return part + "\n" + getHeapString(e, e*2 + 1, shift - 1 );		
	}

	@Override
	public E get(int i) {
		if (this.checkRange(i))
			return (E) this.items[i];
		return null;
	}

	@Override
	public E search(E ele) {		
		return null;
	}
	
}
