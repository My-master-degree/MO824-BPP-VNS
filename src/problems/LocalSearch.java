package problems;

import solutions.Solution;

public abstract class LocalSearch<S, E> {

	public abstract Solution<S> localOptimalSolution(Evaluator<E> eval, Solution<S> solution);
	
	public abstract Solution<S> randomSolution(Evaluator<E> eval, Solution<S> solution);
}
