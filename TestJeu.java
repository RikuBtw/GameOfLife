import GameOfLife.Jeu;

import java.lang.Thread;

public class TestJeu {
	public static void main(String[] args){
		//Nombre de joueur à insérer dans new Jeu(x)
		Jeu j = new Jeu(1);
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
