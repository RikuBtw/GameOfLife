import GameOfLife.Jeu;

import java.lang.Thread;

public class TestJeu {
	public static void main(String[] args){
		Jeu j = new Jeu(1);
		for(int i=0; i<500; i++){
			System.out.println("etape :"+i);
			System.out.println(j.toString());
			j.jouer();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
