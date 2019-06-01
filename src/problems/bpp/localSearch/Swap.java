package problems.bpp.localSearch;

import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import solutions.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import problems.Evaluator;
import problems.LocalSearch;

public class Swap extends LocalSearch<Bin, Bin>{

	private Integer p, q;
	
	public Swap (Integer p, Integer q) {
		this.p = p;
		this.q = q;
	}
	
	@Override
	public Solution<Bin> localOptimalSolution(Evaluator<Bin> eval, Solution<Bin> solution) {
		Random random = new Random();
		Integer n = solution.size();		
//		iterate over bins
		for (int i = 0; i < n; i++) {			
			for (int j = 0; j < n; j++) {
				if (i != j) {
					int p = this.p >=  
					Bin firstBin = solution.get(i);
					Bin secondBin = solution.get(j);
					secondBin.addAll(firstBin.subList(0, p));
				}				
			}			
		}		
						
//		create copy of bins
		firstBin = (Bin) firstBin.clone();
		secondBin = (Bin) secondBin.clone();
//		save sizes
		int fBSize = firstBin.size();
		int sBSize = secondBin.size();				
//		select randomly min{p, firstBin.size()} items from the first bin
		List<Integer> firstBinRemovedItens =  new ArrayList<Integer> (p),
				secondBinRemovedItens =  new ArrayList<Integer> (p);		
		while (firstBin.size() > 0 && fBSize - firstBin.size() < p) {
			firstBinRemovedItens.add(firstBin.remove(random.nextInt(firstBin.size())));
		}
//		select randomly min{q, secondBin.size()} items from the second bin
		while (secondBin.size() > 0 && sBSize - secondBin.size() < q) {
			secondBinRemovedItens.add(secondBin.remove(random.nextInt(secondBin.size())));
		}
//		move items and check integrity
		if (secondBin.addAll(firstBinRemovedItens) && firstBin.addAll(secondBinRemovedItens)) {
			solution.set(firstBinIndex, firstBin);
			solution.set(secondBinIndex, secondBin);
		}
		return solution;
	}

	@Override
	public Solution<Bin> randomSolution(Evaluator<Bin> eval, Solution<Bin> solution) {
		Random random = new Random();
		Integer n = solution.size();
//		select randomly two bins
		Integer firstBinIndex = random.nextInt(n),
			secondBinIndex = random.nextInt(n);
		while (firstBinIndex.equals(secondBinIndex))
			secondBinIndex = random.nextInt(n);
		Bin firstBin = solution.get(firstBinIndex),
			secondBin = solution.get(secondBinIndex);		
//		create copy of bins
		firstBin = (Bin) firstBin.clone();
		secondBin = (Bin) secondBin.clone();
//		save sizes
		int fBSize = firstBin.size();
		int sBSize = secondBin.size();				
//		select randomly min{p, firstBin.size()} items from the first bin
		List<Integer> firstBinRemovedItens =  new ArrayList<Integer> (p),
				secondBinRemovedItens =  new ArrayList<Integer> (p);		
		while (firstBin.size() > 0 && fBSize - firstBin.size() < p) {
			firstBinRemovedItens.add(firstBin.remove(random.nextInt(firstBin.size())));
		}
//		select randomly min{q, secondBin.size()} items from the second bin
		while (secondBin.size() > 0 && sBSize - secondBin.size() < q) {
			secondBinRemovedItens.add(secondBin.remove(random.nextInt(secondBin.size())));
		}
//		move items and check integrity
		if (secondBin.addAll(firstBinRemovedItens) && firstBin.addAll(secondBinRemovedItens)) {
			solution.set(firstBinIndex, firstBin);
			solution.set(secondBinIndex, secondBin);
		}
		return solution;
	}


}
