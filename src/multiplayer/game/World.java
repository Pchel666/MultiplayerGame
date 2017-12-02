package multiplayer.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class World extends BasicGameState{

	private GridPoint[][] grid;
	private int gridWidth;
	private int gridHeight;
	private boolean pause;
	private Image p1img;
	private Image p2img;
	private Player player1;
	private Player player2;
	private int score1;
	private int score2;
	
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
		pause = false;
		p1img = new Image("res/Player1.png");
		p2img = new Image("res/Player2.png");
		player1 = new Player(1, p1img, 1, 1);
		player2 = new Player(2, p2img, 48, 48);
		grid[1][1].occupant = player1;
		grid[48][48].occupant = player2;
		score1 = 0;
		score2 = 0;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for (int i = 0; i < gridHeight; i++) {
			for (int j = 0; j < gridHeight; j++) {
				grid[i][j].img.draw(250+10*i, 100+10*j);
				if(grid[i][j].occupant!=null) {
					grid[i][j].occupant.image.draw(250+10*i, 100+10*j);
				}
			}
		}
		g.drawString("Player 1 score:", 10, 30);
		g.drawString(String.valueOf(score1), 154, 30);
		g.drawString("Player 2 score:", 10, 50);
		g.drawString(String.valueOf(score2), 154, 50);
		if(pause) {
			g.drawString("Pause, press ESC to unpause", 370, 50);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException {
		if(gc.getInput().isKeyPressed(Input.KEY_UP) && !pause) {
			System.out.println("Up");
		}
		if(gc.getInput().isKeyPressed(Input.KEY_DOWN) && !pause) {
			System.out.println("Down");
		}
		if(gc.getInput().isKeyPressed(Input.KEY_LEFT) && !pause) {
			System.out.println("Left");
		}
		if(gc.getInput().isKeyPressed(Input.KEY_RIGHT) && !pause) {
			System.out.println("Right");
		}
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			if(!pause) {
				System.out.println("Pause");
				pause = true;
			}
			else if(pause) {
				System.out.println("Unpause");
				pause = false;
			}
		}
	}

	public int getID() {
		return 3;
	}

}