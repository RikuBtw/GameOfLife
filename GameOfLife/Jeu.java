package GameOfLife;

import Liste.Liste;
import java.util.Random;
import java.util.Scanner;


public class Jeu{
	
	private Plateau lePlateau;
	private Liste factions;
	private int nbfactions;
	
	/** Constructeur de la classe Jeu
	 * 
	 */
	public Jeu(int leNbJoueurs){
		this.lePlateau = new Plateau(100, 100);
		this.initialiser();
		this.nbfactions = leNbJoueurs;
		this.factions = initialiserJoueurs(this.nbfactions);
	}
	
	/** Méthode permettant d'initialiser le jeu
	 * 
	 */
	public void initialiser(){
		Liste liste = new Liste();
		for(int j=0; j<this.nbfactions; j++){
			liste.add(new Liste());
		}
		Random r = new Random();
		for(int i=0; i<500; i++){
			boolean placer = false;
			while(!placer){
				int x = r.nextInt()%100;
				int y = r.nextInt()%100;
				int z = r.nextInt()%liste.size();
				boolean used = false;
				for(int j=0; j<liste.size(); j++){
					if(( ((Coordonnee)(liste.get(i))).getX()==x )&&( ((Coordonnee)(liste.get(i))).getY()==y )){
						used= true;
					}
				}
				if(!used){
					((Liste)(liste.get(z))).add(new Coordonnee(x,y));
					placer= true;
				}
			}
		}
		this.lePlateau.naissance(liste, this.factions);
	}
	
	/** Méthode permettant l'initialisation des joueurs
	 *
	 * @param nbJoueurs - Le nombre de joueurs
	 * @return Une liste de joueurs
	 */
	public Liste initialiserJoueurs(int nbJoueurs){
		Liste joueurs = new Liste();
		Scanner sc = new Scanner(System.in);
		for(int i=0; i<nbJoueurs; i++){
			String nom = sc.nextLine();
			String couleur = sc.nextLine();
			joueurs.add(new Faction(nom,couleur));	
		}
		sc.close();
		return joueurs;
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
		for(int z=0; z<this.nbfactions; z++){
			vivantes.add(new Liste());
		}
		Liste mortes = new Liste();
		for (int i = 0; i < 100; i++){
			for (int j = 0; j < 100; j++){
				if (!this.lePlateau.getCellule(i, j).getEtat()){
					if (this.lePlateau.getVoisins(i,j) == 3){
						Faction f = (this.lePlateau.getCellule(i, j)).getFaction();
						boolean ajouter = false;
						int k = 0;
						while(!ajouter){
							if( ((Faction)(this.factions.get(k))).equals(f) ){
								((Liste)(vivantes.get(k))).add(new Coordonnee(i,j));
							}
						}
					
					}else if((this.lePlateau.getVoisins(i,j) < 2)||(this.lePlateau.getVoisins(i,j) > 3)){
						mortes.add(new Coordonnee(i,j));
					}
				}
			}
		}
		this.lePlateau.naissance(vivantes, this.factions);
		this.lePlateau.mort(mortes);
	}
	
}
