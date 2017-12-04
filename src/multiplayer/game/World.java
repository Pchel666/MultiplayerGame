package multiplayer.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class World extends BasicGameState{

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
	private int thisPlayerNumber;
	private boolean firstRun;
	//Server variables
	public boolean stopServer;
	private static ServerSocket serverSocket;
	private static Socket socket;
	private static DataOutputStream dos;
	private static DataInputStream dis;
	private String msg;
	private String receivedMsg;
	
	public World(int world) {
		
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		stopServer = false;
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
		thisPlayerNumber = 1;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for (int i = 0; i < gridHeight; i++) {
			for (int j = 0; j < gridHeight; j++) {
				grid[i][j].img.draw(250+10*i, 100+10*j);
			}
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
		if (stopServer) {
			try {
				dos.close();
				dis.close();
				socket.close();
				serverSocket.close();
				System.out.println("Server connection stopped");
				sbg.enterState(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (firstRun) {
			firstRun = false;
			try {
				serverSocket = new ServerSocket(15000);
				socket=serverSocket.accept();
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());
				msg = "Message from server";
				receivedMsg = "No msg from client yet";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!firstRun && !stopServer) {
			try {
				dos.writeUTF(msg);
				receivedMsg = dis.readUTF();
				System.out.println(receivedMsg);
			} catch (IOException e) {
				e.printStackTrace();
				stopServer = true;
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_UP) && !pause) {
			if(thisPlayerNumber == 1 && grid[player1.posX][player1.posY-1].passable) {
				grid[player1.posX][player1.posY].passable = true;
				player1.posY--;
				grid[player1.posX][player1.posY].passable = false;
			}
			if(thisPlayerNumber == 2 && grid[player2.posX][player2.posY-1].passable) {
				grid[player2.posX][player2.posY].passable = true;
				player2.posY--;
				grid[player2.posX][player2.posY].passable = false;
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_DOWN) && !pause) {
			if(thisPlayerNumber == 1 && grid[player1.posX][player1.posY+1].passable) {
				grid[player1.posX][player1.posY].passable = true;
				player1.posY++;
				grid[player1.posX][player1.posY].passable = false;
			}
			if(thisPlayerNumber == 2 && grid[player2.posX][player2.posY+1].passable) {
				grid[player2.posX][player2.posY].passable = true;
				player2.posY++;
				grid[player2.posX][player2.posY].passable = false;
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_LEFT) && !pause) {
			if(thisPlayerNumber == 1 && grid[player1.posX-1][player1.posY].passable) {
				grid[player1.posX][player1.posY].passable = true;
				player1.posX--;
				grid[player1.posX][player1.posY].passable = false;
			}
			if(thisPlayerNumber == 2 && grid[player2.posX-1][player2.posY].passable) {
				grid[player2.posX][player2.posY].passable = true;
				player2.posX--;
				grid[player2.posX][player2.posY].passable = false;
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_RIGHT) && !pause) {
			if(thisPlayerNumber == 1 && grid[player1.posX+1][player1.posY].passable) {
				grid[player1.posX][player1.posY].passable = true;
				player1.posX++;
				grid[player1.posX][player1.posY].passable = false;
			}
			if(thisPlayerNumber == 2 && grid[player2.posX+1][player2.posY].passable) {
				grid[player2.posX][player2.posY].passable = true;
				player2.posX++;
				grid[player2.posX][player2.posY].passable = false;
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			if(!pause) {
				pause = true;
			}
			else if(pause) {
				pause = false;
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_1)) {
			thisPlayerNumber = 1;
		}
		if(gc.getInput().isKeyPressed(Input.KEY_2)) {
			thisPlayerNumber = 2;
		}
	}

	public int getID() {
		return 3;
	}
}