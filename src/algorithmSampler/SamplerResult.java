package algorithmSampler;

import java.util.ArrayList;

import complexityAssessment.AlgorithmSample;

public class SamplerResult<R> {
	public ArrayList<AlgorithmSample> samples; 
	public ArrayList<R> results; // the output comeback from the algo
	public int complexity; // assessment resulte
}
