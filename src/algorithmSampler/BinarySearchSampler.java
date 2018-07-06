package algorithmSampler;

import java.util.ArrayList;

import algorithmDataScheme.GeneralDataScheme;
import algorithmDataScheme.BinarySearchDataScheme;
import algorithmInterface.IAlgorithm;
import algorithmInterface.BinarySearchAlgorithm;
import complexityAssessment.AlgorithmSample;

public class BinarySearchSampler implements AlgorithmSampler {
	private BinarySearchDataScheme algoData;

	BinarySearchSampler(GeneralDataScheme algoData) {
		this.algoData = (BinarySearchDataScheme)algoData;
	}

	@Override
	public SamplerResult<?> samplingProcess(IAlgorithm algoInst) {

		Integer rounds = algoData.inputs.size(); // rounds of sampling
		BinarySearchAlgorithm curAlgoInst = (BinarySearchAlgorithm)algoInst; // casting algorithm instance
		
		// initializing storing objects
		ArrayList<Integer> results = new ArrayList<Integer>();
		ArrayList<AlgorithmSample> samples = new ArrayList<AlgorithmSample>();
		SamplerResult<Integer> samplerResult = new SamplerResult<Integer>();
		
		for(int i = 0; i < rounds; i++) {
			
			// - 1 - setting the algorithm parameters
			int size = algoData.inputs.get(i).length;
			Integer[] array = new Integer[size]; // whan reading from json the inputs is double (??)
			System.arraycopy(algoData.inputs.get(i), 0, array, 0, array.length);
			Integer value = algoData.valuesToFind.get(i);
			
			// - 2 - calling the algorithm
			Integer res = curAlgoInst.binarySearch(array, value);
			
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
}
