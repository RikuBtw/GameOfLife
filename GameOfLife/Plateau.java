package GameOfLife;

import java.util.List;
import java.util.ArrayList;

public class Plateau{

	private int tailleHorizontale;
	private int tailleVerticale;
	private Cellule[][] plateau;

	/** Constructeur du plateau
	 *
	 * @param saTailleHorizontale
	 * @param saTailleVerticale
	 */
	public Plateau(int saTailleHorizontale, int saTailleVerticale){
		this.tailleHorizontale = saTailleHorizontale;
		this.tailleVerticale = saTailleVerticale;
		this.plateau = new Cellule[this.tailleVerticale][this.tailleHorizontale];
		this.initialiser();
	}

	/** Méthode initialisant le plateau de cellules
	 *
	 */
	public void initialiser(){
		for(int i=0; i<this.tailleVerticale; i++){
			for(int j=0; j<this.tailleHorizontale; j++){
				this.plateau[i][j] = new Cellule();
			}
		}
	}

	/** Accesseur renvoyant la taille horizontale du plateau
	 *
	 * @return tailleVerticale
	 */
	public int getTailleHorizontale(){
		return this.tailleHorizontale;
	}

	/** Accesseur renvoyant la taille verticale du plateau
	 *
	 * @return tailleHorizontale
	 */
	public int getTailleVerticale(){
		return this.tailleVerticale;
	}

	/** Accesseur renvoyant la cellule du plateau donnée en entrée.
	 *
	 * @param x - coordonnée x
	 * @param y - coordonnée y
	 * @return Cellule de coordonnées x, y
	 */
	public Cellule getCellule(int x, int y){
		return this.plateau[x][y];
	}

	/** Accesseur permettant d'obtenir les voisins d'une cellule
	 *
	 * @param x - La coordonnée x de la matrice
	 * @param y - La coordonnée y de la matrice
	 * @return nbVoisins - Le nombre de voisins
	 */
	public List<Coordonnee> getVoisins(int x, int y){
		//Les voisins d'une cellule sont stockés dans une liste de coordonnées, nommée "voisins"
		List<Coordonnee> voisins = new ArrayList<Coordonnee>();
		//On vérifie les cellules entourant celle choisie
		for(int i=(x-1); i <= (x+1); i++){
			for (int j = (y-1); j <= (y+1); j++){
				//La cellule doit rester dans l'aire de jeu
				if((i >= 0) && (i < this.tailleVerticale) && (j >= 0) && (j < this.tailleHorizontale) && (!((i == x)&&(j == y)))){
					//Si la cellule est viavante, on l'ajoute aux voisins.
					if (this.plateau[i][j].getEtat()){
						voisins.add(new Coordonnee(i,j));
					}
				}
			}
		}
		return voisins;
	}

	/** Accesseur permettant de déterminer les alliés d'une cellule
	 *
	 * @param x - Coordonnée x de la cellule
	 * @param y - Coordonnée y de la cellule
	 * @return Liste des alliés de la cellule
     */
	public List<Coordonnee> getAllies(int x, int y){
		//Les alliés d'une cellule sont stockés dans une liste de coordonnées, nommée "allies"
		List<Coordonnee> allies = new ArrayList<Coordonnee>();
		//Si la cellule existe, on lance la détection
		if (this.plateau[x][y].getEtat()){
			//On vérifie les cellules entourant celle choisie
			for(int i=(x-1); i <= (x+1); i++){
				for (int j = (y-1); j <= (y+1); j++){
					//La cellule doit rester dans l'aire de jeu
					if((i >= 0) && (i < this.tailleVerticale) && (j >= 0) && (j < this.tailleHorizontale) && (!((i == x)&&(j == y)))){
						//Si la coordonnée voisine possède une cellule vivante, on vérifie si elle est alliée
						if (this.plateau[i][j].getEtat()){
							//Si une des deux cellules vérifiées ne possède pas d'alliance, on vérifie leur appartenance à la même faction
							if (this.plateau[x][y].getFaction().getAlliance() == null || this.plateau[i][j].getFaction().getAlliance() == null){
								//Si les faction sont égales, elles sont alliées.On les ajout alors à la liste.
								if (this.plateau[x][y].getFaction().equals(this.plateau[i][j].getFaction())) {
									allies.add(new Coordonnee(i, j));
								}
							}else
							//Si elles ont une alliance chacune, on les compares.
							if (this.plateau[x][y].getFaction().getAlliance() != null && this.plateau[i][j].getFaction().getAlliance() != null){
								//Si leurs alliances sont similaires,on ajoute la cellule voisine à la liste d'alliés
								if(this.plateau[x][y].getFaction().getAlliance().equals(this.plateau[i][j].getFaction().getAlliance())){
									allies.add(new Coordonnee(i,j));
								}
							}
						}
					}
				}
			}
		}
		return allies;
	}

	/** Accesseur permettant de déterminer les ennemis d'une cellule
	 *
	 * @param x - Coordonnée x de la cellule
	 * @param y - Coordonnée y de la cellule
     * @return Liste des ennemis de la cellule
     */
	public List<Coordonnee> getEnnemis(int x, int y) {
		//Les ennemis d'une cellule sont stockés dans une liste de coordonnées, nommée "ennemis"
		List<Coordonnee> ennemis = new ArrayList<Coordonnee>();
		//Si la cellule existe, on lance la détection
		if (this.plateau[x][y].getEtat()){
			//On vérifie les cellules entourant celle choisie
			for (int i = (x - 1); i <= (x + 1); i++) {
				for (int j = (y - 1); j <= (y + 1); j++) {
					//La cellule doit rester dans l'aire de jeu
					if ((i >= 0) && (i < this.tailleVerticale) && (j >= 0) && (j < this.tailleHorizontale) && (!((i == x) && (j == y)))) {
						//Si la cellule choisie n'est pas sans faction (et donc vide), on lance la comparaison des factions pour toutes ses voisines vivantes
						if (this.plateau[x][y].getFaction() != null && this.plateau[i][j].getFaction() != null) {
							//On teste l'égalité des deux cellules, celle choisie et une de celles qui l'entoure. Si elles ne sont pas égales, on vérifie leurs alliances
							if (!(this.plateau[x][y].getFaction()).equals(this.plateau[i][j].getFaction())) {
								//On vérifie si les cellules ont chacunes une alliance, si ce n'est pas le cas elles sont ennemies.
								if (this.plateau[x][y].getFaction().getAlliance() != null && this.plateau[i][j].getFaction().getAlliance() != null) {
									//Si les alliances sont différentes, elles sont ennemies.
									if (!(this.plateau[x][y].getFaction().getAlliance()).equals(this.plateau[i][j].getFaction().getAlliance())) {
										ennemis.add(new Coordonnee(i, j));
									}
								}else{
									ennemis.add(new Coordonnee(i, j));
								}
							}
						}
					}

				}
			}
		}
		return ennemis;
	}

	/** Méthode permettant la naissance des cellules devant vivre ainsi que l'attribution de leur faction.
	 *
	 * @param vivantes - Liste des coordonnées des cellules devant vivre
	 * @param factions - Liste de faction que doivent prendre les cellules
	 */
	public void naissance(List<Coordonnee> vivantes, List<Faction> factions){
		//Pour toutes les cellules vivantes, on va associer à la cellule une faction. On appelera ensuite la fonction setLife() qui finira l'opération
		for (int i = 0; i < vivantes.size(); i++){
			this.plateau[vivantes.get(i).getX()][vivantes.get(i).getY()].setLife(factions.get(i));
		}

	}

	/** Méthode permettant la mort des cellules devant mourir.
	 *
	 * @param liste - La liste des cellules devant mourir
	 */
	public void mort(List<Coordonnee> liste){
		//Pour toute la liste, on va appeller la fonction freeCellule() qui va tuer la cellule.
		for (int i = 0; i < liste.size(); i++){
			this.plateau[liste.get(i).getX()][liste.get(i).getY()].freeCellule();
		}
	}

	/** Redéfinition de la méthode String toString()
	 *
     */
	public String toString(){
		String out = "";
		for (int i = 0; i < tailleVerticale; i++){
			for (int j = 0; j< tailleHorizontale; j++ ){
				out+=this.plateau[i][j].toString();
			}
			out+="\n";
		}
		return out;
	}

}
