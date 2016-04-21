import GameOfLife.Jeu;

import java.lang.Thread;

//NE SERT A RIEN LOL
public class TestJeu {
	public static void main(String[] args){
		Jeu j = new Jeu(2);
		for(int i=0; i<500; i++){
			System.out.println("etape :"+i);
			j.jouer();
			System.out.println(j.toString());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
