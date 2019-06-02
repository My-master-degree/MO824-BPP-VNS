package problems.bpp.construction;

import java.util.Comparator;

import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import problems.bpp.Bins;
import solutions.Solution;
import utils.heap.ArrayHeap;
import utils.heap.Heap;

public class BFD implements ConstructionMethod {

	@Override
	public Bins construct(BPP_Inverse BPP_Inverse) {
//		no need to sort the items weight, because the items already are sorted in the input
//		Heap<Bin> binHeap = new ArrayHeap<Bin> (new Comparator<Bin>() {
//			@Override
//			public int compare(Bin b1, Bin b2) {
//				Double b1RemainingCapacity = b1.getRemainingCapacity();
//				Double b2RemainingCapacity = b1.getRemainingCapacity();
//				if (b1RemainingCapacity > b2RemainingCapacity)
//					return -1;
//				if (b1RemainingCapacity < b2RemainingCapacity)
//					return 1;
//				return 0;
//			}
//		}); 
//		the list size was defined based in the BFD approximation factor
		int upperBoundAttempt = (int) Math.ceil(1.23 * BPP_Inverse.lowerBound);
		Bins sol = new Bins(upperBoundAttempt);
		sol.add(new Bin(BPP_Inverse));
		for (int i = 0; i < BPP_Inverse.size; i++) {
			Bin bestBin = sol.get(0);
			for (int j = 1; j < sol.size(); j++) {
				Bin bin = sol.get(j);
				if (bin.getRemainingCapacity() >= BPP_Inverse.itensWeight[i] && 
					bin.getRemainingCapacity() < bestBin.getRemainingCapacity()) {
					bestBin = bin; 
				}
			}
			if (bestBin.getRemainingCapacity() >= BPP_Inverse.itensWeight[i]) {
				bestBin.add(i);
			}else {
				Bin newBin = new Bin(BPP_Inverse);
				newBin.add(i);
				sol.add(newBin);
			}
		}
		return sol;
	}

}
