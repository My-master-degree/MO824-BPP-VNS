package utils.heap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import problems.bpp.BPP;
import problems.bpp.Bin;
import problems.bpp.Bins;
import solutions.Solution;

public class Util {
	public static void checkBinPackingSolution(Bins bins, BPP bpp) {
		Map<Integer, Integer> items = new HashMap<Integer, Integer> (bpp.size);
		for (int i = 0; i < bpp.size; i++) {
			items.put(i, 0);
		}
		for (Bin bin : bins) {
			Double consumedWeight = 0d;
			for (Integer item : bin) {
//				System.out.print(item+",");
				items.put(item, items.get(item) + 1);
				consumedWeight += bpp.itensWeight[item]; 
			}
//			System.out.println();
			if (consumedWeight > bpp.capacity) {
				System.out.println("A Bin has passed the capacity!");
			}
			if (consumedWeight == 0) {
				System.out.println("A Bin is empty");
			}
		}
		for (int i = 0; i < bpp.size; i++) {
			Integer r = items.get(i);
			if (r == 0) {
				System.out.println("Item "+i+" is missing!");
			} else if (r > 1) {
				System.out.println("Item "+i+" is repeated!");
			} 
		}
	}

	public static void printSolution(Bins solution) {
		for (int i = 0; i < solution.size(); i++) {
			System.out.println((i + 1) + ": "+ solution.get(i).toString());
		}
	}
	
	public static Double costOfBins(Bin... bins) {
		Double cost = 0d;
		for (Bin bin : bins) {
			cost += Math.pow(bin.getRemainingCapacity(), 2);
		}
		return cost;
	}
	
	public static Double costOfBins(Bins bins) {
		Double cost = 0d;
		for (Bin bin : bins) {
			cost += Math.pow(bin.getRemainingCapacity(), 2);
		}
		return cost;
	}

	public static Double costOfRemainingWeights(Double... binsWeights) {
		Double cost = 0d;
		for (Double binWeight : binsWeights) {
			cost += Math.pow(binWeight, 2);
		}
		return cost;
	}
	
}
