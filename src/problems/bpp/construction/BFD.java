package problems.bpp.construction;

import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import solutions.Solution;

public class BFD implements ConstructionMethod {

	@Override
	public Solution<Bin> construct(BPP_Inverse BPP_Inverse) {
		Solution<Bin> sol = new Solution<Bin>();
		for (int i = 0; i < BPP_Inverse.size; i++) {
			sol.add(new Bin(BPP_Inverse, i));
		}
		return sol;
	}

}
