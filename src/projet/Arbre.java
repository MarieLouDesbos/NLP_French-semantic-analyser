package projet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Arbre<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String file_path = "/Users/marie-lou/eclipse-workspace/Analyseur_semantique/mots/arbre_mots_composes.txt";

	private Noeud<String> racine;

    //Initialise un arbre avec le noeud racine.
    public Arbre(Noeud<String> racine) {
        this.racine = racine;
    }

    //Vérifie si l'arbre est vide (le noeud racine est vide).
    public boolean isEmpty() {
        return racine == null;
    }

    public Noeud<String> getracine() {
        return racine;
    }

    public void setRacine(Noeud<String> racine) {
        this.racine = racine;
    }

    /**
     *
     * Check if given Mot is present in the Arbre.
     *
     * @param key The Mot to search for
     * @return <code>true</code> if the given key was found in the Arbre,
     * <code>false</code> otherwise.
     */
    public boolean exists(String key) {
        return find(racine, key);
    }

    //Nombre de Noeuds (taille) dans l'arbre.
    public int size() {
        return getNumberOfDescendants(racine) + 1;
    }

    //Connaitre le nombre de descendant qu'un noeud a
    public int getNumberOfDescendants(Noeud<String> Noeud) {
        int n = Noeud.getEnfants().size();
        for (Noeud<String> child : Noeud.getEnfants())
            n += getNumberOfDescendants(child);

        return n;

    }

    private boolean find(Noeud<String> noeud, String keyNoeud) {
        boolean res = false;
        if (noeud.getMot().equals(keyNoeud))
            return true;

        else {
            for (Noeud<String> child : noeud.getEnfants())
                if (find(child, keyNoeud))
                    res = true;
        }

        return res;
    }

    private Noeud<String> findNoeud(Noeud<String> Noeud, String keyNoeud) {
        if (Noeud == null)
            return null;
        if (Noeud.getMot().equals(keyNoeud))
            return Noeud;
        else {
            Noeud<String> cNoeud = null;
            for (Noeud<String> child : Noeud.getEnfants())
                if ((cNoeud = findNoeud(child, keyNoeud)) != null)
                    return cNoeud;
        }
        return null;
    }


    /**
     *
     * Get the list of Noeuds in the longest path from racine to any leaf in the Arbre.
     *
     * For example, for the below Arbre
     * <pre>
     *          A
     *         / \
     *        B   C
     *           / \
     *          D  E
     *              \
     *              F
     * </pre>
     *
     * The result would be [A, C, E, F]
     *
     * @return The list of Noeuds in the longest path.
     */
    public ArrayList<Noeud<String>> getLongestPathFromracineToAnyLeaf() {
        ArrayList<Noeud<String>> longestPath = null;
        int max = 0;
        for (ArrayList<Noeud<String>> path : getPathsFromRacineToAnyLeaf()) {
            if (path.size() > max) {
                max = path.size();
                longestPath = path;
            }
        }
        return longestPath;
    }


    /**
     *
     * Get the height of the Arbre (the number of Noeuds in the longest path from racine to a leaf)
     *
     * @return The height of the Arbre.
     */
    public int getHeight() {
        return getLongestPathFromracineToAnyLeaf().size();
    }

    /**
     *
     * Get a list of all the paths (which is again a list of Noeuds along a path) from the racine Noeud to every leaf.
     *
     * @return List of paths.
     */
    public ArrayList<ArrayList<Noeud<String>>> getPathsFromRacineToAnyLeaf() {
        ArrayList<ArrayList<Noeud<String>>> paths = new ArrayList<ArrayList<Noeud<String>>>();
        ArrayList<Noeud<String>> currentPath = new ArrayList<Noeud<String>>();
        getPath(racine, currentPath, paths);

        return paths;
    }

    private void getPath(Noeud<String> Noeud, ArrayList<Noeud<String>> currentPath,
                         ArrayList<ArrayList<Noeud<String>>> paths) {
        if (currentPath == null)
            return;

        currentPath.add(Noeud);

        if (Noeud.getEnfants().size() == 0) {
            // This is a leaf
            paths.add(clone(currentPath));
        }
        for (Noeud<String> child : Noeud.getEnfants())
            getPath(child, currentPath, paths);

        int index = currentPath.indexOf(Noeud);
        for (int i = index; i < currentPath.size(); i++)
            currentPath.remove(index);
    }

    private ArrayList<Noeud<String>> clone(ArrayList<Noeud<String>> list) {
        ArrayList<Noeud<String>> newList = new ArrayList<Noeud<String>>();
        for (Noeud<String> Noeud : list)
            newList.add(new Noeud<String>(Noeud));

        return newList;
    }
    
    
    //Fonction qui regarde si le mot composé est présent dans l'arbre
    public boolean motPresent(String[] listeMots) {
    	//ArrayList<ArrayList<Noeud<T>>> contenuArbre = getPathsFromRacineToAnyLeaf();
    	Noeud<String> n = racine;
    	//String motCompose = "";
    	for(String mot : listeMots) { //Pour chaque mot de la liste donnée
    		//System.out.println("Mot de la liste :" + mot);
    		//System.out.println("On regarde dans la liste des enfants du noeud actuel s'il existe :");
    		List<Noeud<String>> enfants = n.getEnfants();
    		int tailleListe = enfants.size();
    		int index = 0;
    		while(tailleListe > 0) {
    			Noeud<String> child = enfants.get(index);
    			//System.out.println("Noeud actuel :" + child.getMot());
    			if(child.getMot().equals("feuille") && mot == "fin"){ //Si l'unique enfant du noeud est le mot feuille alors c'est un mot composé
    				//System.out.println("Le mot est bien un mot composé !");
    				return true;
    			} else if(mot.equals(child.getMot())) { //Si le mot est un enfant du noeud
    				//System.out.println("Le mot existe donc on passe au mot suivant :");
    				n = child;
    				//motCompose = motCompose + " " + mot;
    				break;
    			}
    			//System.out.println("Le mot de l'enfant ne correspond pas au mot de la liste donc on passe à l'enfant suivant");
    			index+=1;
    			tailleListe-=1;
    			//System.out.println("Taille de la liste : " + tailleListe);
    			if(tailleListe==0) {
    				return false;
    			}    			
    		}
    	}
    	return false;
    }
    
    // Fonction qui ajoute les mots composés de la liste dans un arbre :
    // Elle doit mettre une feuille "fin" à la fin de chaque mot composé pour savoir si on est bien a une feuille
    
    public static void putInTree(Arbre<String> arbre) throws IOException {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/marie-lou/eclipse-workspace/Analyseur_semantique/mots/LEXICALNET-JEUXDEMOTS-ENTRIES-MWE.txt")));
		
		//StringBuffer sb = new StringBuffer();
		String line; 
		int nbLignesAjoutés = 0;
		Noeud<String> noeudActuel;
		while ((line = reader.readLine()) != null) {
			//On coupe la ligne au niveau des espaces et on met chaque partie dans un tableau
			String[] contenuLigne = line.split(" ");
			//On met chaque morceau de la ligne dans un noeud
			noeudActuel = arbre.getracine();
			for (String a: contenuLigne) {
				//System.out.println("Le mot que je veux ajouter est : " + a);
				//Je regarde si le mot est pas déjà un enfant du noeud actuel
			
				for(Noeud<String> enfants : noeudActuel.getEnfants()) { //Pour chaque noeud enfant je regarde le mot qu'il contient
					//System.out.println("Enfant du noeud : " + enfants.getMot());
					if(enfants.getMot().equals(a)) { //Si un des noeud contient le mot qu'on essai d'ajouter on n'ajoute rien et on donne juste le noeud suivant
						//System.out.println("Le mot est déjà présent dans les enfants du noeud actuel donc je ne l'ajoute pas");
						noeudActuel = enfants;
						break;
					}
				}
				/*List<Noeud<String>> enfants = noeudActuel.getEnfants(); //Je regarde les noeud enfants
				for(int i = 0; i < enfants.size(); i++) { //Pour chaque noeud enfant je regarde le mot qu'il contient
					//System.out.println("Enfant du noeud : " + enfants.get(i).getMot());
					if(enfants.get(i).getMot().equals(a)) { //Si un des noeud contient le mot qu'on essai d'ajouter on n'ajoute rien et on donne juste le noeud suivant
						//System.out.println("Le mot est déjà présent dans les enfants du noeud actuel donc je ne l'ajoute pas");
						noeudActuel = enfants.get(i);
						break;
					}
				}*/
				Noeud<String> n = new Noeud<String>(a);
				noeudActuel.addChild(n);
				//System.out.println("J'ai ajouté le mot '" + a + "' après le mot '" + noeudActuel.getMot() + "' dans l'arbre");
				noeudActuel = n;
			}
			Noeud<String> f = new Noeud<String>("feuille");
			noeudActuel.addChild(f);
			nbLignesAjoutés++;
			System.out.println(nbLignesAjoutés + " lignes ajoutées.");
			//System.out.println("J'ai ajouté le mot '" + f.getMot() + "' après le mot '" + noeudActuel.getMot() + "' dans l'arbre");
		}
		reader.close();
		System.out.println("Fichier mot composé transformé en arbre");
    }
    
    
    //Fonction qui enregistre l'arbre dans un fichier
  	public static void creationFichierMotCompos() throws IOException {
  		
  		//Création du fichier qui va contenir l'arbre
  		File fichierMots = new File(file_path);
  		
  		//fichierMots.getParentFile().mkdirs();
  		
  		//Création de l'arbre
  		Noeud<String> racine = new Noeud<String>("racine");
  		Arbre<String> arbre = new Arbre<String>(racine);
  		
  		putInTree(arbre);
  		
  		//On met l'arbre dans le fichier
  		OutputStream os = new FileOutputStream(fichierMots);
  		ObjectOutputStream oos = new ObjectOutputStream(os);
  		
  		System.out.println("Ecriture dans le fichier : " + fichierMots.getAbsolutePath());
  		
  		oos.writeObject(arbre);
  		
  		oos.close();
  		
  		System.out.println("C'est fini !");
  		
  		
  	}
    
    public static void main(String[] args) throws IOException {
    	// Création d'un Noeud de type String
    	//Noeud<String> racine = new Noeud<String>("racine");
    	 
    	/*
    	// Ajouter un enfant
    	Noeud<String> n2 = new Noeud<String>("Le");
    	racine.addChild(n2);
    	Noeud<String> n3 = new Noeud<String>("La");
    	racine.addChild(n3);
    	Noeud<String> n4 = new Noeud<String>("petit");
    	n2.addChild(n4);
    	Noeud<String> n5 = new Noeud<String>("grand");
    	n2.addChild(n5);
    	Noeud<String> f1 = new Noeud<String>("feuille");
    	n4.addChild(f1);
    	Noeud<String> f2 = new Noeud<String>("feuille");
    	n5.addChild(f2);
    	*/
    	
    	// Création d'un arbre, on lui donne le noeud racine
    	//Arbre<String> arbre = new Arbre<String>(racine);
    	 
    	// Get the pre-order traversal
    	//List<Noeud<String>> preOrder = arbre.getPreOrderTraversal();
    	
    	//putInTree(arbre);
    	
    	//List<Noeud<String>> enfants = racine.getEnfants();
    	//Noeud<String> n1 = enfants.get(2);
    	//ArrayList<ArrayList<Noeud<String>>> chemins = arbre.getPathsFromRacineToAnyLeaf();
    	//n1.getMot();
    	//for(int i = 0; i < 100; i++)
    	//	System.out.println(enfants.get(i).getMot());
    	
    	//String[] mots = {"REV", "Bremerhaven", "fin"};
    	//System.out.println(arbre.motPresent(mots));
    	
    	// Je crée l'arbre et je le met dans un fichier
    	creationFichierMotCompos();
    	
    }
}

/*
 * Il faut penser à enregistrer l'arbre dans un fichier pour y accéder plus rapidement
 * 
 * Il faudrait faire une liste avec les enfants du noeud qu'on regarde et regarder dedans s'il y a le mot qu'on cherche
 * et ainsi on prendrait moins de temps à créer l'arbre.
 * Et une fois qu'on sait qu'il est dedans on cherche dans quel noeud il est et on se place dans ce noeud
 * 
 * Comment stocker l'arbre ??en
 */