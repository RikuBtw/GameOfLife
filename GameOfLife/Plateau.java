package GameOfLife;

import Liste.Liste;

public class Plateau{
	
	private int tailleHorizontale;
	private int tailleVerticale;
	private Cellule[][] plateau;

	/** Constructeur du plateau
	 * 
	 * @param tailleHorizontale
	 * @param tailleVerticale
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
	
	/** Accesseur permettant d'obtenir le nombre de voisins d'une cellule
	 * 
	 * @param x - La coordonnée x de la matrice
	 * @param y - La coordonnée y de la matrice
	 * @return nbVoisins - Le nombre de voisins
	 */
	public int getVoisins(int x, int y){
		int nbVoisins = 0;
		
		for(int i=(x-1); i <= (x+1); i++){
			for (int j = (y-1); j <= (y+1); j++){
				if((i >= 0) && (i < this.tailleVerticale) && (j >= 0) && (j < this.tailleHorizontale) && (!((i == x)&&(j == y)))){
					if((this.plateau[x][y].getFaction()).equals(this.plateau[i][j].getFaction())){
						nbVoisins++;
					}
				}
			}
		}
		return nbVoisins;
	}
	
	/** Méthode permettant la naissance des cellules devant vivre.
	 * 
	 * @param liste - La liste des cellules devant vivre
	 */
	public void naissance(Liste liste, Liste factions){
		for (int i = 0; i < liste.size(); i++){
			for (int j=0; j< ((Liste)(liste.get(i))).size(); j++ ){
				this.plateau[((Coordonnee)(((Liste)(liste.get(i))).get(j))).getX()][((Coordonnee)(((Liste)(liste.get(i))).get(j))).getY()].setLife((Faction)factions.get(i));
			}
		}
	}
	
	/** Méthode permettant la mort des cellules devant mourir.
	 * 
	 * @param liste - La liste des cellules devant mourir
	 */
	public void mort(Liste liste){
		for (int i = 0; i < liste.size(); i++){
			this.plateau[((Coordonnee)(liste.get(i))).getX()][((Coordonnee)(liste.get(i))).getY()].freeCellule();
		}
	}
	

}
