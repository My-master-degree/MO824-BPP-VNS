/**
 * 
 */
package metaheuristics.vns;

import java.util.List;
import java.util.Random;

import problems.Evaluator;
import solutions.Solution;

/**
 * Abstract class for metaheuristic GRASP (Greedy Randomized Adaptive Search
 * Procedure). It consider a maximization problem.
 * 
 * @author ccavellucci, fusberti
 * @param <E>
 *            Generic type of the element which composes the solution.
 */
public abstract class AbstractVNS<E> {
	
	public enum VNS_TYPE{
		INTENSIFICATION(1),
		DIVERSIFICATION(2);
		VNS_TYPE(int type){
			this.type = type;
		}
		int type;
	}
	
	protected VNS_TYPE vns_type;
	
	/**
	 * flag that indicates whether the code should print more information on
	 * screen
	 */
	public static boolean verbose = true;

	/**
	 * a random number generator
	 */
	static Random rng = new Random(0);
	
	/**
	 * the neighborhood structure list
	 */
	protected List<LocalSearch<E>> neighborhoodStructures;
	
	/**
	 * the objective function being optimized
	 */
	protected Evaluator<E> ObjFunction;

	/**
	 * the best solution cost
	 */
	protected Double bestCost;

	/**
	 * the best solution
	 */
	protected Solution<E> bestSol;

	/**
	 * the number of iterations the VNS main loop executes.
	 */
	protected Integer maxNumberOfIterations;
	
	/**
	 * the time in microseconds the VNS main loop executes.
	 */
	protected Integer maxDurationInMilliseconds;
	
	/**
	 * the time in microseconds the VNS main loop executes.
	 */
	protected Integer k_step;	

	/**
	 * Constructor for the AbstractGRASP class.
	 * 
	 * @param objFunction
	 *            The objective function being maximized.
	 * @param alpha
	 *            The GRASP greediness-randomness parameter (within the range
	 *            [0,1])
	 * @param iterations
	 *            The number of iterations which the GRASP will be executed.
	 */
	public AbstractVNS(Evaluator<E> objFunction, Integer iterations, Integer maxDurationInMilliseconds, 
			List<LocalSearch<E>> localSearchs, VNS_TYPE vns_type) {
		this.ObjFunction = objFunction;
		this.maxNumberOfIterations = iterations;
		this.maxDurationInMilliseconds = maxDurationInMilliseconds;
		this.neighborhoodStructures = localSearchs;
		this.vns_type = vns_type;
		if (vns_type.equals(VNS_TYPE.INTENSIFICATION))
			k_step = 1;
		else
			k_step = 2;
	}	
	
	/**
	 * The GRASP mainframe. It consists of a loop, in which each iteration goes
	 * through the constructive heuristic and local search. The best solution is
	 * returned as result.
	 * 
	 * @return The best feasible solution obtained throughout all iterations.
	 */
	public Solution<E> solve() {
		bestSol = constructiveHeuristic();
		if (verbose) {
//			System.out.println("\tConstruction:" + bestSol.size());
		}
		long endTime = System.currentTimeMillis() + maxDurationInMilliseconds;	
		Solution<E> localOptimalSolution = bestSol;
		this.ObjFunction.evaluate(localOptimalSolution);
		int j = 0;
		for (int i = 0; 
			i < this.neighborhoodStructures.size() && 
			System.currentTimeMillis() < endTime; i += k_step, j++) {			
//			get random solution
//			Solution<E> randomSolution = this.random(this.bestSol);
//			this.ObjFunction.evaluate(randomSolution);			
			
//			get local optimal solution
//			Solution<E> localOptimalSolution = localSearch(randomSolution);
//			Solution<E> localOptimalSolution = this.neighborhoodStructures.get(i).localOptimalSolution(this.ObjFunction, this.bestSol);
//			for (int k = 0; k < this.neighborhoodStructures.size(); k++) {
			localOptimalSolution = this.neighborhoodStructures.get(i).localOptimalSolution(this.ObjFunction, localOptimalSolution);	
//			}
			this.ObjFunction.evaluate(localOptimalSolution);
//			check cost
			if (localOptimalSolution.cost < bestSol.cost) {
				bestSol = new Solution<E>(localOptimalSolution);
				this.ObjFunction.evaluate(bestSol);
				if (verbose) {
//					System.out.println("\t(Iter. " + j + ") BestSol = " + bestSol);
				}
			}		
//			break;
		}
		
		
//		
		for (;j < maxNumberOfIterations && System.currentTimeMillis() < endTime; j++) {
			Solution<E> randomSolution = this.random(localOptimalSolution);	
			this.ObjFunction.evaluate(randomSolution);
			for (int i = 0; i < this.neighborhoodStructures.size(); i++) {				
//				check cost
				randomSolution = this.neighborhoodStructures.get(i).localOptimalSolution(this.ObjFunction, randomSolution);
				if (randomSolution.cost < bestSol.cost) {
					bestSol = new Solution<E>(randomSolution);
					this.ObjFunction.evaluate(bestSol);
					if (verbose) {
//						System.out.println("\t(Iter. " + j + ") BestSol = " + bestSol);
					}
				}		
			}
		}
		

		return bestSol;
	}	
	
	/**
	 * The GRASP local search phase is responsible for repeatedly applying a
	 * neighborhood operation while the solution is getting improved, i.e.,
	 * until a local optimum is attained.
	 * 
	 * @return An local optimum solution.
	 */
	public abstract Solution<E> random(Solution<E> solution);	
	
	/**
	 * The GRASP constructive heuristic, which is responsible for building a
	 * feasible solution by selecting in a greedy-random fashion, candidate
	 * elements to enter the solution.
	 * 
	 * @return A feasible solution to the problem being maximized.
	 */
	public abstract Solution<E> constructiveHeuristic();

	/**
	 * @return the objFunction
	 */
	public Evaluator<E> getObjFunction() {
		return ObjFunction;
	}
	

}
