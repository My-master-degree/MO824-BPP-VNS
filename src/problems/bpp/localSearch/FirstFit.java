package problems.bpp.localSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import metaheuristics.vns.LocalSearch;
import problems.bpp.BPP;
import problems.bpp.Bin;
import problems.bpp.Bins;
import problems.bpp.construction.FFD;
import utils.heap.Util;

public class FirstFit extends LocalSearch<BPP, Bins> {

	private int size;
	
	public FirstFit(int size) {
		this.size = size;
	}
	
	@Override
	public Bins localOptimalSolution(BPP eval, Bins solution) {
//		copy items
		List<Integer> items = new ArrayList<Integer> ();
		for (Bin bin : solution) {
			items.addAll(bin);
		}		
//		first fit
		int upperBoundAttempt = (int) Math.ceil(1.23 * eval.lowerBound);
		Bins sol = new Bins(upperBoundAttempt);
		for (int i = 0; i < eval.size; i++) {
			boolean inserted = false;
			for (int j = 0; j < sol.size(); j++) {
				Bin bin = sol.get(j);
				if (bin.add(i)) {
					inserted = true;
					break;
				}
			}
			if (!inserted) {						
				Bin newBin = new Bin(eval);
				newBin.add(i);
				sol.add(newBin);
			}
		}	
		if (Util.costOfBins(sol) < Util.costOfBins(solution))	{
//			System.out.println("FF with: "+sol.size());
			return sol;
		}else {
//			System.out.println("FF with: "+solution.size());
			return solution;
		}
	}

	@Override
	public Bins randomSolution(BPP eval, Bins solution) {
		Random random = new Random();
		int n = solution.size();
		Bins newSolution = (Bins) solution.clone();		
//		define the numbers of bins to be used
		int numberOfBins = random.nextInt(n);
//		get bins randomly
		Bins bins = new Bins(numberOfBins);
		for (int i = 0; i < numberOfBins; i++) {
			bins.add(newSolution.remove(random.nextInt(newSolution.size())));
		}
//		copy items
		List<Integer> items = new ArrayList<Integer> ();
		for (Bin bin : bins) {
			items.addAll(bin);
		}		
//		first fit
		int upperBoundAttempt = (int) Math.ceil(1.23 * eval.lowerBound);
		Bins partialSolution = new Bins(upperBoundAttempt);
		for (int i = 0; i < items.size(); i++) {
			boolean inserted = false;
			for (int j = 0; j < partialSolution.size(); j++) {
				Bin bin = partialSolution.get(j);
				if (bin.add(items.get(i))) {
					inserted = true;
					break;
				}
			}
			if (!inserted) {						
				Bin newBin = new Bin(eval);
				newBin.add(items.get(i));
				partialSolution.add(newBin);
			}
		}			
		newSolution.addAll(partialSolution);
		return newSolution;
	}

}
