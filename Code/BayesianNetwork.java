import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.net.estimate.SimpleEstimator;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

//Classe utilizzata per la classificazione dei funghi tramite rete bayesiana
public class BayesianNetwork {
	
	protected BayesNet classifier; //Weka classificatore
	protected Evaluation evaluation; //valutazione del classificatore
	protected Instances data; //elenco delle istanze
	
	//effettua training partendo dal percorso del file csv
	public void trainFromData(String pathTrainingFile) throws Exception {
		 
		DataSource source = new DataSource(pathTrainingFile);		 
		data = source.getDataSet();
		data.setClassIndex(0);

		classifier = new BayesNet();
		
		//opzioni del classificatore
		SimpleEstimator estimator = new SimpleEstimator(); //stimatore che valuta le probabilità direttamente dai dati
		estimator.setAlpha(0.5); //probabilità iniziale
		classifier.setEstimator(estimator);
				
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
		
		//probabilità
		return classifier.distributionForInstance(mushroomIst)[(int)classValue];
	}
}
