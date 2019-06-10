package metaheuristics.vns;

import problems.Evaluator;
import problems.bpp.BPP;
import problems.bpp.Bins;

public abstract class LocalSearch<E, S> {

	public abstract S localOptimalSolution(E eval, S solution);
	
	public abstract S randomSolution(E eval, S solution);
	
}
