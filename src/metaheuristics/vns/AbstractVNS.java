/**
 * 
 */
package metaheuristics.vns;

import java.util.List;
import java.util.Random;

import problems.Evaluator;
import problems.bpp.BPP;
import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import problems.bpp.Bins;
import solutions.Solution;

/**
 * Abstract class for metaheuristic VNS (Variable Neighborhood Search). 
 * It consider a maximization problem.
 * 
 * @author matheus diógenes, cristina freitas
 * @param <E>
 *            Generic type of the element which composes the solution.
 */
public abstract class AbstractVNS<E extends Evaluator<T, S>, S extends Solution<T>, T> {
	
	public enum VNS_TYPE{
		INTENSIFICATION(1),
		DIVERSIFICATION(2),
		INTENSIFICATION_DIVERSIFICATION(3), 
		NONE(4);
		VNS_TYPE(int type){
			this.type = type;
		}
		int type;
	}
	
	protected int a;
	
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
	protected List<LocalSearch<E, S>> neighborhoodStructures;
	
	/**
	 * the objective function being optimized
	 */
	protected E ObjFunction;

	/**
	 * the best solution cost
	 */
	protected Double bestCost;

	/**
	 * the best solution
	 */
	protected S bestSol;

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
	public AbstractVNS(E objFunction, Integer iterations, Integer maxDurationInMilliseconds, 
			List<LocalSearch<E, S>> localSearchs, VNS_TYPE vns_type) {
		this.ObjFunction = objFunction;
		this.maxNumberOfIterations = iterations;
		this.maxDurationInMilliseconds = maxDurationInMilliseconds;
		this.neighborhoodStructures = localSearchs;
		this.vns_type = vns_type;
		if (vns_type.equals(VNS_TYPE.INTENSIFICATION_DIVERSIFICATION) ||vns_type.equals(VNS_TYPE.DIVERSIFICATION)) {
			k_step = this.neighborhoodStructures.size()/2;
			a = this.neighborhoodStructures.size();
		}else if (vns_type.equals(VNS_TYPE.INTENSIFICATION)) {
			a = this.neighborhoodStructures.size();
		}
	}	
	
	private int getKStep(int k, int improvements, int iterations) {
		if (vns_type.equals(VNS_TYPE.INTENSIFICATION_DIVERSIFICATION)) {
			if (rng.nextDouble() <= improvements/iterations) {
				return k; 
			}
			return k + k_step;
		}else if(vns_type.equals(VNS_TYPE.DIVERSIFICATION)) {
			return k + k_step;
		}else if(vns_type.equals(VNS_TYPE.INTENSIFICATION)) {
			return (k%a)/(a-1);
		}
		return k + 1;
	}
	
	/**
	 * The GRASP mainframe. It consists of a loop, in which each iteration goes
	 * through the constructive heuristic and local search. The best solution is
	 * returned as result.
	 * 
	 * @return The best feasible solution obtained throughout all iterations.
	 */
	public S solve() {
//		build initial solution
		bestSol = constructiveHeuristic();
		S localOptimalSolution = bestSol;
		this.ObjFunction.evaluate(localOptimalSolution);
//		set initial parameters
		long endTime = System.currentTimeMillis() + maxDurationInMilliseconds;					
		Integer improvements = 0;
		for (int i = 0, j = 1; 
			i < this.neighborhoodStructures.size() &&
			j <= maxNumberOfIterations &&
			System.currentTimeMillis() < endTime; j++) {
//			int index = i%this.neighborhoodStructures.size();
//			random solution
			S randomSolution = this.neighborhoodStructures.get(i).randomSolution(this.ObjFunction, localOptimalSolution);
			this.ObjFunction.evaluate(randomSolution);
//			local opt solution
			localOptimalSolution = this.neighborhoodStructures.get(i).localOptimalSolution(this.ObjFunction, randomSolution);
			this.ObjFunction.evaluate(localOptimalSolution);
//			check cost
			if (localOptimalSolution.cost > bestSol.cost) {				
				i = 0;
				improvements++;
				bestSol = (S) localOptimalSolution.clone();
				this.ObjFunction.evaluate(bestSol);
				if (verbose) {
					System.out.println("\t(Iter. " + j + ") BestSol = " + bestSol);
				}
			}else {
				i = getKStep(i, improvements, j);
			}
		}
		

		return bestSol;
	}	
	
	public abstract S constructiveHeuristic();

	/**
	 * @return the objFunction
	 */
	public E getObjFunction() {
		return ObjFunction;
	}
	

}
