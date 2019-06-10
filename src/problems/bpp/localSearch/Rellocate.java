package problems.bpp.localSearch;

import java.util.Random;

import metaheuristics.vns.LocalSearch;
import problems.bpp.BPP;
import problems.bpp.Bin;
import problems.bpp.Bins;
import utils.heap.Util;

public class Rellocate extends LocalSearch<BPP, Bins> {
	
	private Swap swap;	
	
	public Rellocate(int p) {
		swap = new Swap(p, 0);			
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
					int p = swap.getP() >= firstSize ? firstSize : swap.getP();					
					Bin firstSubBin = firstBin.subBin(0, p);					
					Double newFirstBinWeight = firstBin.getWeight() - firstSubBin.getWeight(),
						newSecondBinWeight = secondBin.getWeight() + firstSubBin.getWeight();
					if (newSecondBinWeight <= bpp.capacity && 
						Util.costOfRemainingWeights(newFirstBinWeight, newSecondBinWeight) < Util.costOfBins(firstBin, secondBin)) {					
//						change firstBin						
						firstBin.removeAll(firstSubBin);
//						change secondBin
						secondBin.addAll(firstSubBin);	
//						if some there's bin empty
						if (firstBin.size() == 0) {
							newSolution.remove(firstSize);
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
			Integer firstBinIndex = random.nextInt(n);
			Integer secondBinIndex = random.nextInt(n);
			while (firstBinIndex.equals(secondBinIndex))
				secondBinIndex = random.nextInt(n);
			Bin firstBin = (Bin) newSolution.get(firstBinIndex).clone(),
					secondBin = (Bin) newSolution.get(secondBinIndex).clone();	
	//		save sizes
			int fBSize = firstBin.size();			
	//		select randomly p items from the first bin
			int p = this.swap.getP() >= fBSize ? fBSize : this.swap.getP();
			Bin firstBinRemovedItens =  new Bin (eval, p);		
			for (int i = 0; i < p; i++) {
				firstBinRemovedItens.add(firstBin.remove(random.nextInt(firstBin.size())));
			}
	//		move items and check integrity
			if (secondBin.addAll(firstBinRemovedItens)) {
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
