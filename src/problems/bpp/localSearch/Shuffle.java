package problems.bpp.localSearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import problems.bpp.BPP;
import problems.bpp.Bin;
import problems.bpp.Bins;

public class Shuffle extends LocalSearch {

	@Override
	public Bins localOptimalSolution(BPP eval, Bins solution) {
		// TODO Auto-generated method stub
		return solution;
	}

	@Override
	public Bins randomSolution(BPP eval, Bins solution) {
		Bins bins = new Bins(eval.itensWeight.length);
		Random random = new Random();
		List<Integer> items = new ArrayList<Integer> ();
		for (int i = 0; i < eval.itensWeight.length; i++) {
			items.add(i);
		}
		Bin currentBin = new Bin(eval);
		bins.add(currentBin);
		while(items.size() > 0) {
			Integer item = items.remove(random.nextInt(items.size()));
			if (!currentBin.add(item)) {
				currentBin = new Bin(eval);
				currentBin.add(item);
				bins.add(currentBin);
			}
		}
//		System.out.println(bins.size());
		return bins;
	}

}
