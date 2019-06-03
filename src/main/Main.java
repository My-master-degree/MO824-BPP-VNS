package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBModel;
import metaheuristics.vns.AbstractVNS;
import metaheuristics.vns.LocalSearch;
import problems.Evaluator;
import problems.bpp.BPP;
import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import problems.bpp.Bins;
import problems.bpp.construction.BFD;
import problems.bpp.construction.ConstructionMethod;
import problems.bpp.construction.FFD;
import problems.bpp.construction.NFD;
import problems.bpp.localSearch.FirstFit;
import problems.bpp.localSearch.Rellocate;
import problems.bpp.localSearch.Shuffle;
import problems.bpp.localSearch.Swap;
import problems.bpp.solvers.Gurobi_BPP;
import problems.bpp.solvers.VNS_BPP;
import solutions.Solution;
import utils.heap.ArrayHeap;
import utils.heap.Heap;
import utils.heap.Util;

public class Main {
	
	public static Integer VNS_ITERATION_MAX_NUMBER = Integer.MAX_VALUE;
	public static Integer VNS_TIME_MAX_MILI_SECONDS = 1800000;
	public static Integer EXACT_TIME_MAX_SECONDS = 1800;
	
	public static void main(String[] args) {
	
		String[] bpp_instances = new String[] {
//			"./bpp_instances/instance0a.bpp",
			"./bpp_instances/instance0.bpp",
			"./bpp_instances/instance1.bpp",
			"./bpp_instances/instance2.bpp",
			"./bpp_instances/instance3.bpp",
			"./bpp_instances/instance4.bpp",
			"./bpp_instances/instance5.bpp",	
			"./bpp_instances/instance6.bpp",
			"./bpp_instances/instance7.bpp",		
			"./bpp_instances/instance8.bpp",			
			"./bpp_instances/instance9.bpp",										
		};
//		heuristic
		BFD bfd = new BFD();
		String str = "";
		str = "VNS BFD Itensification\n";
		System.out.println("VNS BFD Itensification");		
		str += runVNS(bpp_instances, bfd, AbstractVNS.VNS_TYPE.INTENSIFICATION);
		str += "VNS BFD Diversification\n";
		System.out.println("VNS BFD Diversification");		
		str += runVNS(bpp_instances, bfd, AbstractVNS.VNS_TYPE.DIVERSIFICATION);
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("heuristics.txt"));
			writer.write(str);		     
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
//		exact
		str = "EXACT\n";
		try {
			str += runExacts(bpp_instances);
		} catch (IOException | GRBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer = new BufferedWriter(new FileWriter("exacts.txt"));
			writer.write(str);		     
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	  
		
	}
	
	public static String runVNS(String[] instances, ConstructionMethod constructionMethod, AbstractVNS.VNS_TYPE vns_type) {		
		String str = "";
		for (int i = 0; i < instances.length; i++) {		
			System.out.println(instances[i]);
			str += instances[i] + "\n";
			try {
				
				List<LocalSearch<Bin>> neighborhoodConverted = new ArrayList<LocalSearch<Bin>>();
				
				LocalSearch<Bin> localSearch = new LocalSearch<Bin>() {
					@Override
					public Solution<Bin> localOptimalSolution(Evaluator<Bin> eval, Solution<Bin> solution) {						
						return new Shuffle().localOptimalSolution((BPP) eval, (Bins) solution);
					}

					@Override
					public Solution<Bin> randomSolution(Evaluator<Bin> eval, Solution<Bin> solution) {
						return new Shuffle().randomSolution((BPP) eval, new Bins(solution));
					}
				};
				VNS_BPP vns_bpp = new VNS_BPP(instances[i], VNS_ITERATION_MAX_NUMBER, VNS_TIME_MAX_MILI_SECONDS, constructionMethod, 
						localSearch, neighborhoodConverted, vns_type);
				
				
				List<problems.bpp.localSearch.LocalSearch> neighborhoodStructures = new ArrayList<problems.bpp.localSearch.LocalSearch>(
					Arrays.asList(
							new Swap(0, 1), new Swap(0, 2), new Swap(1, 1), new Swap(2, 1), new FirstFit(vns_bpp.getObjFunction().getDomainSize())
							)
					); 				
				for (problems.bpp.localSearch.LocalSearch neighborhoodStructure : neighborhoodStructures) {
					neighborhoodConverted.add(new LocalSearch<Bin>() {				
						@Override
						public Solution<Bin> randomSolution(Evaluator<Bin> eval, Solution<Bin> solution) {					
							return (Solution<Bin>) neighborhoodStructure.randomSolution((BPP) eval, new Bins(solution));
						}
						
						@Override
						public Solution<Bin> localOptimalSolution(Evaluator<Bin> eval, Solution<Bin> solution) {
							return (Solution<Bin>) neighborhoodStructure.localOptimalSolution((BPP) eval, (Bins) solution);
						}
					});
				}
				
							
				Bins sol = new Bins(vns_bpp.solve());
				
				str += "\tVNS cost: "+vns_bpp.solve().size() + "\n";
				System.out.println("\tVNS cost: "+vns_bpp.solve().size());
				
				
				Util.checkBinPackingSolution(sol, (BPP) vns_bpp.getObjFunction());
				
//				Util.checkBinPackingSolution(new Shuffle().randomSolution((BPP_Inverse) vns_bpp.getObjFunction(), 
//					nfd.construct((BPP_Inverse) vns_bpp.getObjFunction())), (BPP) vns_bpp.getObjFunction());
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
//			break;
			
		}	
		return str;
	}
	
	public static String runExacts(String[] instances) throws IOException, GRBException {
		String str = "";
		for (int i = 0; i < instances.length; i++) {
			str += instances[i] + "\n";
			// instance name
			Gurobi_BPP gurobi = new Gurobi_BPP(instances[i]);
//			gurobi.env = new GRBEnv("mip1.log");
			gurobi.env = new GRBEnv();
			gurobi.model = new GRBModel(gurobi.env);
			// execution time in seconds 
			gurobi.model.getEnv().set(GRB.DoubleParam.TimeLimit, EXACT_TIME_MAX_SECONDS);
			// generate the model
			gurobi.populateNewModel(gurobi.model);
			// write model to file
//			gurobi.model.write("model.lp");
			long time = System.currentTimeMillis();
			gurobi.model.optimize();
			time = System.currentTimeMillis() - time;
			System.out.println("\n\nZ* = " + gurobi.model.get(GRB.DoubleAttr.ObjVal));
			str += "Z* = " + gurobi.model.get(GRB.DoubleAttr.ObjVal)+"\n";
			System.out.println("\nTime = "+time);
			str += "Time = " + time +"\n";
//			System.out.print("Y = [");
//			str += "Y = [";
//			for (int j = 0; j < gurobi.bpp.size; j++) {
//	//				          System.out.print(gurobi.x[j].get(GRB.DoubleAttr.X) + ", ");
//		          str += gurobi.y[j].get(GRB.DoubleAttr.X) + ", ";
//			}			
//			System.out.println("]");
//			str += "]\n";
//			System.out.print("X = [");
//			str += "X = [";
//			for (int j = 0; j < gurobi.bpp.size; j++) {
//				System.out.print("[");
//				str += "[";
//				for (int k = 0; k < gurobi.bpp.size; k++) {
//					System.out.print(gurobi.x[j][k].get(GRB.DoubleAttr.X) + ", ");
//					str += gurobi.x[j][k].get(GRB.DoubleAttr.X) + ", ";
//				}	
//				System.out.println("]");
//				str += "]\n";
//			}			
//			System.out.print("]");
//			str += "]";
//			Write file			
			gurobi.model.dispose();
			gurobi.env.dispose();
//			break;
		}
		return str;
	}
}
