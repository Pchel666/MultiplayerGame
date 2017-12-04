package multiplayer.game;

import org.newdawn.slick.Image;

public class Player {
	
	public Image image;
	public int playerNumber;
	public int score;
	public int posX;
	public int posY;
	
	public Player(int pn, Image i, int x, int y) {
		playerNumber = pn;
		score = 0;
		image = i;
		posX = x;
		posY = y;
	}
	
	public void addPoints(int p) {
		score+=p;
	}
}