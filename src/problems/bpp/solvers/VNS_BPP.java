package problems.bpp.solvers;

import java.io.IOException;
import java.util.List;

import metaheuristics.vns.AbstractVNS;
import metaheuristics.vns.LocalSearch;
import metaheuristics.vns.AbstractVNS.VNS_TYPE;
import problems.bpp.BPP;
import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import problems.bpp.construction.ConstructionMethod;
import solutions.Solution;

public class VNS_BPP extends AbstractVNS<Bin> {
	
	private ConstructionMethod constructionMethod;
	private LocalSearch<Bin> localSearch;
	
	public VNS_BPP(String filename, Integer iterations, Integer maxDurationInMilliseconds, 
			ConstructionMethod constructionMethod, LocalSearch<Bin> localSearch, 
			List<LocalSearch<Bin>> neighborhoodStructures, VNS_TYPE vns_type) throws IOException {		
		super(new BPP_Inverse(filename), iterations, maxDurationInMilliseconds, neighborhoodStructures, vns_type);
		this.constructionMethod = constructionMethod;
		this.localSearch = localSearch;
	}
	
	public VNS_BPP(BPP eval, Integer iterations, Integer maxDurationInMilliseconds, 
			ConstructionMethod constructionMethod, LocalSearch<Bin> localSearch, 
			List<LocalSearch<Bin>> neighborhoodStructures, VNS_TYPE vns_type) throws IOException {		
		super(eval, iterations, maxDurationInMilliseconds, neighborhoodStructures, vns_type);
		this.constructionMethod = constructionMethod;
		this.localSearch = localSearch;
	}

	@Override
	public Solution<Bin> random(Solution<Bin> solution) {
		return localSearch.randomSolution((BPP_Inverse) super.ObjFunction, solution);
	}

	@Override
	public Solution<Bin> constructiveHeuristic() {
		return constructionMethod.construct((BPP_Inverse) super.ObjFunction);	
	}

}
