package multiplayer.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class WorldJoin extends BasicGameState {
	
	//Game variables
	private GridPoint[][] grid;
	private int gridWidth;
	private int gridHeight;
	private boolean pause;
	private Image p1img;
	private Image p2img;
	private Image gridWall;
	private Player player1;
	private Player player2;
	private int score1;
	private int score2;
	private boolean firstRun;
	//Client variables
	public boolean stopClient;
	private static Socket socket;
	private static DataOutputStream dos;
	private static DataInputStream dis;
	private String msg;
	private String receivedMsg;

	public WorldJoin(int worldjoin) {
		
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		stopClient = false;
		firstRun = true;
		gridWidth = 50;
		gridHeight = 50;
		gridWall = new Image("res/GridPointWall.png");
		grid = new GridPoint[gridWidth][gridHeight];
		for (int i = 0; i < gridHeight; i++) {
			for (int j = 0; j < gridHeight; j++) {
				grid[i][j] = new GridPoint(i, j);
			}
		}
		for (int i = 0; i < gridHeight; i++) {
			grid[0][i].passable = false;
			grid[0][i].img = gridWall;
			grid[gridWidth-1][i].passable = false;
			grid[gridWidth-1][i].img = gridWall;
		}
		for (int i = 0; i < gridWidth; i++) {
			grid[i][gridHeight-1].passable = false;
			grid[i][gridHeight-1].img = gridWall;
			grid[i][0].passable = false;
			grid[i][0].img = gridWall;
		}
		pause = false;
		p1img = new Image("res/Player1.png");
		p2img = new Image("res/Player2.png");
		player1 = new Player(1, p1img, 1, 1);
		grid[player1.posX][player1.posY].passable = false;
		player2 = new Player(2, p2img, gridWidth-2, gridHeight-2);
		grid[player2.posX][player2.posY].passable = false;
		score1 = 0;
		score2 = 0;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for (int i = 0; i < gridHeight; i++) {
			for (int j = 0; j < gridHeight; j++) {
				grid[i][j].img.draw(250+10*i, 100+10*j);
			}
		}
		switch(receivedMsg) {
		case "UP":
			grid[player1.posX][player1.posY].passable = true;
			player1.posY--;
			grid[player1.posX][player1.posY].passable = false;
			receivedMsg = "";
			break;
		case "DOWN":
			grid[player1.posX][player1.posY].passable = true;
			player1.posY++;
			grid[player1.posX][player1.posY].passable = false;
			receivedMsg = "";
			break;
		case "LEFT":
			grid[player1.posX][player1.posY].passable = true;
			player1.posX--;
			grid[player1.posX][player1.posY].passable = false;
			receivedMsg = "";
			break;
		case "RIGHT":
			grid[player1.posX][player1.posY].passable = true;
			player1.posX++;
			grid[player1.posX][player1.posY].passable = false;
			receivedMsg = "";
			break;
		case "PAUSE":
			pause = true;
			receivedMsg = "";
			break;
		case "UNPAUSE":
			pause = false;
			receivedMsg = "";
			break;
		default:
			break;
		}
		player1.image.draw(250+10*player1.posX, 100+10*player1.posY);
		player2.image.draw(250+10*player2.posX, 100+10*player2.posY);
		g.drawString("Player 1 score:", 10, 30);
		g.drawString(String.valueOf(score1), 154, 30);
		g.drawString("Player 2 score:", 10, 50);
		g.drawString(String.valueOf(score2), 154, 50);
		if(pause) {
			g.drawString("Pause, press ESC to unpause", 370, 50);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException {
		if (stopClient) {
			try {
				dos.close();
				dis.close();
				socket.close();
				sbg.enterState(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (firstRun) {
			firstRun = false;
			try {
				socket = new Socket("localhost", 15000);
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());
				msg = "";
				receivedMsg = "";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!firstRun && !stopClient) {
			try {
				dos.writeUTF(msg);
				msg = "";
				receivedMsg = dis.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
				stopClient = true;
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_UP) && !pause) {
			if(grid[player2.posX][player2.posY-1].passable) {
				grid[player2.posX][player2.posY].passable = true;
				player2.posY--;
				grid[player2.posX][player2.posY].passable = false;
				msg = "UP";
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_DOWN) && !pause) {
			if(grid[player2.posX][player2.posY+1].passable) {
				grid[player2.posX][player2.posY].passable = true;
				player2.posY++;
				grid[player2.posX][player2.posY].passable = false;
				msg = "DOWN";
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_LEFT) && !pause) {
			if(grid[player2.posX-1][player2.posY].passable) {
				grid[player2.posX][player2.posY].passable = true;
				player2.posX--;
				grid[player2.posX][player2.posY].passable = false;
				msg = "LEFT";
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_RIGHT) && !pause) {
			if(grid[player2.posX+1][player2.posY].passable) {
				grid[player2.posX][player2.posY].passable = true;
				player2.posX++;
				grid[player2.posX][player2.posY].passable = false;
				msg = "RIGHT";
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			if(!pause) {
				pause = true;
				msg = "PAUSE";
			}
			else if(pause) {
				pause = false;
				msg = "UNPAUSE";
			}
		}
	}

	public int getID() {
		return 4;
	}
}