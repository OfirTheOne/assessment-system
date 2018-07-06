package algorithmSampler;

import java.util.ArrayList;

import algorithmDataScheme.FindMaxInArrayDataScheme;
import algorithmDataScheme.GeneralDataScheme;
import algorithmInterface.FindMaxInArrayAlgorithm;
import algorithmInterface.IAlgorithm;
import complexityAssessment.AlgorithmSample;

public class FindMaxInArraySampler implements AlgorithmSampler {
	private FindMaxInArrayDataScheme algoData;
	
	FindMaxInArraySampler(GeneralDataScheme algoDataScheme) {
		this.algoData = (FindMaxInArrayDataScheme)algoDataScheme;
	}
	
	@Override
	public SamplerResult<Integer> samplingProcess(IAlgorithm algoInst) {
		SamplerResult<Integer> samplerResult = new SamplerResult<Integer>();

		Integer rounds = algoData.inputs.size();
		FindMaxInArrayAlgorithm curAlgoInst = (FindMaxInArrayAlgorithm)algoInst;
		
		ArrayList<Integer> results = new ArrayList<Integer>();
		ArrayList<AlgorithmSample> samples = new ArrayList<AlgorithmSample>();
		
		for(int i = 0; i < rounds; i++) {
			int size = algoData.inputs.get(i).length;
			Integer[] array = new Integer[size]; // whan reading from json the inputs is double (??)
			System.arraycopy(algoData.inputs.get(i), 0, array, 0, array.length);
			
			Integer res = curAlgoInst.findMaxInArray(array);
			results.add(res);
			AlgorithmSample sample = new AlgorithmSample(algoData.inputSizes.get(i), curAlgoInst.analyzerCounters.__counters);
			samples.add(sample);
			curAlgoInst.analyzerCounters.flushCounters();
		}
		samplerResult.samples = samples;
		samplerResult.results = results;
		return samplerResult;
		
	}


}
