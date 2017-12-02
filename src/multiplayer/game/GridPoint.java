package multiplayer.game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GridPoint {
	
	public Image img;
	public boolean passable;
	public int x;
	public int y;
	
	public GridPoint(int xx, int yy) throws SlickException {
		img = new Image("res/GridPoint.png");
		passable = true;
		x = xx;
		y = yy;
	}
}