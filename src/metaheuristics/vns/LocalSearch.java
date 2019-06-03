package metaheuristics.vns;

import problems.Evaluator;
import problems.bpp.BPP;
import problems.bpp.Bin;
import problems.bpp.Bins;
import solutions.Solution;

public abstract class LocalSearch<E> {

	public abstract Solution<E> localOptimalSolution(Evaluator<E> eval, Solution<E> solution);
	
	public abstract Solution<E> randomSolution(Evaluator<E> eval, Solution<E> solution);
	
}
