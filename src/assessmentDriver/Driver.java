package assessmentDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import com.google.gson.Gson;

import algorithmDataScheme.BubbleSortDataScheme;
import algorithmDataScheme.FindMaxInArrayDataScheme;
import algorithmDataScheme.GeneralDataScheme;
import algorithmDataScheme.MergeSortDataScheme;
import algorithmInterface.IAlgorithm;
import algorithmSampler.AlgoSamplerFactory;
import algorithmSampler.AlgorithmSampler;
import algorithmSampler.SamplerResult;
import complexityAssessment.AlgorithmSample;
import complexityAssessment.ComplexityAssessmentAlgo;

public class Driver {
	public static void main(String[] args) {

		// extract parameters
		int aid = Integer.parseInt(args[0]);
		String classPath = args[1]; //"C:\\javaCode\\java-project-backend\\res\\output\\";
		String className = args[2]; //"injectedCode.AlgoImpl"; 
		String jsonPath = args[3]; //"C:\\javaCode\\java-project-backend\\res\\q-json\\samples\\q01-sample.json"; 

		// load algo class
		Class<?> algoClass = getClassObjectFromClassFile(new File(classPath), className);

		// get QuestionData from json file
		GeneralDataScheme algoData = readDataFromJson(jsonPath, aid);

		
		// factor the relevant Sampler class
		AlgorithmSampler algoSampler = AlgoSamplerFactory.getSampler(aid, algoData);
		
		// create an instance of the algorithm object
		IAlgorithm algoInst = null;
		try {
			algoInst = (IAlgorithm)(algoClass.newInstance());
		} catch (IllegalAccessException|InstantiationException e) { e.printStackTrace(); }
		
		// start the sampling process and get the samplerResult
		SamplerResult<?> result = algoSampler.samplingProcess(algoInst);
		result.complexity = assessmentHandler(result.samples);
		Gson gson = new Gson();
        String jsonResult = gson.toJson(result, result.getClass());
        System.out.println(jsonResult);
		
	}

	private static int assessmentHandler(ArrayList<AlgorithmSample> samples) {
		ComplexityAssessmentAlgo algo = new ComplexityAssessmentAlgo(samples);
		int[] result = algo.findBigOInSample();
		// the last one contains the assessment return from the largest sample and the most dominent
		return result[result.length-1]; 
		
	}
	
	private static Class<?> getClassObjectFromClassFile(File classPath, String className) {

		URLClassLoader loader = null;
		try {
			URL classUrl = classPath.toURI().toURL();
			loader = new URLClassLoader(new URL[]{
					classUrl
			});
			Class<?> cls = loader.loadClass(className);
			return cls;
		} catch (ClassNotFoundException | MalformedURLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(loader != null ) {
					loader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static GeneralDataScheme readDataFromJson(String jsonPath, int aid) {
		BufferedReader br = null;
		GeneralDataScheme algoData = null;
		try {
			// load the QuestionData json file and conv to object
			br = new BufferedReader(new FileReader(jsonPath)); // read
			switch (aid) {
			case 1:
				algoData = new Gson().fromJson(br, FindMaxInArrayDataScheme.class);  // parse
				break;
			case 2:
				algoData = new Gson().fromJson(br, BubbleSortDataScheme.class);  // parse
				break;
			case 3:
				algoData = new Gson().fromJson(br, MergeSortDataScheme.class);  // parse
				break;
			default:
				break;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(br != null) {
				try { br.close(); } catch (IOException e) { e.printStackTrace(); }
			}
		}
		return algoData;
	}


}

