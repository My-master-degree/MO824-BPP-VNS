package problems.bpp.localSearch;

import problems.bpp.Bin;
import solutions.Solution;

import java.util.Random;

import problems.Evaluator;
import problems.LocalSearch;

public class P_Q_Exchange extends LocalSearch<Bin, Bin>{	
	
	@Override
	public Solution<Bin> localOptimalSolution(Evaluator<Bin> eval, Solution<Bin> solution) {
		return solution;
	}

	@Override
	public Solution<Bin> randomSolution(Evaluator<Bin> eval, Solution<Bin> solution) {
		return solution;
	}


}
