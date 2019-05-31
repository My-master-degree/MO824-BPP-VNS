package main;

import java.io.IOException;
import java.util.Arrays;

import metaheuristics.vns.AbstractVNS;
import problems.bpp.construction.BFD;
import problems.bpp.construction.FFD;
import problems.bpp.construction.NFD;
import problems.bpp.localSearch.P_Q_Exchange;
import problems.bpp.localSearch.Swap;
import problems.bpp.solvers.VNS_BPP;

public class Main {
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
		BFD bfd = new BFD();
		FFD ffd = new FFD();
		NFD nfd = new NFD();		
		P_Q_Exchange kampkes = new P_Q_Exchange();
		
		for (int i = 0; i < bpp_instances.length; i++) {
			try {
				VNS_BPP vns_bpp = new VNS_BPP(bpp_instances[i], 1000, 180000, nfd, kampkes, Arrays.asList(new Swap(1, 2)));
				System.out.println(vns_bpp.solve().size());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}		
	}
}
