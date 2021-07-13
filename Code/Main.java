import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Main {
	
	//probabilità minima per affermare che la classificazione tramite albero di decisione sia affidabile
	static int ConfidentProbability = 95; 


	public static void main(String[] args) {		
		
		final Scanner input = new Scanner(System.in);
		
		//benvenuto
		System.out.println("Welcome to the Mushroom knoledge program.\n");
		 		 		    
	    while (true) {
	        System.out.println("Select an option:");
	        System.out.println("1. Enter to the mushroom classification");
	        System.out.println("2. Are you bad? Find out if you have eaten a poisous mushroom");
	        System.out.println("3. Exit\n");
	        System.out.print("Enter choice:");

	        switch (input.nextInt()) {
	            case 1:
	            	mushroomClassification(input);
	                break;
	            case 2:
	            	setLogicProoferForUser(input);
	                break;
	                
	            case 3:
	                System.out.println("Exit from program");
	                System.exit(0);
	               break;
	               
	            default:
	                System.out.println("Specify a correct value");
	        }
	    }
	}
	
	//sezione della classificazione dei funghi
	static void mushroomClassification(Scanner input) {		
		
		DataManager dataManager = new DataManager();			
		DecisionTree tree = new DecisionTree();
		BayesianNetwork BN = new BayesianNetwork();
	
		try {
			 tree = dataManager.getDecisionTree();
			 BN = dataManager.getBN();
		} catch (Exception e) {

			//se non esiste su file albero e BN, bisogna effettuare tassativamente il training dei dati
			trainFromData(dataManager ,tree ,BN, input, false );
		} 
		    
		int choice = -1;
		
	    while (choice != 5) {
	        System.out.println("Select an option:");
	        System.out.println("1. Use training set");
	        System.out.println("2. Display Decision Tree");
	        System.out.println("3. Display Bayesian Network");
	        System.out.println("4. Classify new instance");
	        System.out.println("5. Exit\n");
	        System.out.print("Enter choice:");

	        choice = input.nextInt();
	        switch (choice) {
	            case 1:
	            	trainFromData(dataManager ,tree ,BN, input, true);
	                break;
	                
	            case 2:
	               System.out.println(tree.getDescription());
	                break;
	                
	            case 3:
	              System.out.println(BN.getDescription());
	               break;
	               
	            case 4:
	            	classifyInstance(tree ,BN, input);
	               break;    
	               
	            case 5:
	                System.out.println("Exit from mushroom classification");	                
	                break;
	                
	            default:
	                System.out.println("Specify a correct value");
	        }
	    }
	}
	
	//metodo che effettua il training dei classificatori dai dati
	static void trainFromData(DataManager dataManager ,DecisionTree tree ,BayesianNetwork BN, final Scanner input, boolean canExit ) {
		
		System.out.println("Please specify a csv file, in order to train the system to recognize poisonus and edibles mushrooms"+ (canExit ? " (specify -1 in order to exit)" : "")  +": ");
		
		while(true) {
			
			try {
				
				String pathTrainingFile = input.nextLine();
				
				if(canExit && pathTrainingFile.equals("-1"))
					break;
				
				if(!pathTrainingFile.isEmpty()) {
					
					//controllo se esiste il file
					File f = new File(pathTrainingFile);
					if(f.exists() && !f.isDirectory()) { 
						
						//effettuo training di albero e rete bayesiana
						tree.trainFromData(pathTrainingFile);
						BN.trainFromData(pathTrainingFile);
						
						//memorizzo su file
						dataManager.setDecisionTree(tree);
						dataManager.setBN(BN);
						
						break;
					}
				}
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	//metodo che effettua la classificazione di un nuovo fungo
	static void classifyInstance(DecisionTree tree ,BayesianNetwork BN, Scanner input) {
				
		Mushroom m = new Mushroom();		
		
		while(true) {
			System.out.println("Select a property to insert:\n");
			
	        System.out.println("1. cap shape");
	        System.out.println("2. cap surface");
	        System.out.println("3. cap color");
	        System.out.println("4. bruises");
	        System.out.println("5. odor");
	        
	        System.out.println("6. gill attachment");
	        System.out.println("7. gill spacing");
	        System.out.println("8. gill size");
	        System.out.println("9. gill color");
	        System.out.println("10. stalk shape");
	        
	        System.out.println("11. stalk root");
	        System.out.println("12. stalk surface above ring");
	        System.out.println("13. stalk surface below ring");
	        System.out.println("14. stalk color above ring");
	        System.out.println("15. stalk color below ring");
	        
	        System.out.println("16. veil type");
	        System.out.println("17. veil color");
	        System.out.println("18. ring number");
	        System.out.println("19. ring type");
	        System.out.println("20. spore print color");
	        
	        System.out.println("21. population");
	        System.out.println("22. habitat\n");	
	        
	        System.out.println("-1. Begin classification\n");
	        
	        
	        int choice = 0;
	        
	        try {
	        	choice= input.nextInt();
	        }
	        catch(Exception e) {
	        	input.nextLine();
	        }
	        
	        if(choice == -1)
	        	break; //esci dal ciclo, incomincia la classificazione
	        
	        else if(choice < 1 || choice > 22) {
	        	System.out.println("Specify a correct value\n");
	        }
	        else {
	        	       	
	 	        System.out.println("Insert a value:\n");	 	        
	 	        
	 	      
	 	        switch (choice) {	        
	 	        	
	 	        case 1:	        	
	 	        	
	 	        	m.cap_shape = getValueForMushroom(Mushroom.values_cap_shape, input);	 	        	
	 	        	
	 	        	break;
	 	        case 2:	        	
	 	        	
	 	        	m.cap_surface = getValueForMushroom(Mushroom.values_cap_surface, input);	 	        	
	 	        	
	 	        	break;
	 	        case 3:	        	
	 	        	
	 	        	m.cap_color = getValueForMushroom(Mushroom.values_cap_color, input);	 	        	
	 	        	
	 	        	break;
	 	        case 4:	        	
	 	        	
	 	        	m.bruises = getValueForMushroom(Mushroom.values_bruises, input);	 	        	
	 	        	
	 	        	break;
	 	        case 5:	        	
	 	    	   
	 	        	m.odor = getValueForMushroom(Mushroom.values_odor, input);	 	        	
	 	        	
	 	        	break;
	 	        case 6:	        	
 	        	
 	        	    m.gill_attachment = getValueForMushroom(Mushroom.values_gill_attachment, input);	 	        	
 	        	
 	        	    break;
	 	        case 7:	        	
	        	
	 	    	 	m.gill_spacing = getValueForMushroom(Mushroom.values_gill_spacing, input);	 	        	
	        	
	 	    	 	break;
	 	        case 8:	        	
	 	        	
	 	        	m.gill_size = getValueForMushroom(Mushroom.values_gill_size, input);	 	        	
	 	        	
	 	        	break;
	 	        case 9:	        	
	 	        	
	 	        	m.gill_color = getValueForMushroom(Mushroom.values_gill_color, input);	 	        	
	 	        	
	 	        	break;
	 	        case 10:	        	
	 	    	   
	 	        	m.stalk_shape = getValueForMushroom(Mushroom.values_stalk_shape, input);	 	        	
	 	        	
	 	        	break;
	 	        case 11:	        	
	        	
	        	    m.stalk_root = getValueForMushroom(Mushroom.values_stalk_root, input);	 	        	
	        	
	        	    break;
	 	        case 12:	        	
	        	
	 	    	 	m.stalk_surface_above_ring = getValueForMushroom(Mushroom.values_stalk_surface_above_ring, input);	 	        	
	        	
	 	    	 	break;
	 	        case 13:	        	
		        	
	 	    	 	m.stalk_surface_below_ring = getValueForMushroom(Mushroom.values_stalk_surface_below_ring, input);	 	        	
	        	
	 	    	 	break;
	 	        case 14:	        	
	 	        	
	 	        	m.stalk_color_above_ring = getValueForMushroom(Mushroom.values_stalk_color_above_ring, input);	 	        	
	 	        	
	 	        	break;
	 	        case 15:	        	
	 	        	
	 	        	m.stalk_color_below_ring = getValueForMushroom(Mushroom.values_stalk_color_below_ring, input);	 	        	
	 	        	
	 	        	break;
	 	        case 16:	        	
	 	    	   
	 	        	m.veil_type = getValueForMushroom(Mushroom.values_veil_type, input);	 	        	
	 	        	
	 	        	break;
	 	        case 17:	        	
	        	
	        	    m.veil_color = getValueForMushroom(Mushroom.values_veil_color, input);	 	        	
	        	
	        	    break;
	 	        case 18:	        	
	        	
	 	    	 	m.ring_number = getValueForMushroom(Mushroom.values_ring_number, input);	 	        	
	        	
	 	    	 	break;
	 	       case 19:	        	
		        	
	 	    	 	m.ring_type = getValueForMushroom(Mushroom.values_ring_type, input);	 	        	
	        	
	 	    	 	break;
	 	        case 20:	        	
	 	        	
	 	        	m.spore_print_color = getValueForMushroom(Mushroom.values_spore_print_color, input);	 	        	
	 	        	
	 	        	break;
	 	        case 21:	        	
	 	        	
	 	        	m.population = getValueForMushroom(Mushroom.values_population, input);	 	        	
	 	        	
	 	        	break;
	 	        case 22:	        	
	 	    	   
	 	        	m.habitat = getValueForMushroom(Mushroom.values_habitat, input);	 	        	
	 	        	
	 	        	break;
	 	        	
	 	       default:
	                System.out.println("Specify a correct value");
	 	 
	 	        }
	        }
	       
		}
					
		
		try {
			
			System.out.println(m.toString() + "\n");

			double probability = 0;
			
			
			//se non sono presenti i dati per il fungo necessari all'albero di decisione, viene utilizzata la rete bayesiana			
			String firstNodeField = tree.getFirstNodeFieldForTree(m);
			
			String m_firstNode_Value = (String)m.getClass().getField(firstNodeField).get(m);
			
			if(m_firstNode_Value != null && !m_firstNode_Value.isEmpty()) {
				
				probability = tree.classifyInstance(m);		
				
				//se la probabilità, utilizzando l'albero, non è abbastanza alta, viene utilizzata anche la rete bayesiana, tramite una media dei due valori
				if(probability < ConfidentProbability) {
					
					//classe assegnata dall'albero
					String classTree = m._class;	
					
					//effettuo classificazione bayes
					double probabilityBN = BN.classifyInstance(m);					
					String classBN = m._class;
					
					//se la classe è la stessa per entrambi i classificatori...
					if(classTree.equals(classBN)) {
						probability = (probability + probabilityBN) / 2; //media delle due probabilità
					}
					else {
						//viene assegnata la classe con una probabilità più alta
						if(probability > probabilityBN) {
							m._class = classTree; //vince tree
						}
						else {
							probability = probabilityBN; //vince BN
						}
					}
				}
			}
			else {

				probability = BN.classifyInstance(m);
			}			
			
			DecimalFormat df = new DecimalFormat("0.00");

			System.out.println("Your mushroom is " + m.getClassName() + ", with probability of "+ df.format(probability * 100) + " %\n\n");
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}
	
	//metodo che ottiene un determinato valore per una determinata proprietà del fungo
	static String getValueForMushroom(HashMap<String, String> values,Scanner input) {
		
		String result = null;
		
		for(String key : values.keySet()) {
     		
    		 System.out.print(key);
    		 System.out.print(". ");
    		 System.out.println(values.get(key));	
    	}
      
		do {
			result = input.nextLine();
		}while(values.getOrDefault(result, "-1") == "-1");
		
    	return result;
	}
	
	//metodo che avvia la procedura di deduzione
	static void setLogicProoferForUser(Scanner input) {

		//Definizione atomi
		LogicProofer.Atom poisonous_mushroom = new LogicProofer.Atom("poisonous_mushroom");
		
		LogicProofer.Atom long_incubation = new LogicProofer.Atom("long_incubation");
		LogicProofer.Atom long_symp_occourence = new LogicProofer.Atom("long_symp_occourence", "Did you eat the mushroom 6 hours ago or earlier");
		LogicProofer.Atom short_incubation = new LogicProofer.Atom("short_incubation");
		LogicProofer.Atom short_symp_occourence = new LogicProofer.Atom("short_symp_occourence", "Did you eat the mushroom later than 6 hours ago");
		
		LogicProofer.Atom synd_phallotoxin = new LogicProofer.Atom("synd_phallotoxin");
		LogicProofer.Atom synd_gyromitrin = new LogicProofer.Atom("synd_gyromitrin");
		LogicProofer.Atom synd_coprine = new LogicProofer.Atom("synd_coprine");
		LogicProofer.Atom synd_psilocybin = new LogicProofer.Atom("synd_psilocybin");
		LogicProofer.Atom synd_gi_toxins= new LogicProofer.Atom("synd_gi_toxins");
		
		LogicProofer.Atom symp_gastroenteritis = new LogicProofer.Atom("symp_gastroenteritis");
		LogicProofer.Atom symp_abdodminal_cramping = new LogicProofer.Atom("symp_abdodminal_cramping");
		LogicProofer.Atom symp_nausea = new LogicProofer.Atom("symp_nausea");
		LogicProofer.Atom symp_vomiting = new LogicProofer.Atom("symp_vomiting");
		LogicProofer.Atom symp_increased_hepatic_enzymes = new LogicProofer.Atom("symp_increased_hepatic_enzyimes");
		LogicProofer.Atom symp_hepatic_failure = new LogicProofer.Atom("symp_hepatic_failure");
		LogicProofer.Atom symp_renal_failure = new LogicProofer.Atom("symp_renal_failure");
		LogicProofer.Atom symp_encephalopathy = new LogicProofer.Atom("symp_encephalopathy");
		LogicProofer.Atom symp_diarrhea = new LogicProofer.Atom("symp_diarrhea");
		LogicProofer.Atom symp_dizziness = new LogicProofer.Atom("symp_dizzines");
		LogicProofer.Atom symp_headache = new LogicProofer.Atom("symp_headache");
		LogicProofer.Atom symp_oliguria = new LogicProofer.Atom("symp_oliguria");
		LogicProofer.Atom symp_acute_renal_failure = new LogicProofer.Atom("symp_acute_renal_failure");
		LogicProofer.Atom symp_tubulointerstital_nephritis = new LogicProofer.Atom("symp_tubulointerstital_nephritis");
		LogicProofer.Atom symp_fibrosis = new LogicProofer.Atom("symp_fibrosis");
		LogicProofer.Atom symp_intractable_seizures = new LogicProofer.Atom("symp_intractable_seizures");
		LogicProofer.Atom symp_facial_flushing = new LogicProofer.Atom("symp_facial_flushing");
		LogicProofer.Atom symp_hypotension = new LogicProofer.Atom("symp_hypotension");
		LogicProofer.Atom symp_euphoria = new LogicProofer.Atom("symp_euphoria");
		LogicProofer.Atom symp_agitation = new LogicProofer.Atom("symp_agitation");
		LogicProofer.Atom symp_hyperthermia = new LogicProofer.Atom("symp_hyperthermia");
		LogicProofer.Atom symp_hallucinations = new LogicProofer.Atom("symp_hallucinations");
		LogicProofer.Atom drink_alcohol = new LogicProofer.Atom("drink_alcohol", "Did you drink alcool in the past hours");
			
		
		ArrayList<LogicProofer.Clause> KnoledgeBase = new ArrayList<LogicProofer.Clause>();

		
		ArrayList<LogicProofer.Literal> bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(long_symp_occourence, false));
		bodyClause.add(new LogicProofer.Literal(long_incubation, false));
		KnoledgeBase.add(new LogicProofer.Clause(poisonous_mushroom, bodyClause));
		//			
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(synd_phallotoxin, false));
		KnoledgeBase.add(new LogicProofer.Clause(long_incubation, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_gastroenteritis, false));
		bodyClause.add(new LogicProofer.Literal(symp_abdodminal_cramping, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_phallotoxin, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_diarrhea, false));
		bodyClause.add(new LogicProofer.Literal(symp_nausea, false));
		bodyClause.add(new LogicProofer.Literal(symp_increased_hepatic_enzymes, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_phallotoxin, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_hepatic_failure, false));
		bodyClause.add(new LogicProofer.Literal(symp_renal_failure, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_phallotoxin, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_encephalopathy, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_phallotoxin, bodyClause));
		//
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(synd_gyromitrin, false));
		KnoledgeBase.add(new LogicProofer.Clause(long_incubation, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_gastroenteritis, false));
		bodyClause.add(new LogicProofer.Literal(symp_dizziness, false));
		bodyClause.add(new LogicProofer.Literal(symp_headache, false));
		bodyClause.add(new LogicProofer.Literal(symp_intractable_seizures, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_gyromitrin, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_oliguria, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_gyromitrin, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_acute_renal_failure, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_gyromitrin, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_tubulointerstital_nephritis, false));
		bodyClause.add(new LogicProofer.Literal(symp_fibrosis, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_gyromitrin, bodyClause));
		
		
		
		//
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(short_symp_occourence, false));
		bodyClause.add(new LogicProofer.Literal(short_incubation, false));
		KnoledgeBase.add(new LogicProofer.Clause(poisonous_mushroom, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(synd_coprine, false));
		KnoledgeBase.add(new LogicProofer.Clause(short_incubation, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_facial_flushing, false));
		bodyClause.add(new LogicProofer.Literal(drink_alcohol, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_coprine, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_nausea, false));
		bodyClause.add(new LogicProofer.Literal(drink_alcohol, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_coprine, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_vomiting, false));
		bodyClause.add(new LogicProofer.Literal(drink_alcohol, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_coprine, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_hypotension, false));
		bodyClause.add(new LogicProofer.Literal(drink_alcohol, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_coprine, bodyClause));
		//
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(synd_psilocybin, false));
		KnoledgeBase.add(new LogicProofer.Clause(short_incubation, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_euphoria, false));
		bodyClause.add(new LogicProofer.Literal(symp_hallucinations, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_psilocybin, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_agitation, false));
		bodyClause.add(new LogicProofer.Literal(symp_hyperthermia, false));
		KnoledgeBase.add(new LogicProofer.Clause(synd_psilocybin, bodyClause));
		//
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(synd_gi_toxins, false));
		KnoledgeBase.add(new LogicProofer.Clause(short_incubation, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_nausea, false));
		bodyClause.add(new LogicProofer.Literal(drink_alcohol, true));
		KnoledgeBase.add(new LogicProofer.Clause(synd_gi_toxins, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_vomiting, false));
		bodyClause.add(new LogicProofer.Literal(drink_alcohol, true));
		KnoledgeBase.add(new LogicProofer.Clause(synd_gi_toxins, bodyClause));
		
		bodyClause = new ArrayList<LogicProofer.Literal>();
		bodyClause.add(new LogicProofer.Literal(symp_nausea, false));
		bodyClause.add(new LogicProofer.Literal(symp_diarrhea, true));
		KnoledgeBase.add(new LogicProofer.Clause(synd_gi_toxins, bodyClause));
		
		//istanzio l'oggetto che proverà la query dell'utente
		LogicProofer lp = new LogicProofer(KnoledgeBase, input, System.out);
		
		//query posta dall'utente
		ArrayList<LogicProofer.Literal> query = new ArrayList<LogicProofer.Literal> ();
		query.add(new LogicProofer.Literal(poisonous_mushroom, false));
		
		//effettuo deduzione
		LogicProofer.ExplanationTree exTree = lp.ProveQuery(query);
		
		if(exTree == null) {
			  System.out.println("Your mushroom is not poisonus, take a rest.");
		}
		else {
			  System.out.println("Your mushroom is poisonus. Do you want to know why? (y/n)");
			  
			  switch (input.next()) {
			  
			  case "y":
				  
				  int annidation = 0;
				  
				  do {
					  
					  System.out.println(exTree.toString());
					  
					  StringBuilder sb = new StringBuilder();
					  sb.append("\nPress -1 to ");
					  if(annidation == 0) {
						  sb.append("exit");
					  }
					  else {
						  sb.append("go up");
					  }
					
					  if(exTree.HasChildren()) {
						  sb.append(" or a number X for the explanation of the atom at the position X");	
					  }

					  System.out.println(sb.toString());
					  
					  //ottengo il valore
					  int indexChildren = GetNumberFromInput(input, -1,exTree.GetSizeOfExplanations() - 1);	
					  
					  if(indexChildren == -1) {
						  //go to the upper lever
						  annidation--;
						  exTree =  exTree.ParentTree;
					  }
					  else {
						  
						  LogicProofer.ExplanationTree children = exTree.GetExplanationTree(indexChildren);
						  
						  if(children == null) {
							  System.out.println(exTree.Clause.Body.get(indexChildren).Atom.toString() + " is false");
						  }
						  else {
							  annidation++;
							  exTree = children;
						  }
					  }
					  
				  
				  }while(annidation != -1);
				  
				  
				  break;
			  }
		}
	}
	
	//classe che ottiene un valore numerico dall'utente
	static int GetNumberFromInput(Scanner input, int min, int max) {
				
		do
		{
			try {
				int choice= input.nextInt();
				
				if(choice >= min && choice <= max) {
					return choice;
				}
	        }
	        catch(Exception e) {
	        	input.nextLine();
	        }
		}while(true);
	}

}
	    


	
	    
		