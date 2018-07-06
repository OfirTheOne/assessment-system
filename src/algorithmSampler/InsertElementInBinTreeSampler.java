package algorithmSampler;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import algorithmDataScheme.FindMaxInArrayDataScheme;
import algorithmDataScheme.GeneralDataScheme;
import algorithmDataScheme.InsertElementInBinTreeDataScheme;
import algorithmInterface.FindMaxInArrayAlgorithm;
import algorithmInterface.IAlgorithm;
import algorithmInterface.InsertElementInBinTreeAlgorithm;
import complexityAssessment.AlgorithmSample;
import sideClasses.binaryTree.BinaryTree;
import sideClasses.binaryTree.Node;

public class InsertElementInBinTreeSampler implements AlgorithmSampler {
	private InsertElementInBinTreeDataScheme algoData;

	InsertElementInBinTreeSampler(GeneralDataScheme algoDataScheme) {
		this.algoData = (InsertElementInBinTreeDataScheme) algoDataScheme;
	}

	@Override
	public SamplerResult<?> samplingProcess(IAlgorithm algoInst) {
		SamplerResult<String> samplerResult = new SamplerResult<String>();

		Integer rounds = algoData.input_object_paths.size();
		InsertElementInBinTreeAlgorithm curAlgoInst = (InsertElementInBinTreeAlgorithm)algoInst;
		
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<AlgorithmSample> samples = new ArrayList<AlgorithmSample>();
		
		for(int i = 0; i < rounds; i++) {
			String treeObjectPath = algoData.input_object_paths.get(i);
			Node rootNode = (Node)ReadObjectFromFile(treeObjectPath);
			Integer value = algoData.input_values.get(i);
			
			curAlgoInst.insertElementInBinTree(rootNode, value);
			WriteObjectToFile(rootNode, algoData.output_object_paths.get(i));
			results.add(algoData.output_object_paths.get(i));
			AlgorithmSample sample = new AlgorithmSample(algoData.inputSizes.get(i), curAlgoInst.analyzerCounters.__counters);
			samples.add(sample);
			curAlgoInst.analyzerCounters.flushCounters();
		}
		samplerResult.samples = samples;
		samplerResult.results = results;
		return samplerResult;
	}

	private Object ReadObjectFromFile(String filepath) {
		try {
			ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filepath));
			Object obj = objectIn.readObject();
			System.out.println("The Object has been read from the file");
			objectIn.close();
			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	private void WriteObjectToFile(Object obj, String filepath) {
		
	}

}
