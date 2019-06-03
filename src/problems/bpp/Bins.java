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

	public Bins(Solution<Bin> solution) {
		for (Bin bin : solution) {
			super.add(bin);
		}
	}
}
