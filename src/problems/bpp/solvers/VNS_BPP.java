package problems.bpp.solvers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import metaheuristics.vns.AbstractVNS;
import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import problems.bpp.construction.ConstructionMethod;
import problems.bpp.localSearch.LocalSearch;
import solutions.Solution;

public class VNS_BPP extends AbstractVNS<Bin> {
	
	private ConstructionMethod constructionMethod;
	private LocalSearch localSearch;
	
	public VNS_BPP(String filename, Integer iterations, Integer maxDurationInMilliseconds, 
			ConstructionMethod constructionMethod, LocalSearch localSearch, 
			List<LocalSearch> neighborhoodStructures) throws IOException {
		super(new BPP_Inverse(filename), iterations, maxDurationInMilliseconds, neighborhoodStructures);
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
