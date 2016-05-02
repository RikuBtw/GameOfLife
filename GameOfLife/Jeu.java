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
		this.lePlateau = new Plateau(100, 100); // 100x100 POUR LE TEST
		this.nbFactions = leNbJoueurs;
		this.factions = initialiserJoueurs(this.nbFactions);
		this.initialiser();
		this.alliances = new ArrayList<Alliance>();
	}

	/** Méthode permettant d'initialiser le jeu
	 *
	 */
	public void initialiser(){
		// LA VALEUR DU FOR CONTROLE LE NOMBRE DE CELLULES ALEATOIRES. ENVIRON 20*AXE X POUR UNE GENERATION CORRECTE
		for(int i=0; i<2000; i++){
			int x = Math.abs((int)(Math.random()*this.lePlateau.getTailleVerticale()));
			int y = Math.abs((int)(Math.random()*this.lePlateau.getTailleHorizontale()));
			int f = Math.abs((int)(Math.random()*this.nbFactions));
			vivantes.add(new Coordonnee(x, y));
			appartenanceFaction.add(this.factions.get(f));
		}
		this.lePlateau.naissance(vivantes, appartenanceFaction);
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
			System.out.print("Nom joueur "+(i+1)+":");
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

		//Permet de lancer les vérifications de guerre
		this.checkWar();
		//Règles de vie classiques
		this.checkLife();

	}

	/** Méthode permettant de faire vivre ou mourir une cellule
	 *
	 */
	public void checkLife() {
		//On reset les listes permettant la mort et création
		vivantes.clear();
		mortes.clear();
		appartenanceFaction.clear();
		//On va vérifier les règles pour chaque cellule du plateau
		for (int i = 0; i < this.lePlateau.getTailleVerticale(); i++) {
			for (int j = 0; j < this.lePlateau.getTailleVerticale(); j++) {
				//On stocke les voisins de la cellule en cours dans la variable voisins pour alléger le code
				List<Coordonnee> voisins = this.lePlateau.getVoisins(i, j);
				//Si la cellule est vide, on va appliquer les règles de voisinage, sinon, les règles d'alliance.
				if (!this.lePlateau.getCellule(i, j).getEtat()) {
					//Si la cellule a 3 voisins, on vérifie son appartenance àune alliance ou une même faction et on la créé, sinon, elle reste morte.
					if (voisins.size() == 3) {
						boolean memeFaction = true;
						boolean memeAlliance = true;
						//On stocke la faction du premier voisin dans la variable faction pour alléger le code.
						Faction faction = this.lePlateau.getCellule(voisins.get(0).getX(), voisins.get(0).getY()).getFaction();
						//Pour les 2 voisins restant
						for (int c = 1; c < voisins.size(); c++) {
							//Si un des deux n'est pas de la même faction que le premier, le booléen memeFaction passe à false
							if (this.lePlateau.getCellule(voisins.get(c).getX(), voisins.get(c).getY()).getFaction() == null || !faction.equals(this.lePlateau.getCellule(voisins.get(c).getX(), voisins.get(c).getY()).getFaction())) {
								memeFaction = false;
							} else
							//Si un des deux voisins n'a pas la même alliance, on passe le booléen memeAlliance à false
							if (this.lePlateau.getCellule(voisins.get(c).getX(), voisins.get(c).getY()).getFaction().getAlliance() == null || !faction.getAlliance().equals(this.lePlateau.getCellule(voisins.get(c).getX(), voisins.get(c).getY()).getFaction().getAlliance())) {
								memeAlliance = false;
							}
						}
						//Si les cellules sont de la même faction, on les insère directement dans la liste de celles devant vivre
						if (memeFaction) {
							vivantes.add(new Coordonnee(i, j));
							appartenanceFaction.add(this.lePlateau.getCellule(voisins.get(0).getX(), voisins.get(0).getY()).getFaction());
						} else
						//Sinon on vérifie qui de l'alliance va possèder la cellule, via une probabilité de 33%
						if (memeAlliance) {
							int proba = Math.abs((int) (Math.random() * 3));
							vivantes.add(new Coordonnee(i, j));
							appartenanceFaction.add(this.lePlateau.getCellule(voisins.get(proba).getX(), voisins.get(proba).getY()).getFaction());
						}
					}else
					// La cellule meurt si elle est vide et n'a pas 3 voisins
					{
						mortes.add(new Coordonnee(i, j));
					}

				}else
				//Si elle est déjà existante, on vérifie si la cellule a 2 ou 3 voisins alliés. Si oui, elle vit, sinon elle meurt.
				if(this.lePlateau.getCellule(i, j).getEtat()) {
					List<Coordonnee> allies = this.lePlateau.getAllies(i, j);
					if (allies.size() == 2 || allies.size() == 3) {
						vivantes.add(new Coordonnee(i, j));
						appartenanceFaction.add(this.lePlateau.getCellule(allies.get(0).getX(), allies.get(0).getY()).getFaction());
					}else{
						mortes.add(new Coordonnee(i, j));
					}
				}
			}
		}
		//On lance enfin les morts et naissances.
		this.lePlateau.mort(mortes);
		this.lePlateau.naissance(vivantes, appartenanceFaction);

	}

	/** Méthode permettant de vérifier les conditions de guerre
	 *
	 */
	public void checkWar(){
		//On reset les listes permettant la mort et création
		vivantes.clear();
		mortes.clear();
		appartenanceFaction.clear();
		//On va vérifier la guerre pour chaque cellule du plateau
		for (int i = 0; i < this.lePlateau.getTailleVerticale(); i++){
			for (int j = 0; j < this.lePlateau.getTailleHorizontale(); j++){
				//Si la cellule aest vivante, on amorce la vérification de guerre
				if (this.lePlateau.getCellule(i, j).getEtat()){
					//On stocke ses ennemis
					List<Coordonnee> ennemis = this.lePlateau.getEnnemis(i, j);
					//Si la cellule a des ennemis,on lance la condition de guerre
					if(ennemis.size()!=0){
						//Pour chaque ennemi de la cellule choisie, on compare son nombre d'alliés au nombre d'alliés de ses ennemis
						for (int k=0; k<ennemis.size(); k++){
							//Si il est supérieur, la cellule reste en vie, sinon elle meurt. L'égalité engendre la mort des deux camps.
							if((this.lePlateau.getAllies(i, j)).size() > this.lePlateau.getAllies(ennemis.get(k).getX() , ennemis.get(k).getY()).size()){
								vivantes.add(new Coordonnee(i,j));
								appartenanceFaction.add(this.lePlateau.getCellule(i,j).getFaction());
							}else{
								mortes.add(new Coordonnee(i,j));
							}
						}
					}else{
						vivantes.add(new Coordonnee(i,j));
						appartenanceFaction.add(this.lePlateau.getCellule(i,j).getFaction());
					}

				}
			}
		}
		this.lePlateau.mort(mortes);
		this.lePlateau.naissance(vivantes, appartenanceFaction);
	}

	/** Redéfinition de la méthode String toString()
	 *
     */
	public String toString(){
		return this.lePlateau.toString();
	}
}
