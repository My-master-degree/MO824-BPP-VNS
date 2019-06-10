package problems.bpp.construction;

import java.util.Arrays;

import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import problems.bpp.Bins;

public class OneBinPerItem implements ConstructionMethod {

	@Override
	public Bins construct(BPP_Inverse eval) {
		Bins bins = new Bins(eval.size);		
		for (int i = 0; i < eval.size; i++) {
			bins.add(new Bin(eval, Arrays.asList(i)));
		}
		return bins;
	}

}
