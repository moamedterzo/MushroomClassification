import java.io.FileWriter;
import java.util.HashMap;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

//Classe che rappresenta le proprietà del fungo
public class Mushroom {

	public String _class;

	public String cap_shape;
	public String cap_surface;
	public String cap_color;
	public String bruises;
	public String odor;
	
	public String gill_attachment;
	public String gill_spacing;
	public String gill_size;
	public String gill_color;
	public String stalk_shape;
	
	public String stalk_root;
	public String stalk_surface_above_ring;
	public String stalk_surface_below_ring;
	public String stalk_color_above_ring;
	public String stalk_color_below_ring;
	
	public String veil_type;
	public String veil_color;
	public String ring_number;
	public String ring_type;
	public String spore_print_color;
	
	public String population;
	public String habitat;
	
	//elenco dei valori possibili per ciascun attributo
	public static HashMap<String, String> values_cap_shape = new  HashMap<String, String>();
	public static HashMap<String, String> values_cap_surface = new  HashMap<String, String>();
	public static HashMap<String, String> values_cap_color = new  HashMap<String, String>();
	public static HashMap<String, String> values_bruises = new  HashMap<String, String>();
	public static HashMap<String, String> values_odor = new  HashMap<String, String>();
	
	public static HashMap<String, String> values_gill_attachment = new  HashMap<String, String>();
	public static HashMap<String, String> values_gill_spacing = new  HashMap<String, String>();
	public static HashMap<String, String> values_gill_size = new  HashMap<String, String>();
	public static HashMap<String, String> values_gill_color = new  HashMap<String, String>();
	public static HashMap<String, String> values_stalk_shape = new  HashMap<String, String>();
	
	public static HashMap<String, String> values_stalk_root = new  HashMap<String, String>();
	public static HashMap<String, String> values_stalk_surface_above_ring = new  HashMap<String, String>();
	public static HashMap<String, String> values_stalk_surface_below_ring = new  HashMap<String, String>();
	public static HashMap<String, String> values_stalk_color_above_ring = new  HashMap<String, String>();
	public static HashMap<String, String> values_stalk_color_below_ring = new  HashMap<String, String>();
	
	public static HashMap<String, String> values_veil_type = new  HashMap<String, String>();
	public static HashMap<String, String> values_veil_color = new  HashMap<String, String>();
	public static HashMap<String, String> values_ring_number = new  HashMap<String, String>();
	public static HashMap<String, String> values_ring_type = new  HashMap<String, String>();
	public static HashMap<String, String> values_spore_print_color = new  HashMap<String, String>();
	
	public static HashMap<String, String> values_population = new  HashMap<String, String>();
	public static HashMap<String, String> values_habitat = new  HashMap<String, String>();
	
	
	static {
		
		//valorizzazione dei vari elenchi per ciascun attributo
		values_cap_shape.put("b", "bell");
		values_cap_shape.put("c", "conical");
		values_cap_shape.put("x", "convex");
		values_cap_shape.put("f", "flat");
		values_cap_shape.put("k", "knobbed");
		values_cap_shape.put("s", "sunken");
	
		values_cap_surface.put("f", "fibrous");
		values_cap_surface.put("g", "grooves");
		values_cap_surface.put("y", "scaly");
		values_cap_surface.put("s", "smooth");
		
		values_cap_color.put("n", "brown");
		values_cap_color.put("b", "buff");
		values_cap_color.put("c", "cinnamon");
		values_cap_color.put("g", "gray");
		values_cap_color.put("r", "green");
		values_cap_color.put("p", "pink");
		values_cap_color.put("u", "purple");
		values_cap_color.put("e", "red");
		values_cap_color.put("w", "white");
		values_cap_color.put("y", "yellow");
		
		values_bruises.put("t", "bruises");
		values_bruises.put("f", "no");
		
		values_odor.put("a", "almond");
		values_odor.put("l", "anise");
		values_odor.put("c", "creosote");
		values_odor.put("y", "fishy");
		values_odor.put("f", "foul");
		values_odor.put("m", "musty");
		values_odor.put("n", "none");
		values_odor.put("p", "pungent");
		values_odor.put("s", "spicy");
		
		values_gill_attachment.put("a", "attached");
		values_gill_attachment.put("d", "descending");
		values_gill_attachment.put("f", "free");
		values_gill_attachment.put("n", "notched");
		
		values_gill_spacing.put("c", "close");
		values_gill_spacing.put("w", "crowded");
		values_gill_spacing.put("d", "distant");
		
		values_gill_size.put("b", "broad");
		values_gill_size.put("n", "narrow");
		
		values_gill_color.put("k", "black");
		values_gill_color.put("n", "brown");
		values_gill_color.put("b", "buff");
		values_gill_color.put("h", "chocolate");
		values_gill_color.put("g", "gray");
		values_gill_color.put("r", "green");
		values_gill_color.put("o", "orange");
		values_gill_color.put("p", "pink");
		values_gill_color.put("u", "purple");
		values_gill_color.put("e", "red");
		values_gill_color.put("w", "white");
		values_gill_color.put("y", "yellow");
		
		values_stalk_shape.put("e", "enlarging");
		values_stalk_shape.put("t", "tapering");
		
		values_stalk_root.put("b", "bulbous");
		values_stalk_root.put("c", "club");
		values_stalk_root.put("u", "cup");
		values_stalk_root.put("e", "equal");
		values_stalk_root.put("z", "rhizomorphs");
		values_stalk_root.put("r", "rooted");
		values_stalk_root.put("?", "missing");
		
		values_stalk_surface_above_ring.put("f", "fibrous");
		values_stalk_surface_above_ring.put("y", "scaly");
		values_stalk_surface_above_ring.put("k", "silky");
		values_stalk_surface_above_ring.put("s", "smooth");
		
		values_stalk_surface_below_ring.put("f", "fibrous");
		values_stalk_surface_below_ring.put("y", "scaly");
		values_stalk_surface_below_ring.put("k", "silky");
		values_stalk_surface_below_ring.put("s", "smooth");
		
		values_stalk_color_above_ring.put("n", "brown");
		values_stalk_color_above_ring.put("b", "buff");
		values_stalk_color_above_ring.put("c", "cinnamon");
		values_stalk_color_above_ring.put("g", "gray");
		values_stalk_color_above_ring.put("o", "orange");
		values_stalk_color_above_ring.put("p", "pink");
		values_stalk_color_above_ring.put("e", "red");
		values_stalk_color_above_ring.put("w", "white");
		values_stalk_color_above_ring.put("y", "yellow");
		
		values_stalk_color_below_ring.put("n", "brown");
		values_stalk_color_below_ring.put("b", "buff");
		values_stalk_color_below_ring.put("c", "cinnamon");
		values_stalk_color_below_ring.put("g", "gray");
		values_stalk_color_below_ring.put("o", "orange");
		values_stalk_color_below_ring.put("p", "pink");
		values_stalk_color_below_ring.put("e", "red");
		values_stalk_color_below_ring.put("w", "white");
		values_stalk_color_below_ring.put("y", "yellow");
		
		values_veil_type.put("p", "partial");
		values_veil_type.put("u", "universal");
		
		values_veil_color.put("n", "brown");
		values_veil_color.put("o", "orange");
		values_veil_color.put("w", "white");
		values_veil_color.put("y", "yellow");
		
		values_ring_number.put("n", "none");
		values_ring_number.put("o", "one");
		values_ring_number.put("t", "two");
		
		values_ring_type.put("c", "cobwebby");
		values_ring_type.put("e", "evanescent");
		values_ring_type.put("f", "flaring");
		values_ring_type.put("l", "large");
		values_ring_type.put("n", "none");
		values_ring_type.put("p", "pendant");
		values_ring_type.put("s", "sheathing");
		values_ring_type.put("z", "zone");
		
		values_spore_print_color.put("k", "black");
		values_spore_print_color.put("n", "brown");
		values_spore_print_color.put("b", "buff");
		values_spore_print_color.put("h", "chocolate");
		values_spore_print_color.put("r", "green");
		values_spore_print_color.put("o", "orange");
		values_spore_print_color.put("u", "purple");
		values_spore_print_color.put("w", "white");
		values_spore_print_color.put("y", "yellow");
		
		values_population.put("a", "abundant");
		values_population.put("c", "clustered");
		values_population.put("n", "numerous");
		values_population.put("s", "scattered");
		values_population.put("v", "several");
		values_population.put("y", "solitary");
		
		values_habitat.put("g", "grasses");
		values_habitat.put("l", "leaves");
		values_habitat.put("m", "meadows");
		values_habitat.put("p", "paths");
		values_habitat.put("u", "urban");
		values_habitat.put("w", "waste");
		values_habitat.put("d", "woods");
		
	}

	//percorso temporaneo nel quale viene scritto il file contenente l'istanza del fungo corrente
	String tempPath = "bin\\tempFiles.csv";
	
	
	//ottiene un oggetto di tipo Instance partendo dalla classe corrente
	public Instance toInstance() throws Exception {
		
		FileWriter csvWriter = new FileWriter(tempPath,false);  
		
		//intestazione
		csvWriter.append("class,cap-shape,cap-surface,cap-color,bruises,odor,gill-attachment,gill-spacing,gill-size,gill-color,stalk-shape,stalk-root,stalk-surface-above-ring,stalk-surface-below-ring,stalk-color-above-ring,stalk-color-below-ring,veil-type,veil-color,ring-number,ring-type,spore-print-color,population,habitat\n");  
				
		//definizione delle due classi (poisonus ed edible)
		csvWriter.append("p,x,s,n,t,p,f,c,n,k,e,e,s,s,w,w,p,w,o,p,k,s,u\n");
		csvWriter.append("e,x,s,y,t,a,f,c,b,k,e,c,s,s,w,w,p,w,o,p,n,n,g\n");
		
		//dati
		csvWriter.append(_class == null || _class.isEmpty()? "?" : _class);
		csvWriter.append(",");
		
		csvWriter.append(cap_shape == null || cap_shape.isEmpty() ? "?" : cap_shape);
		csvWriter.append(",");
		csvWriter.append(cap_surface == null || cap_surface.isEmpty() ? "?" : cap_surface);
		csvWriter.append(",");
		csvWriter.append(cap_color == null || cap_color.isEmpty() ? "?" : cap_color);
		csvWriter.append(",");
		csvWriter.append(bruises == null || bruises.isEmpty() ? "?" : bruises);
		csvWriter.append(",");
		csvWriter.append(odor == null || odor.isEmpty() ? "?" : odor);
		csvWriter.append(",");
		
		csvWriter.append(gill_attachment == null || gill_attachment.isEmpty() ? "?" : gill_attachment);
		csvWriter.append(",");
		csvWriter.append(gill_spacing == null || gill_spacing.isEmpty() ? "?" : gill_spacing);
		csvWriter.append(",");
		csvWriter.append(gill_size == null || gill_size.isEmpty() ? "?" : gill_size);
		csvWriter.append(",");
		csvWriter.append(gill_color == null || gill_color.isEmpty() ? "?" : gill_color);
		csvWriter.append(",");
		csvWriter.append(stalk_shape == null || stalk_shape.isEmpty() ? "?" : stalk_shape);
		csvWriter.append(",");
		
		csvWriter.append(stalk_root == null || stalk_root.isEmpty() ? "?" : stalk_root);
		csvWriter.append(",");
		csvWriter.append(stalk_surface_above_ring == null || stalk_surface_above_ring.isEmpty() ? "?" : stalk_surface_above_ring);
		csvWriter.append(",");
		csvWriter.append(stalk_surface_below_ring == null || stalk_surface_below_ring.isEmpty() ? "?" : stalk_surface_below_ring);
		csvWriter.append(",");
		csvWriter.append(stalk_color_above_ring == null || stalk_color_above_ring.isEmpty() ? "?" : stalk_color_above_ring);
		csvWriter.append(",");
		csvWriter.append(stalk_color_below_ring == null || stalk_color_below_ring.isEmpty() ? "?" : stalk_color_below_ring);
		csvWriter.append(",");
		
		csvWriter.append(veil_type == null || veil_type.isEmpty() ? "?" : veil_type);
		csvWriter.append(",");
		csvWriter.append(veil_color == null || veil_color.isEmpty() ? "?" : veil_color);
		csvWriter.append(",");
		csvWriter.append(ring_number == null || ring_number.isEmpty() ? "?" : ring_number);
		csvWriter.append(",");
		csvWriter.append(ring_type == null || ring_type.isEmpty() ? "?" : ring_type);
		csvWriter.append(",");
		csvWriter.append(spore_print_color == null || spore_print_color.isEmpty() ? "?" : spore_print_color);
		csvWriter.append(",");
		
		csvWriter.append(population == null || population.isEmpty() ? "?" : population);
		csvWriter.append(",");
		csvWriter.append(habitat == null || habitat.isEmpty() ? "?" : habitat);	

		csvWriter.flush();  
		csvWriter.close();    
		    
	    Instances sourceInstance = new DataSource(tempPath).getDataSet();	    
	    sourceInstance.setClassIndex(0);	    
	    
		return sourceInstance.instance(2);
	}
	
	//restituisce la descrizione testuale del fungo
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
				
		if(cap_shape != null && !cap_shape.isEmpty()) {			

			sb.append("cap shape=");
			sb.append(values_cap_shape.get(cap_shape));			
			sb.append("; ");
		}
		
		if(cap_surface != null && !cap_surface.isEmpty()) {			

			sb.append("cap surface=");
			sb.append(values_cap_surface.get(cap_surface));			
			sb.append("; ");
		}
		
		if(cap_color != null && !cap_color.isEmpty()) {			

			sb.append("cap color=");
			sb.append(values_cap_color.get(cap_color));			
			sb.append("; ");
		}
		
		if(bruises != null && !bruises.isEmpty()) {			

			sb.append("bruises=");
			sb.append(values_bruises.get(bruises));			
			sb.append("; ");
		}
		
		if(odor != null && !odor.isEmpty()) {			

			sb.append("odor=");
			sb.append(values_odor.get(odor));			
			sb.append("; ");
		}
		
		if(gill_attachment != null && !gill_attachment.isEmpty()) {			

			sb.append("gill attachment=");
			sb.append(values_gill_attachment.get(gill_attachment));			
			sb.append("; ");
		}
		
		if(gill_spacing != null && !gill_spacing.isEmpty()) {			

			sb.append("gill spacing=");
			sb.append(values_gill_spacing.get(gill_spacing));			
			sb.append("; ");
		}
		
		if(gill_size != null && !gill_size.isEmpty()) {			

			sb.append("gill size=");
			sb.append(values_gill_size.get(gill_size));			
			sb.append("; ");
		}
		
		if(gill_color != null && !gill_color.isEmpty()) {			

			sb.append("gill color=");
			sb.append(values_gill_color.get(gill_color));			
			sb.append("; ");
		}
		

		if(stalk_shape != null && !stalk_shape.isEmpty()) {			

			sb.append("stalk shape=");
			sb.append(values_stalk_shape.get(stalk_shape));			
			sb.append("; ");
		}
		
		if(stalk_root != null && !stalk_root.isEmpty()) {			

			sb.append("stalk_root=");
			sb.append(values_stalk_root.get(stalk_root));			
			sb.append("; ");
		}
		
		if(stalk_surface_above_ring != null && !stalk_surface_above_ring.isEmpty()) {			

			sb.append("stalk surface above ring=");
			sb.append(values_stalk_surface_above_ring.get(stalk_surface_above_ring));			
			sb.append("; ");
		}
		
		if(stalk_surface_below_ring != null && !stalk_surface_below_ring.isEmpty()) {			

			sb.append("stalk surface below ring=");
			sb.append(values_stalk_surface_below_ring.get(stalk_surface_below_ring));			
			sb.append("; ");
		}
		
		if(stalk_color_above_ring != null && !stalk_color_above_ring.isEmpty()) {			

			sb.append("stalk color above ring=");
			sb.append(values_stalk_color_above_ring.get(stalk_color_above_ring));			
			sb.append("; ");
		}
		
		if(stalk_color_below_ring != null && !stalk_color_below_ring.isEmpty()) {			

			sb.append("stalk color below ring=");
			sb.append(values_stalk_color_below_ring.get(stalk_color_below_ring));			
			sb.append("; ");
		}
		
		if(veil_type != null && !veil_type.isEmpty()) {			

			sb.append("veil type=");
			sb.append(values_veil_type.get(veil_type));			
			sb.append("; ");
		}
		
		if(veil_color != null && !veil_color.isEmpty()) {			

			sb.append("veil color=");
			sb.append(values_veil_color.get(veil_color));			
			sb.append("; ");
		}
		
		if(ring_number != null && !ring_number.isEmpty()) {			

			sb.append("ring number=");
			sb.append(values_ring_number.get(ring_number));			
			sb.append("; ");
		}
		
		if(ring_type != null && !ring_type.isEmpty()) {			

			sb.append("ring type=");
			sb.append(values_ring_type.get(ring_type));			
			sb.append("; ");
		}
		
		if(spore_print_color != null && !spore_print_color .isEmpty()) {			

			sb.append("spore print color =");
			sb.append(values_spore_print_color .get(spore_print_color ));			
			sb.append("; ");
		}
		
		if(population != null && !population.isEmpty()) {			

			sb.append("population=");
			sb.append(values_population.get(population));			
			sb.append("; ");
		}
		
		if(habitat != null && !habitat.isEmpty()) {			

			sb.append("habitat=");
			sb.append(values_habitat.get(habitat));			
			sb.append("; ");
		}
		
		String value = sb.toString();
		
		if(value.isEmpty()) {
			value = "no information";
		}
		else {
			value = value.substring(0, value.length() - 2);
		}

		return "Mushroom: " + value;
	}
	
	//restituisce il nome per esteso della classe del fungo
	public String getClassName() {
		
		if(_class .equals("e"))
			return "edible";
		
		else if(_class.equals("p"))
			return "poisonus";
		
		else
			return "not defined";
	}
}