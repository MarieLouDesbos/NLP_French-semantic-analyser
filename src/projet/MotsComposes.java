package projet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.w3c.dom.Node;

public class MotsComposes {
	/*But : Créer un arbre préfixe des mots composés*/
	
	
	//Fonction qui nettoie le fichier texte pour ne garder que les mots composés et pas le texte inutile au début
	public static boolean cleanDebutFichier() {
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/marie-lou/eclipse-workspace/Analyseur_semantique/mots/LEXICALNET-JEUXDEMOTS-ENTRIES-MWE.txt")));
			
			StringBuffer sb = new StringBuffer();
			String line;    
			
			while ((line = reader.readLine()) != null) {
				// Si la ligne ne commence pas par / on la garde
				int index = line.indexOf("/");
				if(index != 0) {
					sb.append(line + "\n");
				}
			}
			reader.close();
			BufferedWriter out = new BufferedWriter(new FileWriter("/Users/marie-lou/eclipse-workspace/Analyseur_semantique/mots/LEXICALNET-JEUXDEMOTS-ENTRIES-MWE.txt"));
			out.write(sb.toString());
			out.close();
			System.out.println("Fichier mot composé nettoyé");
			
		} catch (Exception e) {
			return false;
		}
		return true;
	}

		
	//Fonction qui crée un fichier CSV qui sépare les mots des mots composés
	public static void creationCSVMotsCompos() throws IOException {
		//Récupérer le contenu du fichier dans un string
		InputStream is = new FileInputStream("/Users/marie-lou/eclipse-workspace/Analyseur_semantique/mots/LEXICALNET-JEUXDEMOTS-ENTRIES-MWE.txt");
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader buffer = new BufferedReader(isr);
	        
	    String contenuFichier = buffer.readLine();
	    
	    StringBuilder builder = new StringBuilder();
	        int i = 0;
	    while(contenuFichier != null){
	    	if(i==0) {
	    		System.out.println("Je suis dans le while");
	    		i++;
	    	}
	    	
	       builder.append(contenuFichier).append("\n");
	       contenuFichier = buffer.readLine();
	    }
	        
	    //String str = builder.toString();
	    //System.out.println(str);
	    buffer.close();
	    System.out.println("Je suis sorti du while");
	    //Mettre le contenu dans un fichier CSV
	    
	    //Délimiteurs qui doivent être dans le fichier CSV
		//final String DELIMITER = " ";
		final String SEPARATOR = "\n";
		
		//En-tête de fichier
		final String HEADERR = "mot_compose i l o p m b";
		
		File outFile = new File("/Users/marie-lou/eclipse-workspace/Analyseur_semantique/mots/MotsComposes.csv");
		FileWriter fileCSV = new FileWriter(outFile);
		
		//Ajouter l'en-tête
		fileCSV.append(HEADERR);
		//Ajouter une nouvelle ligne après l'en-tête
		fileCSV.append(SEPARATOR);
		
		fileCSV.write(contenuFichier);
		
		fileCSV.close();
		
		System.out.println("Fichier csv des mots composés créé");
		
		
	}

	
	public static void main(String[] args) throws IOException {
		//cleanFichier();
		creationCSVMotsCompos();
	}

}
