package problems.bpp;

import solutions.Solution;

public class Bins extends Solution<Bin> {
	@Override
	public Object clone() {		
		Solution<Bin> bins = new Solution<Bin>(this.size());
		for (Bin bin : this) {
			bins.add((Bin) bin.clone());
		}
		return bins;
	}
}
