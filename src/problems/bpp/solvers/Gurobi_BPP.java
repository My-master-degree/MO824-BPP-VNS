package problems.bpp.solvers;

import java.io.IOException;

import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;
import problems.bpp.BPP_Inverse;

public class Gurobi_BPP {
	public GRBEnv env;
	public GRBModel model;
	public GRBVar[] y;
	public GRBVar[][] x;
	public BPP_Inverse bpp;

	public Gurobi_BPP(String filename) throws IOException {
		this.bpp = new BPP_Inverse(filename);
	}	

	public void populateNewModel(GRBModel model) throws GRBException {
		// variables
		y = new GRBVar[bpp.size];
		x = new GRBVar[bpp.size][bpp.size];

		for (int i = 0; i < bpp.size; i++) {
			y[i] = model.addVar(0, 1, 0.0f, GRB.BINARY, "y[" + i + "]");
			for (int j = 0; j < bpp.size; j++) {
				x[i][j] = model.addVar(0, 1, 0.0f, GRB.BINARY, "w[" + i + "][" + j + "]");
			}
		}
		model.update();

		// objective functions
		GRBLinExpr obj = new GRBLinExpr();
		for (int i = 0; i < bpp.size; i++) {
			obj.addTerm(1, y[i]);
		}

		// constraints
//		\sum_{j=1}^n w_j x_{ij} \leq c y_i (i \in \{1, ..., N\})
		// 	for each bin
		for (int i = 0; i < bpp.size; i++) {			
			GRBLinExpr leftSidedExpr = new GRBLinExpr();
			//	for each item
			for (int j = 0; j < bpp.size; j++) {
				leftSidedExpr.addTerm(bpp.itensWeight[j], x[i][j]);
			}
			GRBLinExpr rightSidedExpr = new GRBLinExpr();
			rightSidedExpr.addTerm(bpp.capacity, y[i]);
			model.addConstr(leftSidedExpr, GRB.LESS_EQUAL, rightSidedExpr, "bin_"+i+"_capacity");
		}
//		\sum_{j=1}^n x_{ij} \eq 1 (j \in \{1, ..., N\})		
		// for each item
		for (int j = 0; j < bpp.size; j++) {
			GRBLinExpr leftSidedExpr = new GRBLinExpr();
			// 	for each bin
			for (int i = 0; i < bpp.size; i++) {
				leftSidedExpr.addTerm(1, x[i][j]);
			}
			model.addConstr(leftSidedExpr, GRB.EQUAL, 1, "item_"+j);
		}		
					
					
		
		
		model.setObjective(obj);
		model.update();

		// maximization objective function
		model.set(GRB.IntAttr.ModelSense, GRB.MINIMIZE);

	}
}
