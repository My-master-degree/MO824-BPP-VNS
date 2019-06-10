package problems.bpp.solvers;

import java.io.IOException;
import java.util.List;

import metaheuristics.vns.AbstractVNS;
import metaheuristics.vns.LocalSearch;
import problems.bpp.BPP;
import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import problems.bpp.Bins;
import problems.bpp.construction.ConstructionMethod;
import solutions.Solution;

public class VNS_BPP extends AbstractVNS<BPP, Bins, Bin> {
	
	private ConstructionMethod constructionMethod;
	
	public VNS_BPP(String filename, Integer iterations, Integer maxDurationInMilliseconds, 
			ConstructionMethod constructionMethod, 
			List<LocalSearch<BPP, Bins>> neighborhoodStructures, VNS_TYPE vns_type) throws IOException {		
		super(new BPP_Inverse(filename), iterations, maxDurationInMilliseconds, neighborhoodStructures, vns_type);
		this.constructionMethod = constructionMethod;
	}
	
	public VNS_BPP(BPP eval, Integer iterations, Integer maxDurationInMilliseconds, 
			ConstructionMethod constructionMethod, 
			List<LocalSearch<BPP, Bins>> neighborhoodStructures, VNS_TYPE vns_type) throws IOException {		
		super(eval, iterations, maxDurationInMilliseconds, neighborhoodStructures, vns_type);
		this.constructionMethod = constructionMethod;
	}

	@Override
	public Bins constructiveHeuristic() {
		return constructionMethod.construct((BPP_Inverse) super.ObjFunction);	
	}

}
