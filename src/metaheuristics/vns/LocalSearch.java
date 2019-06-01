package metaheuristics.vns;

import problems.Evaluator;
import problems.bpp.BPP;
import problems.bpp.Bin;
import problems.bpp.Bins;
import solutions.Solution;

public abstract class LocalSearch<E> {

	public abstract E localOptimalSolution(Evaluator<E> eval, S solution);
	
	public abstract S randomSolution(E eval, S solution);
	
}
