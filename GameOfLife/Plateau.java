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
	
	/** Accesseur permettant d'obtenir le nombre de voisins d'une cellule
	 * 
	 * @param x - La coordonnée x de la matrice
	 * @param y - La coordonnée y de la matrice
	 * @return nbVoisins - Le nombre de voisins
	 */
	public List<Coordonnee> getVoisins(int x, int y){
		//System.out.println("Get Voisin ("+x+", "+y+")");
		List<Coordonnee> voisins = new ArrayList<Coordonnee>();
		for(int i=(x-1); i <= (x+1); i++){
			for (int j = (y-1); j <= (y+1); j++){
				if((i >= 0) && (i < this.tailleVerticale) && (j >= 0) && (j < this.tailleHorizontale) && (!((i == x)&&(j == y)))){
		//			System.out.println(i+" "+j);
					if (this.plateau[x][y].getFaction()!=null){
						if((this.plateau[x][y].getFaction()).equals(this.plateau[i][j].getFaction())){
							voisins.add(new Coordonnee(i,j));
						}
					}
				}
			}
		}
		return voisins;
	}
	
	public List<Coordonnee> getEnnemis(int x, int y){
		List<Coordonnee> ennemis = new ArrayList<Coordonnee>();
		for(int i=(x-1); i <= (x+1); i++){
			for (int j = (y-1); j <= (y+1); j++){
				if((i >= 0) && (i < this.tailleVerticale) && (j >= 0) && (j < this.tailleHorizontale) && (!((i == x)&&(j == y)))){
					if (this.plateau[x][y].getFaction()!=null){
						if(!(this.plateau[x][y].getFaction()).equals(this.plateau[i][j].getFaction())){
							ennemis.add(new Coordonnee(i,j));
						}
					}
				}
			}
		}
		return ennemis;
	}
	
	/** Méthode permettant la naissance des cellules devant vivre.
	 * 
	 * @param liste - La liste des cellules devant vivre
	 */
	public void naissance(List< ArrayList<Coordonnee>> liste, List<Faction> factions){
		for (int i = 0; i < liste.size(); i++){
			for (int j=0; j< liste.get(i).size(); j++ ){
				this.plateau[liste.get(i).get(j).getX()][liste.get(i).get(j).getY()].setLife(factions.get(i));
			}
		}
	}
	
	/** Méthode permettant la mort des cellules devant mourir.
	 * 
	 * @param liste - La liste des cellules devant mourir
	 */
	public void mort(List<Coordonnee> liste){
		for (int i = 0; i < liste.size(); i++){
			this.plateau[liste.get(i).getX()][liste.get(i).getY()].freeCellule();
		}
	}
	
	public String toString(){
		String out = "";
		for (int i = 0; i < tailleHorizontale; i++){
			for (int j=0; j< tailleVerticale; j++ ){
				out+=this.plateau[i][j].toString();
			}
			out+="\n";
		}
		return out;
	}

}
