package GameOfLife;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Jeu{

	private Plateau lePlateau;
	private List<Faction> factions;
	private int nbFactions;
	private List<Alliance> alliances;
	//L'ensemble des cellules vivantes
	List<Coordonnee> vivantes = new ArrayList<Coordonnee>();
	//L'ensemble des cellules mortes
	List<Coordonnee> mortes = new ArrayList<Coordonnee>();
	//La faction qui prend la cellule
	List<Faction> appartenanceFaction = new ArrayList<Faction>();

	/** Constructeur de la classe Jeu
	 *
	 */
	public Jeu(int leNbJoueurs){
		this.lePlateau = new Plateau(20, 20); // 20x20 POUR LE TEST
		this.nbFactions = leNbJoueurs;
		this.factions = initialiserJoueurs(this.nbFactions);
		this.initialiser();
		this.alliances = new ArrayList<Alliance>();
	}

	/** Méthode permettant d'initialiser le jeu
	 *
	 */
	public void initialiser(){
		for(int i=0; i<20; i++){
			int x = Math.abs((int)(Math.random()*this.lePlateau.getTailleVerticale()));
			int y = Math.abs((int)(Math.random()*this.lePlateau.getTailleHorizontale()));
			int f = Math.abs((int)(Math.random()*this.nbFactions));
			vivantes.add(new Coordonnee(x, y));
			appartenanceFaction.add(this.factions.get(f));
		}
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
			System.out.print("Nom joueur "+i+1+":");
			String nom = sc.nextLine();
			System.out.print("Sa couleur:");
			String couleur = sc.nextLine();
			joueurs.add(new Faction(nom, couleur, i));
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
	public void checkLife() {
		
		for (int i = 0; i < this.lePlateau.getTailleVerticale(); i++) {
			for (int j = 0; j < this.lePlateau.getTailleVerticale(); j++) {
				if (!this.lePlateau.getCellule(i, j).getEtat()) {
					List<Coordonnee> voisins = this.lePlateau.getVoisins(i, j);
					if (voisins.size() == 3) {
						boolean memeFaction = true;
						boolean memeAlliance = true;
						Faction faction = this.lePlateau.getCellule(voisins.get(0).getX(), voisins.get(0).getY()).getFaction();
						for (int c = 1; c < voisins.size(); c++) {
							if (!faction.equals(this.lePlateau.getCellule(voisins.get(c).getX(), voisins.get(c).getY()).getFaction())) {
								memeFaction = false;
							} else if (!faction.getAlliance().equals(this.lePlateau.getCellule(voisins.get(c).getX(), voisins.get(c).getY()).getFaction().getAlliance())) {
								memeAlliance = false;
							}
						}
						//Si les cellules sont de la même faction, on les insère directement dans la liste de celles devant vivre
						if (memeFaction) {
							vivantes.add(new Coordonnee(i, j));
							appartenanceFaction.add(this.lePlateau.getCellule(voisins.get(0).getX(), voisins.get(0).getY()).getFaction());
							//Sinon on vérifie qui de l'alliance va possèder la cellule
						} else if (memeAlliance) {
							int proba = Math.abs((int) (Math.random() * 3));
							vivantes.add(new Coordonnee(i, j));
							appartenanceFaction.add(this.lePlateau.getCellule(voisins.get(proba).getX(), voisins.get(proba).getY()).getFaction());
						}
					} else if (voisins.size() != 2) {
						mortes.add(new Coordonnee(i, j));
					}
				}
			}
		}
		this.lePlateau.naissance(vivantes, appartenanceFaction);
		this.lePlateau.mort(mortes);
	}

	/** Méthode permettant de vérifier les conditions de guerre
	 *
	 */
	public void checkWar(){
		for (int i = 0; i < this.lePlateau.getTailleVerticale(); i++){
			for (int j = 0; j < this.lePlateau.getTailleHorizontale(); j++){
				List<Coordonnee> ennemis = this.lePlateau.getEnnemis(i, j);
				for (int k=0; k<ennemis.size(); k++){
					if((this.lePlateau.getVoisins(i, j)).size()< this.lePlateau.getVoisins(ennemis.get(k).getX() , ennemis.get(k).getY()).size()){
						(this.lePlateau.getCellule(i, j)).freeCellule();
					}
				}
			}
		}
	}

	public String toString(){
		return this.lePlateau.toString();
	}

}
