package algorithmSampler;

import java.util.ArrayList;

import algorithmDataScheme.GeneralDataScheme;
import algorithmDataScheme.MergeSortDataScheme;
import algorithmInterface.IAlgorithm;
import algorithmInterface.MergeSortAlgorithm;
import complexityAssessment.AlgorithmSample;

public class MergeSortSampler implements AlgorithmSampler {
	private MergeSortDataScheme algoData;

	MergeSortSampler(GeneralDataScheme algoData) {
		this.algoData = (MergeSortDataScheme)algoData;
	}

	@Override
	public SamplerResult<Integer[]> samplingProcess(IAlgorithm algoInst) {
		SamplerResult<Integer[]> samplerResult = new SamplerResult<Integer[]>();
		Integer rounds = algoData.inputs.size();
		MergeSortAlgorithm curAlgoInst = (MergeSortAlgorithm)algoInst;

		ArrayList<Integer[]> results = new ArrayList<Integer[]>();
		ArrayList<AlgorithmSample> samples = new ArrayList<AlgorithmSample>();

		for(int i = 0; i < rounds; i++) {
			Integer[] array =  (Integer[])(algoData.inputs.get(i)); // whan reading from json the inputs is double (??)
			Integer[] res = curAlgoInst.mergeSort(array);
			results.add(res);
			AlgorithmSample sample = new AlgorithmSample(algoData.inputSizes.get(i), curAlgoInst.analyzerCounters.__counters);
			samples.add(sample);
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
