package problems.bpp;

import java.io.IOException;

public class BPP_Inverse extends BPP {
	
	public BPP_Inverse(String filename) throws IOException {
		super(filename);
	}

	@Override
	public Double evaluate(Bins sol) {

		return sol.cost = -super.evaluate(sol);

	}

}
