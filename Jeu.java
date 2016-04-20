package GameOLife;

import Liste.Liste;
import java.util.Random;


public class Jeu{
	
	private Plateau lePlateau;
	
	/** Constructeur de la classe Jeu
	 * 
	 */
	public Jeu(){
		this.lePlateau = new Plateau(100, 100);
		this.initialiser();
	}
	
	/** Méthode permettant d'initialiser le jeu
	 * 
	 */
	public void initialiser(){
		Liste l = new Liste();
		Random r = new Random();
		for(int i=0; i<500; i++){
			boolean placer = false;
			while(!placer){
				int x = r.nextInt()%100;
				int y = r.nextInt()%100;
				boolean used = false;
				for(int j=0; j<l.size(); j++){
					if(( ((Coordonnee)(l.get(i))).getX()==x )&&( ((Coordonnee)(l.get(i))).getY()==y )){
						used= true;
					}
				}
				if(!used){
					l.add(new Coordonnee(x,y));
					placer= true;
				}
			}
		}
		this.lePlateau.naissance(l);
	}
	
	/** Méthode permettant de jouer
	 * 
	 */
	public void jouer(){
		this.checkLife();
	}
	
	/** Méthode permettant de faire vivre ou mourir une cellule
	 *  
	 */
	public void checkLife(){
		Liste vivantes = new Liste();
		Liste mortes = new Liste();
		for (int i = 0; i < 100; i++){
			for (int j = 0; j < 100; j++){
				if (!this.lePlateau.getCellule(i, j).getEtat()){
					if (this.lePlateau.getVoisins(i,j) == 3){
						vivantes.add(new Coordonnee(i,j));
					}else if((this.lePlateau.getVoisins(i,j) < 2)||(this.lePlateau.getVoisins(i,j) > 3)){
						mortes.add(new Coordonnee(i,j));
					}
				}
			}
		}
		this.lePlateau.naissance(vivantes);
		this.lePlateau.mort(mortes);
	}
	
}
