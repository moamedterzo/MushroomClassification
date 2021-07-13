import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.core.Instances;

//classe utilizzata per la memorizzazione dei classificatori su file system
public class DataManager {

	public DataManager() {
		
		//crea directory se non esiste
		File theDir = new File("bin");

		if (!theDir.exists()) {
		    try{
		        theDir.mkdir();
		    } 
		    catch(SecurityException se){  }     
		}
	}
	
	static String filePathDecisionTree = "bin\\serializedFiles_decisionTree";
	static String filePathDecisionTree_Classifier = filePathDecisionTree + "_classifier";
	static String filePathDecisionTree_Data = filePathDecisionTree + "_data";
	static String filePathDecisionTree_Evaluation = filePathDecisionTree + "_evaluation";
	
	
	static String filePathBN = "bin\\serializedFiles_bn";
	static String filePathBN_Classifier = filePathBN + "_classifier";
	static String filePathBN_Data = filePathBN + "_data";
	static String filePathBN_Evaluation = filePathBN + "_evaluation";
	
	
	public DecisionTree getDecisionTree() throws Exception {
		
		  DecisionTree decTree = new DecisionTree();

		  decTree.classifier = (J48) getFile(filePathDecisionTree_Classifier);
		  decTree.data = (Instances)getFile(filePathDecisionTree_Data);
		  decTree.evaluation = (Evaluation)getFile(filePathDecisionTree_Evaluation);
		  
		  return decTree;
		}
	
	public void setDecisionTree(DecisionTree decTree) throws Exception {

		setFile(decTree.classifier, filePathDecisionTree_Classifier);
		setFile(decTree.data, filePathDecisionTree_Data);
		setFile(decTree.evaluation, filePathDecisionTree_Evaluation);
	}
	
	
	public BayesianNetwork getBN() throws Exception {
		
		  BayesianNetwork bn = new BayesianNetwork();

		  bn.classifier = (BayesNet) getFile(filePathBN_Classifier);
		  bn.data = (Instances)getFile(filePathBN_Data);
		  bn.evaluation = (Evaluation)getFile(filePathBN_Evaluation);
		  
		  return bn;
		}
	
	public void setBN(BayesianNetwork bn) throws Exception {

		
		setFile(bn.classifier, filePathBN_Classifier);
		setFile(bn.data, filePathBN_Data);
		setFile(bn.evaluation, filePathBN_Evaluation);
	}
	
	
	
	
	
	//memorizza su file system l'oggetto da serializzare
	private void setFile(Serializable object, String path) throws Exception {		
		
        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(object);
        objectOut.close(); 
		
	}
	
	//ottiene da file system l'oggetto serializzato
	private Object getFile(String path) throws Exception {			
		   
		  FileInputStream fileIn = new FileInputStream(path);
          ObjectInputStream objectIn = new ObjectInputStream(fileIn);

          Object obj = objectIn.readObject();
          objectIn.close();
          
          return obj;		
	}
}
