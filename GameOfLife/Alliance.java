package GameOfLife;

import java.util.ArrayList;
import java.util.List;

public class Alliance {
	private List<Faction> groupe;
	private int nbJoueursMax;
	
	/** Constructeur de la classe Alliance
	 * 
	 * @param leNombreDeJoueurs - Nombre de joueur que l'on souhaite utiliser comme maximum
	 */
	public Alliance(int leNombreDeJoueurs){
		this.groupe = new ArrayList<Faction>();
		this.nbJoueursMax = leNombreDeJoueurs;
	}
	
	/** Méthode permettant d'ajouter une faction à l'alliance
	 * 
	 * @param faction - Faction que l'on souhaite ajouter
	 */
	public void addFaction(Faction faction){
		if (this.groupe.size() < this.nbJoueursMax){
			this.groupe.add(faction);
		}
	}
	
	/** Méthode supprimant la faction de l'alliance
	 * 
	 * @param faction - Faction que l'on souhaite supprimer
	 */
	public void deleteFaction(Faction faction){
		this.groupe.remove(faction);
	}
	
	/** Méthode renvoyant si le groupe existe, c'est à dire possède plus de deux membres.
	 * 
	 * @return True si le groupe existe, false sinon.
	 */
	public boolean checkExistence(){
		if (this.groupe.size() < 2){
			return false;
		}
		return true;
		}
}
