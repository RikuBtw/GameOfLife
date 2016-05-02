import GameOfLife.Jeu;
import java.util.Scanner;
import GameOfLife.Plateau;

import java.lang.Thread;

public class TestJeu {
	public static void main(String[] args){
		int nombre = 0;
		boolean nombreCorrect = false;
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
		//
		Plateau plateau;
		int x = 0;
		boolean tailleCorrecte = false;
		System.out.print("Taille du carré :");
		do{
			Scanner sc = new Scanner(System.in);
			try{
				x = sc.nextInt();
				tailleCorrecte = true;
			}catch (Exception e){
				System.out.print("Saise non valide, veuillez entrez un entier :\n");
				tailleCorrecte = false;
			}
		}while(!tailleCorrecte);

		plateau = new Plateau(x,x);

		Jeu j = new Jeu(plateau, nombre);
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
