
public class Faction {
	private String nom;
	
	/** Constructeur de la classe Faction
	 * 
	 * @param sonNom - Le nom de la faction
	 */
	public Faction(String sonNom){
		this.nom = sonNom;
	}
	
	public String getNom(){
		return this.nom;
	}
	
	public boolean equals(java.lang.Object o){
		if (o instanceof Faction){
			return this.nom.equals(((Faction)o).nom);
		}
		return false;
	}
}
