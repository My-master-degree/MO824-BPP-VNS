package problems.bpp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Collection;

import problems.Evaluator;

public class BPP implements Evaluator<Bin, Bins> {

	/**
     * Dimension of the domain.
     */
    public Integer size;

    /**
     * The bin capacity
     */
    public Double capacity;

    /**
     * The array of items weight
     */
    public Double[] itensWeight;
    
    /**
     * Lower bound
     */
    public Double lowerBound;
	
	public BPP(String filename) throws IOException {
		readInput(filename);
		Double weightSum = 0d;
		for (int i = 0; i < itensWeight.length; i++) {
			weightSum += itensWeight[0];
		}
		this.lowerBound = weightSum/this.capacity;
	}	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see problems.Evaluator#getDomainSize()
	 */
	@Override
	public Integer getDomainSize() {
		return size;
	}

	@Override
	public Double evaluate(Bins sol) {
		double cost = 0d;
		for (Bin bin : sol) {
			cost += bin.getWeight();
		}
		return cost;		
	}	
	
    protected Integer readInput(String filename) throws IOException {

        Reader fileInst = new BufferedReader(new FileReader(filename));
        StreamTokenizer stok = new StreamTokenizer(fileInst);

        stok.nextToken();
        this.size = (int) stok.nval;
        stok.nextToken();
        this.capacity = stok.nval;
        
        this.itensWeight = new Double[this.size];

        for (int i = 0; i < this.size; i++) {
            stok.nextToken();
            this.itensWeight[i] = stok.nval;
        }

        return this.size;
    }

    public Double getWeightItems (Collection<? extends Integer> items) {
    	Double weight = 0d;
    	for (Integer item : items) {
    		weight += this.itensWeight[item]; 
		}
    	return weight; 
    }
    
}
