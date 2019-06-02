package problems.bpp.localSearch;

import problems.bpp.BPP;
import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import problems.bpp.Bins;
import solutions.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import problems.Evaluator;

public class Swap extends LocalSearch{

	private Integer p, q;
	
	public Swap (Integer p, Integer q) {
		this.p = p;
		this.q = q;
	}
	
	@Override
	public Bins localOptimalSolution(BPP bpp, Bins solution) {
		Bins newSolution = (Bins) solution.clone();
		Integer n = newSolution.size();		
//		iterate over bins
		for (int i = 0; i < n; i++) {			
			for (int j = 0; j < n; j++) {
				if (i != j) {					
					Bin firstBin = newSolution.get(i);
					Bin secondBin = newSolution.get(j);
					int firstSize = firstBin.size();
					int secondSize = secondBin.size();
					int p = this.p >= firstSize ? firstSize : this.p;
					int q = this.q >= secondSize ? secondSize : this.q;
					Bin firstSubBin = firstBin.subBin(0, p);
					Bin secondSubBin = secondBin.subBin(0, q);
					Double newFirstBinWeight = firstBin.getWeight() - firstSubBin.getWeight() + secondSubBin.getWeight();
					Double newSecondBinWeight = secondBin.getWeight() - secondSubBin.getWeight() + firstSubBin.getWeight();
					if (newFirstBinWeight <= bpp.capacity && newSecondBinWeight <= bpp.capacity && 
						super.costOfRemainingWeights(newFirstBinWeight, newSecondBinWeight) < super.costOfBins(firstBin, secondBin)) {					
//						change firstBin						
						firstBin.removeAll(firstSubBin);						
						firstBin.addAll(secondSubBin);
//						change secondBin
						secondBin.removeAll(secondSubBin);						
						secondBin.addAll(firstSubBin);
//						if some there's bin empty
						if (firstBin.size() == 0) {
							newSolution.remove(firstSize);
							n = newSolution.size();
						}
						if (secondBin.size() == 0) {
							newSolution.remove(secondSize);
							n = newSolution.size();
						}
						System.out.println("Improvement done");
					}
				}				
			}			
		}		
		return newSolution;
	}

	@Override
	public Bins randomSolution(BPP eval, Bins solution) {
		Bins newSolution = (Bins) solution.clone();
		Random random = new Random();
		Integer n = newSolution.size();
		while (true) {
	//		select randomly two bins
			Integer firstBinIndex = random.nextInt(n),
				secondBinIndex = random.nextInt(n);
			while (firstBinIndex.equals(secondBinIndex))
				secondBinIndex = random.nextInt(n);
			Bin firstBin = newSolution.get(firstBinIndex),
				secondBin = newSolution.get(secondBinIndex);		
	//		create copy of bins
//			firstBin = (Bin) firstBin.clone();
//			secondBin = (Bin) secondBin.clone();
	//		save sizes
			int fBSize = firstBin.size();
			int sBSize = secondBin.size();				
	//		select randomly p items from the first bin
			int p = this.p >= fBSize ? fBSize : this.p;
			int q = this.q >= sBSize ? sBSize : this.q;
			Bin firstBinRemovedItens =  new Bin (eval, p),
					secondBinRemovedItens =  new Bin (eval, p);		
			for (int i = 0; i < p; i++) {
				firstBinRemovedItens.add(firstBin.remove(random.nextInt(firstBin.size())));
			}
	//		select randomly q items from the second bin
			for (int i = 0; i < q; i++) {
				secondBinRemovedItens.add(secondBin.remove(random.nextInt(secondBin.size())));
			}
	//		move items and check integrity
			if (secondBin.addAll(firstBinRemovedItens) && firstBin.addAll(secondBinRemovedItens)) {
				newSolution.set(firstBinIndex, firstBin);
				newSolution.set(secondBinIndex, secondBin);
//				if there's some bin empty
				if (firstBin.size() == 0) {
					newSolution.remove(firstBinIndex);
					n = newSolution.size();
				}
				if (secondBin.size() == 0) {
					newSolution.remove(secondBinIndex);
					n = newSolution.size();
				}				
				break;
			}
		}
		return newSolution;
	}


}
