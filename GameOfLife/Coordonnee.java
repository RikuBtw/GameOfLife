package GameOfLife;

public class Coordonnee{
	protected int x;
	protected int y;

	/** Constructeur associant les coordonnées à leurs valeurs.
	 * 
	 * @param x coordonnée X
	 * @param y coordonnée Y
	 */
	public Coordonnee (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/** Accesseur qui permet de récupérer la coordonnée en x
	 */
	public int getX(){
		return this.x;
	}
	
	/** Accesseur qui permet de récupérer la coordonnée en y
	 */
	public int getY(){
		return this.y;
	}
}
