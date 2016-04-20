package GameOfLife;

import java.lang.Thread;

//NE SERT A RIEN LOL
public class TestJeu {
	public static void main(String[] args){
		Jeu j = new Jeu();
		for(int i=0; i<500; i++){
			j.jouer();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
