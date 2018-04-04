package algorithmSampler;

import algorithmDataScheme.GeneralDataScheme;

public class AlgoSamplerFactory {
	// aid = algorithm id
	public static AlgorithmSampler getSampler(int aid, GeneralDataScheme algoData) {
		AlgorithmSampler sampler = null;
		switch (aid) {
		case 1:
			sampler = new FindMaxInArraySampler(algoData);
			break;
		case 2:
			sampler = new BubbleSortSampler(algoData);
			break;
		case 3:
			sampler = new MergeSortSampler(algoData);
			break;
		default:
			break;
		}
		
		return sampler;
	}
}
