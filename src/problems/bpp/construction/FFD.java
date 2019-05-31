package problems.bpp.construction;

import java.util.List;

import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import solutions.Solution;

public class FFD implements ConstructionMethod {

	@Override
	public Solution<Bin> construct(BPP_Inverse BPP_Inverse) {
//		no need to sort the items weight, because the items already are sorted in the input
//		the list size was defined based in the FFD aproximation factor
		Solution<Bin> sol = new Solution<Bin>((int) Math.ceil(1.222 * BPP_Inverse.lowerBound));
		// TODO Auto-generated method stub
		return null;
	}

}
