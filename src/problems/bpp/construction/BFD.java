package problems.bpp.construction;

import java.util.Comparator;

import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import solutions.Solution;
import utils.heap.ArrayHeap;
import utils.heap.Heap;

public class BFD implements ConstructionMethod {

	@Override
	public Solution<Bin> construct(BPP_Inverse BPP_Inverse) {
//		no need to sort the items weight, because the items already are sorted in the input
		
		
		
		Heap<Bin> binHeap = new ArrayHeap<Bin> (new Comparator<Bin>() {

			@Override
			public int compare(Bin b1, Bin b2) {
				Double b1RemainingCapacity = b1.getRemainingCapacity();
				Double b2RemainingCapacity = b1.getRemainingCapacity();
				if (b1RemainingCapacity > b2RemainingCapacity)
					return -1;
				if (b1RemainingCapacity < b2RemainingCapacity)
					return 1;
				return 0;
			}
		}); 
		
		
//		the list size was defined based in the BFD aproximation factor
		int upperBoundAttempt = (int) Math.ceil(1.23 * BPP_Inverse.lowerBound);
		
		
		
		
		Solution<Bin> sol = new Solution<Bin>();
		
		binHeap.add(new Bin(BPP_Inverse));
		for (int i = 0; i < BPP_Inverse.size; i++) {
			
			sol.add(new Bin(BPP_Inverse, i));
		}
		return sol;
	}

}
