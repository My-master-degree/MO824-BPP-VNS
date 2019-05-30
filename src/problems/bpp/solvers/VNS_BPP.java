package problems.bpp.solvers;

import java.io.IOException;
import java.util.Arrays;

import metaheuristics.vns.AbstractVNS;
import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import problems.bpp.construction.ConstructionMethod;
import problems.LocalSearch;
import solutions.Solution;

public class VNS_BPP extends AbstractVNS<Bin> {
	
	private ConstructionMethod constructionMethod;
	private LocalSearch<Bin, Bin> localSearch;
	
	public VNS_BPP(String filename, Integer iterations, Integer maxDurationInMilliseconds, 
			ConstructionMethod constructionMethod, LocalSearch<Bin, Bin> localSearch) throws IOException {
		super(new BPP_Inverse(filename), iterations, maxDurationInMilliseconds, Arrays.asList(localSearch));
		this.constructionMethod = constructionMethod;
		this.localSearch = localSearch;
	}

	@Override
	public Solution<Bin> localSearch(Solution<Bin> solution) {
		return localSearch.localOptimalSolution((BPP_Inverse) super.ObjFunction, solution);
	}

	@Override
	public Solution<Bin> constructiveHeuristic() {
		return constructionMethod.construct((BPP_Inverse) super.ObjFunction);	
	}

}
