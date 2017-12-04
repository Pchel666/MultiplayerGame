package multiplayer.game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PickUp {

	public Image image;
	public int points;
	public int posX;
	public int posY;
	
	public PickUp(int x, int y, int p) throws SlickException {
		image = new Image("res/PickMeUp.png");
		posX = x;
		posY = y;
		points = p;
	}
	
	public void relocate(int x, int y) {
		posX = x;
		posY = y;
	}
}