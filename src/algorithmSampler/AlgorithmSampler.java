package algorithmSampler;

import algorithmInterface.IAlgorithm;

public interface AlgorithmSampler {
	//public SamplerResult<?> getResult();
	
	public SamplerResult<?> samplingProcess (IAlgorithm algoInst);
}

