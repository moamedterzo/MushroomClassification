import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//classe utilizzata per la deduzione top-down di una query, data una knowledge base
//viene effettuata l'assuzione del mondo chiuso
//gli atomi che non compaiono come testa in nessuna clausola vengono richiesti all'utente, ossia è l'utente a specificare se siano veri o meno
public class LogicProofer {

	
	private ArrayList<Clause> KnoledgeBase;	

	private Scanner input;
	private PrintStream out;

	//elenco degli atomi richiesti all'utente esplicitamente
	private HashMap<Atom, Boolean> AtomsAskedToUser;

	//atomo creato dal sistema, utile per la spiegazione tramite albero della query
	private static Atom AtomYes = new Atom("yes");
	
	
	public LogicProofer(ArrayList<Clause> knoledgeBase, Scanner input, PrintStream out) {
		this.KnoledgeBase = knoledgeBase;
		this.input = input;
		this.out = out;
		
		AtomsAskedToUser = new HashMap<Atom, Boolean>();
	}	
	

	public ExplanationTree ProveQuery( ArrayList<Literal> query) {
		
		//svuota le variabili prima della risoluzione della query
		AtomsAskedToUser.clear();
		
		return Prove_NAF_TD(new Clause(AtomYes, query));
	}	
	
	private ExplanationTree Prove_NAF_TD(Clause query) {

		ExplanationTree explanationTree = new ExplanationTree(query);	
		
		for(int i = 0; i < query.Body.size() ; i++) {
		
			//ottiene il letterale da provare
			Literal l = query.Body.get(i);
			
			//prova dell'atomo
			ExplanationTree subExTree = this.ProveAtom(l.Atom);

			//se l'atomo è provato, il risultato dipende dal valore di "NegationAsSuccess"	
			if( (subExTree == null) == l.NegationAsSuccess) {
				//ok, la procedura continua
				explanationTree.SetExplanationTree(i, subExTree);
			}
			else {
				return null; //la procedura fallisce
			}				
		}
		
		return explanationTree;
	}
	
	private ExplanationTree ProveAtom(Atom a) {
		
		//ottiene tutte le clausole dove questo atomo compare come testa
		ArrayList<Clause> bodyClauses =  this.GetClausesFromHeadAtom(a);
		
		if(bodyClauses.size() == 0) {
			
			//questo atomo deve essere provato interrogando l'utente
			boolean resultQuery = false;
			
			if(AtomsAskedToUser.containsKey(a)) {
				
				//se è stata già fornita una rispota, è inutile interrogare nuovamente l'utente
				resultQuery = AtomsAskedToUser.get(a);
			}
			else {
				//interrogo l'utente
				out.println(a.getQueryForTheUser() + "? (y/n)");
				
				//ottenimento della risposta...
				String answer;
				do
				{					
					answer = input.next();
					
					if(answer.equals("y") || answer.equals("n")) {
						break;
					}			       
				}while(true);


				resultQuery = answer.equals("y");
				
				//memorizzazione della risposta
				AtomsAskedToUser.put(a, resultQuery);
			}
			
			if(resultQuery) {
				return new ExplanationTree(new Clause(a, null)); //ok, questo atomo è provato
			}
			else {
				return null; //questo atomo non è provato
			}
		}
		else {

			//Si prova a risolvere questo atomo, provando ciascun corpo delle clausole dove questo atomo compare come testa
			for(Clause clause : bodyClauses) {
				
				//se il corpo è vuoto, l'atomo è provato automaticamente
				if(clause.Body == null || clause.Body.size() == 0) {
					return new ExplanationTree(clause);
				}
				else {
					//prova del corpo...
					ExplanationTree exTree = this.Prove_NAF_TD(clause);
					if(exTree != null) {
						return exTree;
					}
				}
			}
		}
		return null;
	}
	
	//funzione che restituisce l'elenco delle clausole dove l'atomo compare come testa
	private ArrayList<Clause> GetClausesFromHeadAtom(Atom head){
		
		ArrayList<Clause> result = new ArrayList<Clause>();
		
		for(Clause clause : KnoledgeBase) {
			if(clause.Head.equals(head)) {
				
				result.add(clause);
			}
		}
		
		return result;
	}
	
	//classe utilizzata per spiegare la deduzione di una query
	//questa classe è ricorsiva
	public static class ExplanationTree{
		
		public ExplanationTree(Clause clause) {
			this.Clause = clause;
			this.Explanations = new  ArrayList<ExplanationTree>();
			
			int numberOfElements = clause.Body == null ? 0 : clause.Body.size();
			while(numberOfElements-- > 0) {
				this.Explanations.add(null);
			}
		}
		
		public Clause Clause; //clausola

		public ExplanationTree ParentTree; //il padre di questa classe
		public ArrayList<ExplanationTree> Explanations; //ogni letterale della clausola è spiegato da un albero
		
		
		public boolean HasChildren() {
			return this.Clause.Body != null && this.Clause.Body.size() > 0;
		}
		
		public int GetSizeOfExplanations() {
			return this.Explanations.size();
		}
		
		public ExplanationTree GetExplanationTree(int index) {
			return Explanations.get(index);
		}
		
		public void SetExplanationTree(int index, ExplanationTree exTree) {
			Explanations.set(index, exTree);	
			
			if(exTree != null)
				exTree.ParentTree = this;		
		}
		
		public String toString() {
			
			return this.Clause.toString();
		}
	}
	
	//classe che rappresenta una clausola (testa <- letterale1 and letterale2 ... and letteraleN)
	public static class Clause{
		
		public Clause(Atom head, ArrayList<Literal> body) {
			this.Head = head;
			this.Body = body;
		}
		
		
		public Atom Head;
		
		public ArrayList<Literal> Body;
		
		public String toString() {
			
			StringBuilder sb = new StringBuilder();
			sb.append(this.Head.toString());
			sb.append(" <-- " );
			
			if(this.Body == null || this.Body.size() == 0) {
				sb.append("true");
			}
			else {
				for(int i = 0; i< Body.size(); i++){
					 Literal lit = Body.get(i);
					 sb.append(lit.toString());
					 
					 if(i < Body.size() - 1) {
						 sb.append(" and ");
					 }
				}
			}
			return sb.toString();
		}
	}
	
	//classe che rappresenta un letterale
	public static class Literal{
		
		public Literal(Atom atom, boolean negationAsSuccess) {
			this.Atom = atom;
			this.NegationAsSuccess = negationAsSuccess;
		}
		
		public Atom Atom;
		
		//0 se questo letterale è falso se occorre il fallimento dell'atomo, 
		//1 se questo letterale è vero se il fallimento occorre
		//corrisponde al simbolo ~ nel linguaggio delle clausole proposizionali
		public boolean NegationAsSuccess; 
		
		public String toString() {
			
			return (this.NegationAsSuccess ? "~" : "") + this.Atom.toString();
		}
	}
	
	//classe che rappresenta un atomo
	public static class Atom{
		
		public Atom(String name) {
			this.Name = name;
		}
		
		public Atom(String name, String query) {
			this.Name = name;
			this.Query = query;
		}

		public String Name;
		
		//valore stringa che può essere utilizzato in fase di interrogazione dell'utente, per chiedere se questo atomo sia vero o falso
		public String Query;
		
		
		
		public String toString() {
			return this.Name;
		}
		
		public String getQueryForTheUser() {
			if(this.Query != null && !this.Query.isEmpty()) {
				return this.Query;
			}
			else {
				return "Did you accuse " + this.toString().replaceAll("_", " ");
			}
		}
	}
}
