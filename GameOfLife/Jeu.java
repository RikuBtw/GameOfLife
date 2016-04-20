package GameOfLife;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Jeu{
	
	private Plateau lePlateau;
	private List<Faction> factions;
	private int nbFactions;
	
	/** Constructeur de la classe Jeu
	 * 
	 */
	public Jeu(int leNbJoueurs){
		this.lePlateau = new Plateau(100, 100);
		this.initialiser();
		this.nbFactions = leNbJoueurs;
		this.factions = initialiserJoueurs(this.nbFactions);
	}
	
	/** Méthode permettant d'initialiser le jeu
	 * 
	 */
	public void initialiser(){
		List<ArrayList<Coordonnee>> liste = new ArrayList<ArrayList<Coordonnee>>();
		for(int j=0; j<this.nbFactions; j++){
			liste.add(new ArrayList<Coordonnee>());
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
					for (int k=0; k<liste.get(j).size(); k++){
						if((liste.get(j).get(k).getX()==x)&&(liste.get(j).get(k).getY()==y)){
							used = true;
						}
					}
				}
				if(!used){
					liste.get(z).add(new Coordonnee(x,y));
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
	public List<Faction> initialiserJoueurs(int nbJoueurs){
		List<Faction> joueurs = new ArrayList<Faction>();
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
		this.checkWar();
	}
	
	/** Méthode permettant de faire vivre ou mourir une cellule
	 *  
	 */
	public void checkLife(){
		
		List<ArrayList<Coordonnee>> vivantes = new ArrayList<ArrayList<Coordonnee>>();
		for(int z=0; z<this.nbFactions; z++){
			vivantes.add(new ArrayList<Coordonnee>());
		}
		List<Coordonnee> mortes = new ArrayList<Coordonnee>();
		for (int i = 0; i < 100; i++){
			for (int j = 0; j < 100; j++){
				if (!this.lePlateau.getCellule(i, j).getEtat()){
					if ((this.lePlateau.getVoisins(i,j)).size() == 3){
						Faction f = (this.lePlateau.getCellule(i, j)).getFaction();
						boolean ajouter = false;
						int k = 0;
						while((!ajouter)&&(k<this.factions.size())){
							if(this.factions.get(k).equals(f)){
								vivantes.get(k).add(new Coordonnee(i,j));
								ajouter = true;
							}else{
								k++;
							}
						}
					}else if(((this.lePlateau.getVoisins(i,j)).size() < 2)||((this.lePlateau.getVoisins(i,j)).size() > 3)){
						mortes.add(new Coordonnee(i,j));
					}
				}
			}
		}
		this.lePlateau.naissance(vivantes, this.factions);
		this.lePlateau.mort(mortes);
	}
	
	/** Méthode permettant de vérifier les conditions de guerre
	 * 
	 */
	public void checkWar(){
		for (int i = 0; i < 100; i++){
			for (int j = 0; j < 100; j++){
				List<Coordonnee> ennemis = this.lePlateau.getEnnemis(i, j);
				for (int k=0; k<ennemis.size(); k++){
					if((this.lePlateau.getVoisins(i, j)).size()< this.lePlateau.getVoisins(ennemis.get(k).getX() , ennemis.get(k).getY()).size()){
						(this.lePlateau.getCellule(i, j)).freeCellule();
					}
				}
			}
		}
	}
	
}
