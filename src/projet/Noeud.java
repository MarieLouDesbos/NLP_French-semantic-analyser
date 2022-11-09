package projet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Noeud<String> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mot;
    private List<Noeud<String>> enfant;
    private Noeud<String> parent;

    public Noeud(String mot) {
        this.mot = mot;
        this.enfant = new ArrayList<Noeud<String>>();
    }

    /**
     * Initialize a Noeud with another Noeud's Mot.
     * This does not copy the Noeud's Enfant.
     *
     * @param Noeud The Noeud whose Mot is to be copied.
     */
    public Noeud(Noeud<String> Noeud) {
        this.mot = Noeud.getMot();
        enfant = new ArrayList<Noeud<String>>();
    }

    /**
     *
     * Add a child to this Noeud.
     *
     * @param child child Noeud
     */
    public void addChild(Noeud<String> child) {
        child.setParent(this);
        enfant.add(child);
    }

    /**
     *
     * Add a child Noeud at the given index.
     *
     * @param index The index at which the child has to be inserted.
     * @param child The child Noeud.
     */
    public void addChildAt(int index, Noeud<String> child) {
        child.setParent(this);
        this.enfant.add(index, child);
    }

    public void setEnfant(List<Noeud<String>> Enfant) {
        for (Noeud<String> child : Enfant)
            child.setParent(this);

        this.enfant = Enfant;
    }

    /**
     * Supprimer tous les enfants d'un noeud
     */
    public void removeEnfants() {
        this.enfant.clear();
    }

    /**
     *
     * Supprime l'enfant a un index donné
     *
     * @param index The index at which the child has to be removed.
     * @return the removed Noeud.
     */
    public Noeud<String> removeChildAt(int index) {
        return enfant.remove(index);
    }

    /**
     * Remove given child of this Noeud.
     *
     * @param childToBeDeleted the child Noeud to remove.
     * @return <code>true</code> if the given Noeud was a child of this Noeud and was deleted,
     * <code>false</code> otherwise.
     */
    public boolean removeChild(Noeud<String> childToBeDeleted) {
        List<Noeud<String>> list = getEnfants();
        return list.remove(childToBeDeleted);
    }

    public String getMot() {
        return this.mot;
    }

    public void setMot(String mot) {
        this.mot = mot;
    }

    public Noeud<String> getParent() {
        return this.parent;
    }

    public void setParent(Noeud<String> parent) {
        this.parent = parent;
    }

    public List<Noeud<String>> getEnfants() {
        return this.enfant;
    }

    public Noeud<String> getChildAt(int index) {
        return enfant.get(index);
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj)
            return false;

        if (obj instanceof Noeud) {
            if (((Noeud<?>) obj).getMot().equals(this.mot))
                return true;
        }

        return false;
    }

  
}
