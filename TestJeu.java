import GameOfLife.Jeu;
import java.util.Scanner;
import GameOfLife.Plateau;

import java.lang.Thread;

public class TestJeu {
	public static void main(String[] args){
		int nombre = 0;
		boolean nombreCorrect = false;

		//Choix du nombre de joueurs
		System.out.print("Nombre de joueurs: ");
		do{
			Scanner sc = new Scanner(System.in);
			try{
				nombre = sc.nextInt();
				nombreCorrect = true;
			}catch (Exception e){
				System.out.print("Saise non valide, veuillez entrez un entier :\n");
				nombreCorrect = false;
			}
		}while(!nombreCorrect);

		// Axe Vertical
		int y = 0;
		boolean tailleYCorrecte = false;
		System.out.print("Hauteau du plateau :");
		do{
			Scanner sc = new Scanner(System.in);
			try{
				y = sc.nextInt();
				tailleYCorrecte = true;
			}catch (Exception e){
				System.out.print("Saise non valide, veuillez entrez un entier :\n");
				tailleYCorrecte = false;
			}
		}while(!tailleYCorrecte);

		// Axe Horizontal
		Plateau plateau;
		int x = 0;
		boolean tailleXCorrecte = false;
		System.out.print("Largeur du plateau :");
		do{
			Scanner sc = new Scanner(System.in);
			try{
				x = sc.nextInt();
				tailleXCorrecte = true;
			}catch (Exception e){
				System.out.print("Saise non valide, veuillez entrez un entier :\n");
				tailleXCorrecte = false;
			}
		}while(!tailleXCorrecte);

		// Nombre de cellules générés
		int nombreCellules = 0;
		boolean nbCorrect = false;
		System.out.print("Nombre de cellules disposées :");
		do{
			Scanner sc = new Scanner(System.in);
			try{
				nombreCellules = sc.nextInt();
				nbCorrect = true;
			}catch (Exception e){
				System.out.print("Saise non valide, veuillez entrez un entier :\n");
				nbCorrect = false;
			}
		}while(!nbCorrect);

		//Création plateau
		plateau = new Plateau(x,y);

		//Création du jeu
		Jeu j = new Jeu(plateau, nombre, nombreCellules);

		//Système de cycle
		for(int i=0; i<500; i++){
			System.out.println("Génération :"+i);
			System.out.println(j.toString());
			j.jouer();
			try {
				//500 ms de pause entre chaque génération
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.print("Arrêt forcé du jeu.");
				e.printStackTrace();
			}
		}
	}
}
