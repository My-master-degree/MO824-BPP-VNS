package problems.bpp;

import java.util.ArrayList;
import java.util.Collection;

public class Bin extends ArrayList<Integer>{
	
	private BPP bpp;
	private Double weight;	
	
	public Double getWeight() {
		return weight;
	}

	public Bin (BPP bpp) {
		super();
		this.bpp = bpp;
		weight = 0d;
	}
	
	public Bin (BPP bpp, Collection<? extends Integer> items) {		
		this(bpp);
		this.addAll(items);
	}
	
	@Override
	public boolean add (Integer item) {
		if (this.weight + bpp.itensWeight[item] <= this.bpp.capacity) {
			super.add(item);
			this.weight += bpp.itensWeight[item];
			return true;
		} 	
		return false;
	}
	
	@Override
	public boolean addAll(Collection<? extends Integer> items) {
		double itemsWeight = bpp.getWeightItems(items);
		if (this.weight + itemsWeight <= this.bpp.capacity) {
			super.addAll(items);
			this.weight += itemsWeight;
			return true;
		} 	
		return false;
	}
		
	@Override
	public boolean remove (Object item) {
		Integer _item = (Integer) item;
		if (super.remove(_item)) {		
			this.weight -= bpp.itensWeight[_item];
			return true;
		}
		return false;
	}	
	
	public Bin subBin(int b, int e) {
		return new Bin(this.bpp, this.subList(b, e));
	}
	
	public Double getRemainingCapacity() {
		return this.bpp.capacity - this.weight;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (Integer item : this) 
			str += item + ",";		
		return str;
	}
	
}
