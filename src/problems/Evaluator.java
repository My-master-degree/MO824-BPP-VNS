package problems;

import solutions.Solution;

/**
 * The Evaluator interface gives to a problem the required functionality to
 * obtain a mapping of a solution (n-dimensional array of elements of generic
 * type E (domain)) to a Double (image). It is a useful representation of an
 * objective function for an optimization problem.
 * 
 * @author ccavellucci, fusberti
 * @param <E>
 */
public interface Evaluator<E> {

	/**
	 * Gives the size of the problem domain. Typically this is the number of
	 * decision variables of an optimization problem.
	 * 
	 * @return the size of the problem domain.
	 */
	public abstract Integer getDomainSize();

	/**
	 * The evaluating function is responsible for returning the mapping value of
	 * a solution.
	 * 
	 * @param sol
	 *            the solution under evaluation.
	 * @return the evaluation of a solution.
	 */
	public abstract Double evaluate(Solution<E> sol);

}
