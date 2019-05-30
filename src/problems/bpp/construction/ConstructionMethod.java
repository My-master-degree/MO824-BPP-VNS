package problems.bpp.construction;

import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import solutions.Solution;

public interface ConstructionMethod {
	public Solution<Bin> construct(BPP_Inverse eval);
}
