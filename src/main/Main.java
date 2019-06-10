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
import problems.bpp.construction.OneBinPerItem;
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
	public static Integer VNS_TIME_MAX_MILI_SECONDS = 1000;
	public static Integer EXACT_TIME_MAX_SECONDS = 600;
	
	public static void main(String[] args) {
		String[] bpp_instances = new String[] {
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
		OneBinPerItem oneItemPerBin = new OneBinPerItem();
		String str = "";
		BufferedWriter writer;
//		str = "VNS BFD Itensification Diversification\n";
//		System.out.println("VNS BFD Itensification");		
//		str += runVNS(bpp_instances, oneItemPerBin, AbstractVNS.VNS_TYPE.INTENSIFICATION_DIVERSIFICATION);		
//		try {
//			writer = new BufferedWriter(new FileWriter("vnd_bfd_itensification_diversification.txt"));
//			writer.write(str);		     
//		    writer.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//		none
		str = "";
		str = "VNS BFD None\n";
		System.out.println("VNS BFD None");		
		str += runVNS(bpp_instances, oneItemPerBin, AbstractVNS.VNS_TYPE.NONE);		
		try {
			writer = new BufferedWriter(new FileWriter("vnd_bfd_none.txt"));
			writer.write(str);		     
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
//		exact
//		str = "EXACT\n";
//		try {
//			str += runExacts(bpp_instances);
//		} catch (IOException | GRBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
//		try {
//			writer = new BufferedWriter(new FileWriter("exacts.txt"));
//			writer.write(str);		     
//		    writer.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	  
	}
	
	public static String runVNS(String[] instances, ConstructionMethod constructionMethod, AbstractVNS.VNS_TYPE vns_type) {		
		String str = "";
		for (int i = 0; i < instances.length; i++) {		
			System.out.println(instances[i]);
			str += instances[i] + "\n";
			try {
				BPP_Inverse bpp = new BPP_Inverse(instances[i]);
				List<LocalSearch<BPP_Inverse, Bins>> localSearchs = new ArrayList<LocalSearch<BPP_Inverse, Bins>> (); 
				localSearchs.add(new Swap(0, 1));
				localSearchs.add(new Swap(0, 2));
				localSearchs.add(new Swap(1, 1));
				localSearchs.add(new Swap(2, 1));
//				localSearchs.add(new FirstFit(bpp.size));
				VNS_BPP vns_bpp = new VNS_BPP(bpp, VNS_ITERATION_MAX_NUMBER, VNS_TIME_MAX_MILI_SECONDS, constructionMethod, localSearchs, vns_type);
				
				
				
				
							
				Bins sol = new Bins(vns_bpp.solve());
				
				str += "\tVNS cost: "+sol.size() + "\n";
				System.out.println("\tVNS cost: "+sol.size());
				
				
				Util.checkBinPackingSolution(sol, (BPP) vns_bpp.getObjFunction());
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
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
			System.out.println("\n\nObjBound = " + gurobi.model.get(GRB.DoubleAttr.ObjBound));
			str += "ObjBound = " + gurobi.model.get(GRB.DoubleAttr.ObjBound)+"\n";
			System.out.println("\n\nObjBoundC = " + gurobi.model.get(GRB.DoubleAttr.ObjBoundC));
			str += "ObjBoundC = " + gurobi.model.get(GRB.DoubleAttr.ObjBoundC)+"\n";
			System.out.println("\nTime = "+time);
			str += "Time = " + time +"\n";
//			Write file			
			gurobi.model.dispose();
			gurobi.env.dispose();
//			break;
		}
		return str;
	}
}
