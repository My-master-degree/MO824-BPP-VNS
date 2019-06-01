package problems.bpp.localSearch;

import problems.bpp.BPP;
import problems.bpp.Bin;
import problems.bpp.Bins;
import solutions.Solution;

public abstract class LocalSearch {

	public abstract Solution<Bin> localOptimalSolution(BPP eval, Bins solution);
	
	public abstract Solution<Bin> randomSolution(BPP eval, Bins solution);
	
	public Double costOfBins(Bin... bins) {
		Double cost = 0d;
		for (Bin bin : bins) {
			cost += Math.pow(bin.getRemainingCapacity(), 2);
		}
		return cost;
	}

	public Double costOfRemainingWeights(Double... binsWeights) {
		Double cost = 0d;
		for (Double binWeight : binsWeights) {
			cost += Math.pow(binWeight, 2);
		}
		return cost;
	}
}
