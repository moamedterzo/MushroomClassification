import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.j48.ClassifierSplitModel;
import weka.classifiers.trees.j48.ClassifierTree;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

//Classe utilizzata per la classificazione delle istanze tramite albero di decisione
public class DecisionTree {

	
	protected J48 classifier; //Weka classificatore
	protected Evaluation evaluation; //valutazione del classificatore
	protected Instances data; //elenco delle istanze
	
	
	//effettua training partendo dal percorso del file csv
	public void trainFromData(String pathTrainingFile) throws Exception {
      		
		DataSource source = new DataSource(pathTrainingFile);		
		data = source.getDataSet();
		data.setClassIndex(0);		

		classifier = new J48();
		
		//opzioni
		classifier.setConfidenceFactor((float) 0.25); //fatture di confidenza per effettuare il pruning
		classifier.setMinNumObj(2); //numero minimo di istanze per nodo
		
		classifier.buildClassifier(data);	
		
		//memorizzo ed effettuo valutazione
		evaluation = new Evaluation(data);
		evaluation.crossValidateModel(classifier, data, 10, new Random(1));
	}
	
	//restituisce la descrizione testuale del classificatore
	public String getDescription() {
			
		StringBuilder sb = new StringBuilder();

		sb.append("\n== MODEL ==\n");
		sb.append(classifier.toString());

		sb.append("\n== EVALUATION ==\n");
		sb.append(evaluation.toSummaryString());
		
		return sb.toString();
	}
	

	
	//classifica una nuova istanza e restituisce la probabilità dello stesso
	public double classifyInstance(Mushroom mushroom) throws Exception {
		
		Instance mushroomIst = mushroom.toInstance();
		
		double classValue = classifier.classifyInstance(mushroomIst);
		
		mushroom._class = data.classAttribute().value((int)classValue);
		
		//ottengo probabilità della classe
		ClassifierTree m_root = getClassifierRoot();
		
		Method method = m_root.getClass().getSuperclass().getDeclaredMethod("getProbs", int.class, Instance.class, double.class );
		method.setAccessible(true);
		return (double)method.invoke(m_root, (int)classValue, mushroomIst, (double)1);
		
	}
	
	//restituisce la stringa della proprietà del fungo utilizzata per la comparazione del primo nodo dell'albero di decisione
	public String getFirstNodeFieldForTree(Mushroom m) throws Exception {
		   
		ClassifierTree m_root = getClassifierRoot();
				
		ClassifierSplitModel m_localModel = m_root.getLocalModel();
				
		return m_localModel.leftSide(data);		
	 }
	
	//restituisce il classificatore interno utilizzato dal classificatore Weka
	private ClassifierTree getClassifierRoot() throws Exception {
		
		Field myField = classifier.getClass().getDeclaredField("m_root"); 
		myField.setAccessible(true);			
		return (ClassifierTree) myField.get(classifier);
	}
}
