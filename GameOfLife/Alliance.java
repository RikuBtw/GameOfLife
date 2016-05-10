package GameOfLife;

import java.util.List;

public class Alliance {
	private List<Faction> groupe;
	private int nbJoueursMax;
	private int dureeVie;

	/** Constructeur de la classe Alliance
	 *
	 * @param factions - Liste des factions composant l'alliance
	 * @param leNombreDeJoueurs - Nombre de joueur que l'on souhaite utiliser comme maximum
	 */
	public Alliance(List<Faction> factions, int leNombreDeJoueurs){
		this.groupe = factions;
		this.nbJoueursMax = leNombreDeJoueurs;
		for (int i = 0; i < this.groupe.size(); i++){
			this.groupe.get(i).setAlliance(this);
		}
		this.dureeVie = 500;
	}

	/** Méthode permettant d'ajouter une faction à l'alliance
	 *
	 * @param faction - Faction que l'on souhaite ajouter
	 */
	public void addFaction(Faction faction){
		if (this.groupe.size() < this.nbJoueursMax){
			this.groupe.add(faction);
			this.groupe.get(this.groupe.size()-1).setAlliance(this);
		}
	}

	/** Méthode supprimant la faction de l'alliance
	 *
	 * @param faction - Faction que l'on souhaite supprimer
	 */
	public void deleteFaction(Faction faction){
		this.groupe.remove(faction);
		faction.supprimerAlliance();
	}

	/** Méthode supprimant toutes les factions de l'alliance
	 *
	 */
	public void deleteAll(){
		for(int i = 0; i < this.groupe.size(); i++) {
			this.groupe.remove(groupe.get(i));
			groupe.get(i).supprimerAlliance();
		}
	}

	/** Accesseur renvoyant la liste des factions de l'alliance
	 *
	 * @return Liste des factions de l'alliance
     */
	public List<Faction> getGroupe(){
		return this.groupe;
	}

	/** Méthode renvoyant si le groupe existe, c'est à dire possède deux membres ou plus.
	 *
	 * @return True si le groupe existe, false sinon.
	 */
	public boolean checkExistence(){
		if (this.groupe.size() < 2){
			return false;
		}
		return true;
	}

	/** Accesseur permettant d'obtenir la durée de vie de l'alliance
	 *
	 * @return La durée de vie de l'alliance
     */
	public int getDureeVie(){
		return dureeVie;
	}

	/** Accesseur permettant d'attribuer une durée de vie à l'alliance
	 *
	 * @param duree - La Durée que l'on attribut à l'alliance
     */
	public void setDureeVie(int duree){
		this.dureeVie = duree;
	}

	/** Méthode d'égalité
     */
	public boolean equals(Object o){
		if (o instanceof Faction){
			if(this.groupe.size() != ((Alliance)(o)).groupe.size()){
				return false;
			}else{
				boolean testAlliance = true;
				for(int i=0; i<this.groupe.size(); i++){
					if(this.groupe.get(i).equals(((Alliance)(o)).groupe.get(i))){
						testAlliance = false;
					}
				}
				return testAlliance;
			}
		}
		return false;
	}

	/** Accesseur donnant la liste des factions de l'alliance
	 *
	 * @return La liste des factions de l'alliance
     */
	public List<Faction> getFactions(){
		return this.groupe;
	}
}
