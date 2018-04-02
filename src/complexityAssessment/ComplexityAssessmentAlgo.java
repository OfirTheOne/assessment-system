package complexityAssessment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

import ch.obermuhlner.math.big.BigDecimalMath;

/*
	 * acceptable polynoms ::
	 * 0 = log(n) 
	 * 1 = sqrt(n)
	 * 2 = n
	 * 3 = nlog(n)
	 * 4 = n^2
	 * 5 = n^3
	 * */

public class ComplexityAssessmentAlgo {
	ArrayList<AlgorithmSample> algorithmSamples;
	static double div = 2.5;
	
	public ComplexityAssessmentAlgo(ArrayList<AlgorithmSample> algorithmSamples) {
		this.algorithmSamples = algorithmSamples;
	}


	/**
	 * @return an array containing the big O from each sample (from each counter list).
	 * 		in each item in the array there is a number from 0 to 5, that symbols the complexity the 
	 * 		assessment algorithm detected.
	 * 
	 * */
	public int[] findBigOInSample() {
		preAssessmentCheck();
		
		int[] maxOfSamples = new int[algorithmSamples.size() - 1];

		for (int i = algorithmSamples.size() - 1; i > 0; i--) {
			int mostDominent = getMax(detectFunctionOfN(algorithmSamples.get(i), algorithmSamples.get(i - 1)));
			maxOfSamples[i - 1] = mostDominent;
		}
		return maxOfSamples;
	}
	
	private void preAssessmentCheck() {
		removeUndependentCounters();
	}
	
	private void removeUndependentCounters() {
		int polyCountersSize = algorithmSamples.get(0).iterations.size();
		
		for(int i = 0; i < polyCountersSize; i++) {
			BigDecimal curValue = algorithmSamples.get(0).iterations.get(i);
			for(int j = 1 ;j < algorithmSamples.size(); j++) {
				// check if all counters at index i are equals, if so remove tham from the counters list
				BigDecimal val = algorithmSamples.get(j).iterations.get(i);
				
				if(curValue != val) {
					break;
				} else if(j == algorithmSamples.size()-1) {
					// remove all the i element from the samples
					for(int k = 0; k < algorithmSamples.size(); k++) {
						algorithmSamples.get(k).iterations.remove(i);
					}
				}
			}
		}
	}

	private int[] detectFunctionOfN(AlgorithmSample analyzerSampleA, AlgorithmSample analyzerSampleB) {
		int[] res = new int[analyzerSampleA.iterations.size()];
		for (int i = 0; i < res.length; i++) {
			BigDecimal sampleCellA = analyzerSampleA.iterations.get(i);
			BigDecimal sampleCellB = analyzerSampleB.iterations.get(i);
			int r = findPolynomOfN(analyzerSampleA.inputSize, analyzerSampleB.inputSize, sampleCellA, sampleCellB);
			res[i] = r;
		}
		return res;

	}

	private int findPolynomOfN(BigDecimal inputSizeA, BigDecimal inputSizeB, BigDecimal sampleCellA, BigDecimal sampleCellB) {

		int res = isLogN(inputSizeA, inputSizeB, sampleCellA, sampleCellB);
		res = res != -1 ? res : isSqrtN(inputSizeA, inputSizeB, sampleCellA, sampleCellB);
		res = res != -1 ? res : isN(inputSizeA, inputSizeB, sampleCellA, sampleCellB);
		res = res != -1 ? res : isNLogN(inputSizeA, inputSizeB, sampleCellA, sampleCellB);
		res = res != -1 ? res : isNtimes2(inputSizeA, inputSizeB, sampleCellA, sampleCellB);
		res = res != -1 ? res : isNtimes3(inputSizeA, inputSizeB, sampleCellA, sampleCellB);

		return res;
	}

	private static int getMax(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			if (max < arr[i]) {
				max = arr[i];
			}
		}
		return max;
	}

	private int isLogN(BigDecimal inputSizeA, BigDecimal inputSizeB, BigDecimal sampleCellA, BigDecimal sampleCellB) {
		// log(n)
		MathContext mathContext = new MathContext(50);
		BigDecimal inputRation = (BigDecimalMath.log10(inputSizeA, mathContext))
				.divide(BigDecimalMath.log10(inputSizeB, mathContext), mathContext);
		
		BigDecimal sampleRation = sampleCellA.divide(sampleCellB, mathContext);
		return compareApproximatedNumbers(inputRation, sampleRation, div) ? 0 : -1;
	}

	private int isSqrtN(BigDecimal inputSizeA, BigDecimal inputSizeB, BigDecimal sampleCellA, BigDecimal sampleCellB) {
		// sqrt(n)
		MathContext mathContext = new MathContext(50);
		BigDecimal inputRation = (BigDecimalMath.sqrt(inputSizeA, mathContext))
				.divide(BigDecimalMath.sqrt(inputSizeB, mathContext), mathContext);
		BigDecimal sampleRation = sampleCellA.divide(sampleCellB, mathContext);
		return compareApproximatedNumbers(inputRation, sampleRation, div) ? 1 : -1;
	}

	private int isN(BigDecimal inputSizeA, BigDecimal inputSizeB, BigDecimal sampleCellA, BigDecimal sampleCellB) {
		// n
		MathContext mathContext = new MathContext(50);
		BigDecimal inputRation = inputSizeA.divide(inputSizeB);
		BigDecimal sampleRation = sampleCellA.divide(sampleCellB, mathContext);
		return compareApproximatedNumbers(inputRation, sampleRation, div) ? 2 : -1;
	}

	private int isNLogN(BigDecimal inputSizeA, BigDecimal inputSizeB, BigDecimal sampleCellA, BigDecimal sampleCellB) {
		// n*log(n)
		MathContext mathContext = new MathContext(50);
		
		BigDecimal lognInputA = BigDecimalMath.log10(inputSizeA, mathContext);
		BigDecimal lognInputB = BigDecimalMath.log10(inputSizeB, mathContext);
		BigDecimal nlognInputA = inputSizeA.multiply(lognInputA, mathContext);
		BigDecimal nlognInputB = inputSizeB.multiply(lognInputB, mathContext);
		BigDecimal inputRation = nlognInputA.divide(nlognInputB, mathContext);
		BigDecimal sampleRation = sampleCellA.divide(sampleCellB, mathContext);
		return compareApproximatedNumbers(inputRation, sampleRation, div) ? 3 : -1;
	}

	private int isNtimes2(BigDecimal inputSizeA, BigDecimal inputSizeB, BigDecimal sampleCellA, BigDecimal sampleCellB) {
		// n^2
		MathContext mathContext = new MathContext(50);
		BigDecimal n2InputA = inputSizeA.multiply(inputSizeA, mathContext);
		BigDecimal n2InputB = inputSizeB.multiply(inputSizeB, mathContext);
		BigDecimal inputRation = n2InputA.divide(n2InputB, mathContext);
		BigDecimal sampleRation = sampleCellA.divide(sampleCellB, mathContext);
		return compareApproximatedNumbers(inputRation, sampleRation, div) ? 4 : -1;
	}

	private int isNtimes3(BigDecimal inputSizeA, BigDecimal inputSizeB, BigDecimal sampleCellA, BigDecimal sampleCellB) {
		// n^3
		MathContext mathContext = new MathContext(50);
		BigDecimal inputRation = ((inputSizeA.multiply(inputSizeA).multiply(inputSizeA))
				.divide((inputSizeB).multiply(inputSizeB).multiply(inputSizeB)));
		BigDecimal sampleRation = (sampleCellA).divide(sampleCellB, mathContext);
		return compareApproximatedNumbers(inputRation, sampleRation, div) ? 5 : -1;
	}

	/**
	 * approximate comparison between two numbers.
	 * 
	 * @param a
	 *            first number
	 * @param b
	 *            second number
	 * @param deviation
	 *            the maximum difference between two number that we consider
	 *            equals.
	 * @return if |a - b| < deviation
	 * 
	 *         exepmle : givan :: a = 230.5002 , b = 230.5004 , deviation =
	 *         0.005 . |a - b| = 0.0002 < 0.005 ==> a = b
	 */
	private boolean compareApproximatedNumbers(BigDecimal a, BigDecimal b, double deviation) {
		BigDecimal difference = (a.subtract(b)).abs();
		return difference.doubleValue() < deviation;
	}

}
