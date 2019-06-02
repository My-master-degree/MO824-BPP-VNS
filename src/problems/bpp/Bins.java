package problems.bpp;

import solutions.Solution;

public class Bins extends Solution<Bin> {
	@Override
	public Object clone() {		
		Bins bins = new Bins(this.size());
		for (Bin bin : this) {
			bins.add((Bin) bin.clone());
		}
		return bins;
	}
	
	public Bins(int size) {
		super(size);
	}
}
