package problems.bpp.construction;

import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import solutions.Solution;

public class BFD implements ConstructionMethod {

	@Override
	public Solution<Bin> construct(BPP_Inverse BPP_Inverse) {
//		no need to sort the items weight, because the items already are sorted in the input
//		the list size was defined based in the BFD aproximation factor
		Solution<Bin> sol = new Solution<Bin>((int) Math.ceil(1.222 * BPP_Inverse.lowerBound));
		for (int i = 0; i < BPP_Inverse.size; i++) {
			sol.add(new Bin(BPP_Inverse, i));
		}
		return sol;
	}

}
