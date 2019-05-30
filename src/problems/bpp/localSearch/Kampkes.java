package problems.bpp.localSearch;

import problems.bpp.Bin;
import solutions.Solution;
import problems.Evaluator;
import problems.LocalSearch;

public class Kampkes extends LocalSearch<Bin, Bin>{

	@Override
	public Solution<Bin> localOptimalSolution(Evaluator<Bin> eval, Solution<Bin> solution) {
		return solution;
	}

	@Override
	public Solution<Bin> randomSolution(Evaluator<Bin> eval, Solution<Bin> solution) {
		return solution;
	}


}
