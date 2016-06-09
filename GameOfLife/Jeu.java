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
	//Gestion de l'état stable
	private int generation;
	private boolean stable;
	List<Coordonnee> vivantesStable;
	int[][] cooldownAllianceBloquee;


	/** Constructeur de la classe Jeu
	 *
	 */
	public Jeu(){
		this.nbFactions = this.initialiserNbJoueurs();
		this.factions = this.initialiserJoueurs(this.nbFactions);
		this.lePlateau = this.initialiserPlateau();
		this.initialiser(this.initialiserNbCellules());
		this.alliances = new ArrayList<Alliance>();
		this.generation = 0;
		this.stable = false;
		this.vivantesStable = new ArrayList<Coordonnee>();
		cooldownAllianceBloquee = new int[nbFactions][nbFactions];
		for(int i = 0; i < nbFactions; i++){
			for(int j = 0; j < nbFactions; j++){
				cooldownAllianceBloquee[i][j] = 0;
			}
		}
	}

	/** Méthode permettant d'initialiser le jeu
	 *
	 */
	public void initialiser(int nbCellules){
		for(int i=0; i<nbCellules; i++){
			int x = Math.abs((int)(Math.random()*this.lePlateau.getTailleVerticale()));
			int y = Math.abs((int)(Math.random()*this.lePlateau.getTailleHorizontale()));
			int f = Math.abs((int)(Math.random()*this.nbFactions));
			vivantes.add(new Coordonnee(x, y));
			appartenanceFaction.add(this.factions.get(f));
		}
		this.lePlateau.naissance(vivantes, appartenanceFaction);
		this.calculScore();
	}

	/** Méthode permettant d'initialiser le plateau de jeu
	 *
	 * @return Le plateau de jeu
	 */
	public Plateau initialiserPlateau(){
		Scanner sc = new Scanner(System.in);
		// Axe Vertical
		int y = 0;
		boolean tailleYCorrecte = false;
		System.out.print("\nHauteau du plateau :");
		do{
			sc = new Scanner(System.in);
			try{
				y = sc.nextInt();
				tailleYCorrecte = true;
			}catch (Exception e){
				System.out.print("Saise de la hauteur non valide, veuillez entrez un entier :\n");
				tailleYCorrecte = false;
			}
		}while(!tailleYCorrecte);
		// Axe Horizontal
		Plateau plateau;
		int x = 0;
		boolean tailleXCorrecte = false;
		System.out.print("Largeur du plateau :");
		do{
			sc = new Scanner(System.in);
			try{
				x = sc.nextInt();
				tailleXCorrecte = true;
			}catch (Exception e){
				System.out.print("Saise de la largeur non valide, veuillez entrez un entier :\n");
				tailleXCorrecte = false;
			}
		}while(!tailleXCorrecte);
		plateau = new Plateau(x,y);
		return plateau;
	}

	/** Méthode permettant de générer un certain nombre de cellules
	 *
	 * @return Le nombre de cellules
	 */
	public int initialiserNbCellules(){
		Scanner sc = new Scanner(System.in);
		int nombreCellules = 0;
		boolean nbCorrect = false;
		System.out.print("Nombre de cellules disposées :");
		do{
			sc = new Scanner(System.in);
			try{
				nombreCellules = sc.nextInt();
				if (nombreCellules >= 0 ){
					nbCorrect = true;
				}else{
					throw new Exception();
				}
			}catch (Exception e){
				System.out.print("Saise non valide, veuillez entrez un entier positif :\n");
				nbCorrect = false;
			}
		}while(!nbCorrect);
		return nombreCellules;
	}

	/** Méthode initialisant le nombre de joueurs
	 *
	 * @return Le nombre de joueurs
	 */
	public int initialiserNbJoueurs(){
		int nombre = 0;
		boolean nombreCorrect = false;
		Scanner sc = new Scanner(System.in);
		//Choix du nombre de joueurs
		System.out.print("Nombre de joueurs: ");
		do{
			sc = new Scanner(System.in);
			try{
				nombre = sc.nextInt();
				if (nombre >= 0 ){
					nombreCorrect = true;
				}else{
					throw new Exception();
				}
			}catch (Exception e){
				System.out.print("Saise non valide, veuillez entrez un entier positif :\n");
				nombreCorrect = false;
			}
		}while(!nombreCorrect);
		return nombre;
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
			System.out.print("\nNom joueur "+(i+1)+":");
			String nom = sc.nextLine();
			System.out.print("Sa couleur:");
			String couleur = sc.nextLine();
			joueurs.add(new Faction(nom, couleur, i));
		}
		return joueurs;
	}

	/** Méthode permettant de jouer
	 *
	 */
	public void jouer(){
		//Permet de lancer les vérifications d'alliance
		//this.checkAlliance();
		//Permet de lancer les vérifications de guerre
		this.checkWar();
		//Règles de vie classiques
		this.checkLife();
		//Règles de stabilité
		this.stable = this.checkStable(vivantes);
		//Calcul du score
		this.calculScore();
		//Compte nombre de joueurs restant
		this.checkJoueursRestants();
		//Check la fin du jeu
		this.checkFin();

	}

	/** Gère le système de générations/ticks
	 *
	 */
	public void tick(){
		//Système de cycle
		while(!this.checkFin()){
			System.out.println("Génération :"+ this.generation);
			System.out.println(this.toString());
			this.jouer();
			for(int i = 0; i < nbFactions; i++){
				for(int j = 0; j < nbFactions; j++){
					cooldownAllianceBloquee[i][j]--;
				}
			}
			try {
				//500 ms de pause entre chaque génération
				Thread.sleep(150);
				this.generation++;
			} catch (InterruptedException e) {
				System.out.print("Arrêt forcé du jeu.");
				e.printStackTrace();
			}

		}
		if(this.nbFactions > 1 && this.vainqueur()==null){
			System.out.println("Pas de vainqueur");
		}else if(this.nbFactions > 1 && this.vainqueur()!=null){
			System.out.println(""+this.vainqueur().getNom() + " remporte la partie");
		}System.out.println("Partie terminée");
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
			for (int j = 0; j < this.lePlateau.getTailleHorizontale(); j++) {
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

	/** Methode permettant les règles d'alliance TODO
	 *
	 */
	public void checkAlliance(){
		boolean AllianceBloquee = false;
		for (int i = 0; i < this.lePlateau.getTailleVerticale(); i++) {
			for (int j = 0; j < this.lePlateau.getTailleHorizontale(); j++) {
				if (this.lePlateau.getCellule(i, j).getEtat()){
					List<Coordonnee> ennemis = this.lePlateau.getEnnemis(i, j);
					if(ennemis.size()!=0){
						for (int k=0; k<ennemis.size(); k++){
							if(this.lePlateau.getCellule(i,j).getFaction().getAlliance() == null && this.lePlateau.getCellule(ennemis.get(k).getX(),ennemis.get(k).getY()).getFaction().getAlliance() == null) {
								if(this.cooldownAllianceBloquee[this.lePlateau.getCellule(i,j).getFaction().getNumFaction()][this.lePlateau.getCellule(ennemis.get(k).getX(),ennemis.get(k).getY()).getFaction().getNumFaction()] == 0){
									Alliance temp = new Alliance(2);
									temp.addFaction(this.lePlateau.getCellule(i,j).getFaction());
									temp.addFaction(this.lePlateau.getCellule(ennemis.get(k).getX(),ennemis.get(k).getY()).getFaction());
									this.alliances.add(temp);
									this.lePlateau.getCellule(i,j).getFaction().setAlliance(temp);
									this.lePlateau.getCellule(ennemis.get(k).getX(),ennemis.get(k).getY()).getFaction().setAlliance(temp);
								}
							}else if(this.lePlateau.getCellule(i,j).getFaction().getAlliance() != null && this.lePlateau.getCellule(ennemis.get(k).getX(),ennemis.get(k).getY()).getFaction().getAlliance() == null) {
								for(Faction factionAlliance : this.lePlateau.getCellule(i,j).getFaction().getAlliance().getFactions()) {
									if(this.cooldownAllianceBloquee[factionAlliance.getNumFaction()][this.lePlateau.getCellule(ennemis.get(k).getX(), ennemis.get(k).getY()).getFaction().getNumFaction()] != 0) {
										AllianceBloquee = true;
									}
								}
								if(!AllianceBloquee){
									this.lePlateau.getCellule(i, j).getFaction().getAlliance().addFaction(this.lePlateau.getCellule(ennemis.get(k).getX(), ennemis.get(k).getY()).getFaction());
								}
							}else if (this.lePlateau.getCellule(i,j).getFaction().getAlliance() == null && this.lePlateau.getCellule(ennemis.get(k).getX(),ennemis.get(k).getY()).getFaction().getAlliance() != null){
								for(Faction factionAlliance : this.lePlateau.getCellule(ennemis.get(k).getX(), ennemis.get(k).getY()).getFaction().getAlliance().getFactions()) {
									if(this.cooldownAllianceBloquee[this.lePlateau.getCellule(ennemis.get(k).getX(), ennemis.get(k).getY()).getFaction().getNumFaction()][factionAlliance.getNumFaction()] != 0) {
										AllianceBloquee = true;
									}
								}
								if(!AllianceBloquee) {
									this.lePlateau.getCellule(ennemis.get(k).getX(), ennemis.get(k).getY()).getFaction().getAlliance().addFaction(this.lePlateau.getCellule(i, j).getFaction());
								}
							}
						}
					}
				}
			}
		}
	}

	/** Methode vérifiant si une alliance est terminée
	 *
	 */
	public void checkRuptureAlliance(){
		for (int i = 0; i < alliances.size(); i++){
			if(alliances.get(i).getDureeVie() == 0){
				alliances.get(i).deleteAll();
			}
		}
	}


	/** Méthode permettant de calculer le score des factions
	 *
	 */
	public void calculScore(){
		for(int i = 0; i < this.nbFactions; i++){
			int compteur = 0;
			for(int j = 0; j < vivantes.size(); j++){
				if (this.lePlateau.getCellule(vivantes.get(j).getX(), vivantes.get(j).getY()).getFaction().equals(this.factions.get(i))){
					compteur ++;
				}
			}
			this.factions.get(i).setScore(compteur);
		}
	}

	/** Méthode déterminant le nombre de joueurs restants
	 *
	 * @return Le nombre de joueurs restants
	 */
	public int checkJoueursRestants(){
		int restants = this.nbFactions;
		for(int i = 0; i < this.nbFactions; i++){
			if(this.factions.get(i).getScore() == 0){
				restants--;
			}
		}
		return restants;
	}

	/** Méthode vérifiant si il existe un état stable
	 *
	 * @param listeVivantes - liste des cellules vivantes que l'on va comparer à celle d'il y a 2 générations
	 * @return true si le plateau est stable, false sinon
	 */
	public boolean checkStable(List<Coordonnee> listeVivantes){
		if(this.alliances.size() != 0) {
			//Si le plateau n'a pas commencé, on initialise la liste temporaire globale.
			//Celle-ci est vérifiée toutes les générations paires afin de déterminer si l'état est stable.
			if (this.generation == 0) {
				//Ces lignes permettent de passer outre le problème des pointeurs
				this.vivantesStable = new ArrayList<Coordonnee>();
				for (int n = 0; n < listeVivantes.size(); n++) {
					this.vivantesStable.add(n, listeVivantes.get(n));
				}
				return false;
				//Si la génération est paire, on compare les deux listes
			} else if (this.generation % 2 == 0) {
				for (int i = 0; i < this.vivantesStable.size(); i++) {
					if (vivantesStable.get(i).getX() != listeVivantes.get(i).getX() || vivantesStable.get(i).getY() != listeVivantes.get(i).getY()) {
						//Ces lignes permettent de passer outre le problème des pointeurs
						this.vivantesStable = new ArrayList<Coordonnee>();
						for (int n = 0; n < listeVivantes.size(); n++) {
							this.vivantesStable.add(n, listeVivantes.get(n));
						}
						return false;
					}
				}
				//Ces lignes permettent de passer outre le problème des pointeurs
				this.vivantesStable = new ArrayList<Coordonnee>();
				for (int n = 0; n < listeVivantes.size(); n++) {
					this.vivantesStable.add(n, listeVivantes.get(n));
				}
				return true;
			}
			return false;
		}
		return false;
	}

	public Faction vainqueur(){
		int score = 0;
		Faction faction = null;
		if (this.nbFactions > 1){
			for(int i=0; i < this.nbFactions; i++){
				if(this.factions.get(i).getScore() > score){
					score = this.factions.get(i).getScore();
					faction = this.factions.get(i);
				}else if(this.factions.get(i).getScore() == score){
					faction = null;
				}
			}
		}
		return faction;
	}

	/** Méthode de fin de jeu
	 *
	 * @return true si le jeu est fini, false sinon
	 */
	public boolean checkFin(){
		//Si jeu solo, on vérifie si l'état est stable ou qu'il n'y ai plus de cellule
		if (this.nbFactions == 1){
			if (this.checkJoueursRestants() == 0 || this.stable == true) {
				return true;
			}
			//Si jeu multi, on vérifie si l'état est stable ou qu'il n'y ai qu'une faction
		}else{
			if (this.checkJoueursRestants() <= 1){
				return true;
			}else if (this.stable == true) {
				if(this.alliances.size() == 0) {
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}

	/** Redéfinition de la méthode String toString()
	 *
	 */
	public String toString(){
		for(int i = 0; i < this.nbFactions; i++){
			System.out.println("Score de " + this.factions.get(i).getNom() + ": " + this.factions.get(i).getScore());
		}
		return this.lePlateau.toString();
	}
}
