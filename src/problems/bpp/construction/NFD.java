package problems.bpp.construction;

import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import problems.bpp.Bins;
import solutions.Solution;

public class NFD implements ConstructionMethod {

	@Override
	public Bins construct(BPP_Inverse BPP_Inverse) {
//		no need to sort the items weight, because the items already are sorted in the input
//		the list size was defined based in the BFD aproximation factor
		Bins solution = new Bins((int) Math.ceil(1.691 * BPP_Inverse.lowerBound));
		Bin currentBin = new Bin(BPP_Inverse);
		solution.add(currentBin);
		for (int i = 0; i < BPP_Inverse.size; i++) {
			if (!currentBin.add(i)) {	
				currentBin = new Bin(BPP_Inverse);
				currentBin.add(i);
				solution.add(currentBin);			
			}
		}
		return solution;
	}

}
