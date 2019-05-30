package problems.bpp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.List;

import problems.Evaluator;
import solutions.Solution;

/**
 * A quadractic binary function (QBF) is a function that can be expressed as the
 * sum of quadractic terms: f(x) = \sum{i,j}{a_{ij}*x_i*x_j}. In matricial form
 * a QBF can be expressed as f(x) = x'.A.x 
 * The problem of minimizing a QBF is NP-hard [1], even when no constraints
 * are considered.
 * 
 * [1] Kochenberger, et al. The unconstrained binary quadratic programming
 * problem: a survey. J Comb Optim (2014) 28:58–81. DOI
 * 10.1007/s10878-014-9734-0.
 * 
 * @author ccavellucci, fusberti
 *
 */
public class BPP_Inverse extends BPP {
	
	public BPP_Inverse(String filename) throws IOException {
		super(filename);
	}

	/**
	 * {@inheritDoc} In the case of a GVRP, the evaluation correspond to
	 * computing the sum of arcs used in the solution.
	 * 
	 * @return The evaluation of the GVRP.
	 */
	@Override
	public Double evaluate(Solution<Bin> sol) {

		return sol.cost = -super.evaluate(sol);

	}

}
