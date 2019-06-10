package problems;

import solutions.Solution;

public interface Evaluator<E, S> {

	public abstract Integer getDomainSize();

	public abstract Double evaluate(S sol);

}
