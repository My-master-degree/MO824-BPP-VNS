package problems.bpp.construction;

import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import solutions.Solution;

public class NFD implements ConstructionMethod {

	@Override
	public Solution<Bin> construct(BPP_Inverse BPP_Inverse) {
//		no need to sort the items weight, because the items already are sorted in the input
//		the list size was defined based in the BFD aproximation factor
		Solution<Bin> solution = new Solution<Bin>((int) Math.ceil(1.691 * BPP_Inverse.lowerBound));
		Bin currentBin = new Bin(BPP_Inverse);
		for (int i = 0; i < BPP_Inverse.itensWeight.length; i++) {
			if (!currentBin.add(i)) {
				solution.add(currentBin);
				currentBin = new Bin(BPP_Inverse);
			}
		}
		return solution;
	}

}
