
public class Cellule {
	private boolean etat;
	
	/** Constructeur de la classe Cellule
	 * 
	 */
	
	public Cellule(){
		this.etat = false;
	}
	
	/** Accesseur retournant l'état de la cellule
	 * 
	 * @return Etat de la cellule
	 */
	public boolean getEtat(){
		return this.etat;
	}
	
	/** Selecteur donnant vie à la cellule
	 * 
	 */
	public void setLife(){
		this.etat = true;
	}
	
	
	/** Méthode tuant une cellule
	 * 
	 */
	public void freeCellule(){
		this.etat = false;
	}
	
	
}
