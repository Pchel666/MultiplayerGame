package multiplayer.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class World extends BasicGameState{

	private GridPoint[][] grid;
	private int score1;
	private int score2;
	private int gridWidth;
	private int gridHeight;
	
	public World(int world) {
		
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gridWidth = 50;
		gridHeight = 50;
		grid = new GridPoint[gridWidth][gridHeight];
		for (int i = 0; i < gridHeight; i++) {
			for (int j = 0; j < gridHeight; j++) {
				grid[i][j] = new GridPoint(i, j);
			}
		}
		for (int i = 0; i < gridHeight; i++) {
			grid[i][0].passable = false;
			grid[0][i].passable = false;
			grid[i][49].passable = false;
			grid[49][i].passable = false;
		}
		score1 = 0;
		score2 = 0;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for (int i = 0; i < gridHeight; i++) {
			for (int j = 0; j < gridHeight; j++) {
				grid[i][j].img.draw(250+10*i, 100+10*j);
			}
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException {
		
	}

	public int getID() {
		return 3;
	}

}