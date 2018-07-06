package algorithmSampler;

import java.util.ArrayList;

import algorithmDataScheme.BubbleSortDataScheme;
import algorithmDataScheme.GeneralDataScheme;
import algorithmInterface.BubbleSortAlgorithm;
import algorithmInterface.IAlgorithm;
import complexityAssessment.AlgorithmSample;

public class BubbleSortSampler implements AlgorithmSampler {
	private BubbleSortDataScheme algoData;

	BubbleSortSampler(GeneralDataScheme algoData) {
		this.algoData = (BubbleSortDataScheme)algoData;
	}

	@Override
	public SamplerResult<Integer[]> samplingProcess(IAlgorithm algoInst) {
		
		Integer rounds = algoData.inputs.size(); // rounds of sampling
		BubbleSortAlgorithm curAlgoInst = (BubbleSortAlgorithm)algoInst; // casting algorithm instance

		// initializing storing objects
		ArrayList<Integer[]> results = new ArrayList<Integer[]>();
		ArrayList<AlgorithmSample> samples = new ArrayList<AlgorithmSample>();
		SamplerResult<Integer[]> samplerResult = new SamplerResult<Integer[]>();

		for(int i = 0; i < rounds; i++) {
			
			// - 1 - setting the algorithm parameters
			Integer[] array =  (Integer[])(algoData.inputs.get(i)); // whan reading from json the inputs is double (??)
			
			// - 2 - calling the algorithm
			Integer[] res = curAlgoInst.bubbleSort(array);
			
			// - 3 - storing result
			results.add(res);
			
			// - 4 - storing sample 
			AlgorithmSample sample = new AlgorithmSample(algoData.inputSizes.get(i), curAlgoInst.analyzerCounters.__counters);
			samples.add(sample);
			
			// - 5 - empting iteretions counters 
			curAlgoInst.analyzerCounters.flushCounters();
		}
		samplerResult.samples = samples;
		samplerResult.results = results;
		return samplerResult;
	}
/*
	@Override 
	public boolean assertionProcess() {
		boolean isCorrect = false;
		ArrayList<Integer[]> actual = samplerResult.results;
		ArrayList<Integer[]> expect = algoData.expects;
		try{
			for(int i = 0; i < actual.size(); i++) {
				Integer[] have = actual.get(i);
				Integer[] wont = expect.get(i);
				isCorrect = false;
				assertThat(have).isEqualTo(new Integer[]{12, 34});
				isCorrect = true;
			}
		} catch (Throwable  e) {
			System.out.println("yiyo");
			System.out.println(e);

		} finally {
			System.out.println(isCorrect);
			samplerResult.isCorrect = isCorrect;
			return isCorrect;
		}

	}
	@Override
	public SamplerResult<Integer[]> getResult() {
		return samplerResult;
	}
 */

}
