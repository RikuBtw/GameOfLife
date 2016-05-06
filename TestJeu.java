import GameOfLife.Jeu;
import GameOfLife.Plateau;

import java.lang.Thread;

public class TestJeu {
	public static void main(String[] args){
		//Création du jeu
		Jeu j = new Jeu();
		//Lancement des ticks
		j.tick();
	}
}
