package GameOfLife;

public class Faction {
	private String nom;
	private String color;
	private int noFaction;
	private Alliance alliance; 
	
	/** Constructeur de la classe Faction
	 * 
	 * @param sonNom - Le nom de la faction
	 * @param saCouleur - La couleur de la faction
	 */
	public Faction(String sonNom, String saCouleur, int sonNumFaction){
		this.nom = sonNom;
		this.color = saCouleur;
		this.noFaction = sonNumFaction;
		this.alliance = null;
	}
	
	/** Méthode permettant d'obtenir le nom de la Faction
	 * 
	 * @return Le nom de la faction
	 */
	public String getNom(){
		return this.nom;
	}
	
	/** Méthode permettant d'obtenir la couleur d'une faction
	 * 
	 * @return La couleur de la faction
	 */
	public String getCouleur(){
		return this.color;
	}
	
	/** Méthode permettant de vérifier si deux faction sont les mêmes
	 * 
	 * @return True si les factions sont égales, sinon false
	 */
	public boolean equals(java.lang.Object o){
		if (o instanceof Faction){
			return this.nom.equals(((Faction)o).nom);
		}
		return false;
	}
	
	public void setAlliance(Alliance sonAlliance){
		this.alliance = sonAlliance;
	}
	
	public void supprimerAlliance(){
		this.alliance = null;
	}
	
	public Alliance getAlliance(){
		return this.alliance;
	}

	public void  setNumFaction(int numero){
		this.noFaction = numero;
	}

	public void supprimerNumFaction(){
		this.noFaction = -1;
	}

	public int getNumFaction(){
		return this.noFaction;
	}
}
