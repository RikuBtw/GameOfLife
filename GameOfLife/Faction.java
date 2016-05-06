package GameOfLife;

public class Faction {
	private String nom;
	private String color;
	private int noFaction;
	private Alliance alliance; 
	private int score;
	
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
		this.score = 0;
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

	/** Accesseur permettant d'affecter une alliance à la faction
	 *
	 * @param sonAlliance - L'alliance que l'on veut affecter
     */
	public void setAlliance(Alliance sonAlliance){
		this.alliance = sonAlliance;
	}

	/** Méthode permettant de supprimer l'alliance liée à la faction
	 *
	 */
	public void supprimerAlliance(){
		this.alliance = null;
	}

	/** Accesseur donnant l'alliance liée à la faction
	 *
	 * @return L'alliance de la faction
     */
	public Alliance getAlliance(){
		return this.alliance;
	}

	/** Accesseur permettant d'affecter un numéro à la faction
	 *
	 * @param numero - Le numéro que l'on souhaite attribuer à la faction
     */
	public void  setNumFaction(int numero){
		this.noFaction = numero;
	}

	/** Méthode permettant de supprimer le numéro lié à la faction
	 *
	 */
	public void supprimerNumFaction(){
		this.noFaction = -1;
	}

	/** Accesseur donnant le numéro de la faction
	 *
	 * @return Le numéro de la faction, -1 si inexistante
     */
	public int getNumFaction(){
		return this.noFaction;
	}
	
	/** Accesseur attribuant le score d'une faction
	 * 
	 * @param leScore - Le score de la faction
	 */
	public void setScore(int leScore){
		this.score = leScore;
	}
	
	public int getScore(){
		return this.score;
	}
}
