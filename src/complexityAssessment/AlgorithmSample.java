package complexityAssessment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class AlgorithmSample {
	
		public BigDecimal inputSize;
		public ArrayList<BigDecimal> iterations;
		public AlgorithmSample(BigDecimal inputSize, ArrayList<BigDecimal> iterations) {
			this.inputSize = inputSize;
			this.iterations = iterations;
		}
		
		public AlgorithmSample(int inputSize, ArrayList<BigDecimal> iterations) {
			this(BigDecimal.valueOf(inputSize), iterations);
		}
		
		@Override
		public String toString() {
			return inputSize.toString() +" : "+ iterations;
		}
	
}
