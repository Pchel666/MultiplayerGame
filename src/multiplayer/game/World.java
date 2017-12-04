package multiplayer.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

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
	private int pause1;
	private int pause2;
	private Image p1img;
	private Image p2img;
	private Image gridWall;
	private Player player1;
	private Player player2;
	private int score1;
	private int score2;
	private boolean firstRun;
	private PickUp pickUp;
	private Random rand;
	private int randInt1;
	private int randInt2;
	private int maxScore;
	//Server variables
	public boolean stopServer;
	private static ServerSocket serverSocket;
	private static Socket socket;
	private static DataOutputStream dos;
	private static DataInputStream dis;
	private String msg;
	private String receivedMsg;
	private String[] receivedMsgSplit;
	
	public World(int world) {
		
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		maxScore = 5;
		rand = new Random();
		randInt1 = rand.nextInt(48)+1;
		randInt2 = rand.nextInt(48)+1;
		receivedMsgSplit = new String[3];
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
		pause1 = 0;
		pause2 = 0;
		p1img = new Image("res/Player1.png");
		p2img = new Image("res/Player2.png");
		player1 = new Player(1, p1img, 1, 1);
		grid[player1.posX][player1.posY].passable = false;
		player2 = new Player(2, p2img, gridWidth-2, gridHeight-2);
		grid[player2.posX][player2.posY].passable = false;
		score1 = player1.score;
		score2 = player2.score;
		pickUp = new PickUp(24,24,1);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		receivedMsgSplit = receivedMsg.split(",");
		grid[player2.posX][player2.posY].passable = true;
		player2.posX = Integer.parseInt(receivedMsgSplit[0]);
		player2.posY = Integer.parseInt(receivedMsgSplit[1]);
		grid[player2.posX][player2.posY].passable = false;
		pause2 = Integer.parseInt(receivedMsgSplit[2]);
		if(player1.posX == pickUp.posX && player1.posY == pickUp.posY) {
			player1.score++;
			randInt1 = rand.nextInt(48)+1;
			if (randInt1 == player1.posX || randInt1 == player2.posX) {
				while (randInt1 == player1.posX || randInt1 == player2.posX) {
					randInt1 = rand.nextInt(48)+1;
				}
			}
			randInt2 = rand.nextInt(48)+1;
			if (randInt2 == player1.posY || randInt2 == player2.posY) {
				while (randInt2 == player1.posY || randInt2 == player2.posY) {
					randInt2 = rand.nextInt(48)+1;
				}
			}
			pickUp.relocate(randInt1, randInt2);
		}
		if(player2.posX == pickUp.posX && player2.posY == pickUp.posY) {
			player2.score++;
			randInt1 = rand.nextInt(48)+1;
			if (randInt1 == player1.posX || randInt1 == player2.posX) {
				while (randInt1 == player1.posX || randInt1 == player2.posX) {
					randInt1 = rand.nextInt(48)+1;
				}
			}
			randInt2 = rand.nextInt(48)+1;
			if (randInt2 == player1.posY || randInt2 == player2.posY) {
				while (randInt2 == player1.posY || randInt2 == player2.posY) {
					randInt2 = rand.nextInt(48)+1;
				}
			}
			pickUp.relocate(randInt1, randInt2);
		}
		score1 = player1.score;
		score2 = player2.score;
		for (int i = 0; i < gridHeight; i++) {
			for (int j = 0; j < gridHeight; j++) {
				grid[i][j].img.draw(250+10*i, 100+10*j);
			}
		}
		pickUp.image.draw(250+10*pickUp.posX, 100+10*pickUp.posY);
		player1.image.draw(250+10*player1.posX, 100+10*player1.posY);
		player2.image.draw(250+10*player2.posX, 100+10*player2.posY);
		g.drawString("Player 1 score:", 10, 30);
		g.drawString(String.valueOf(score1), 154, 30);
		g.drawString("Player 2 score:", 10, 50);
		g.drawString(String.valueOf(score2), 154, 50);
		if(pause1 == 1) {
			g.drawString("Pause, press ESC to unpause", 370, 50);
		}
		if(pause2 == 1) {
			g.drawString("Player 2 paused, wait for them to unpause", 370, 50);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException {
		if (player1.score >= maxScore) {
			try {
				msg = player1.posX + "," + player1.posY + "," + player1.score + "," + pause1 + "," + pickUp.posX + "," + pickUp.posY + "," + player2.score;
				dos.writeUTF(msg);
				dos.close();
				dis.close();
				socket.close();
				serverSocket.close();
				sbg.enterState(5);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (player2.score >= maxScore) {
			try {
				msg = player1.posX + "," + player1.posY + "," + player1.score + "," + pause1 + "," + pickUp.posX + "," + pickUp.posY + "," + player2.score;
				dos.writeUTF(msg);
				dos.close();
				dis.close();
				socket.close();
				serverSocket.close();
				sbg.enterState(6);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (stopServer) {
			try {
				dos.close();
				dis.close();
				socket.close();
				serverSocket.close();
				System.exit(0);
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
				msg = player1.posX + "," + player1.posY + "," + player1.score + "," + pause1 + "," + pickUp.posX + "," + pickUp.posY + "," + player2.score;
				receivedMsg = player2.posX + "," + player2.posY + "," + pause2;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!firstRun) {
			try {
				msg = player1.posX + "," + player1.posY + "," + player1.score + "," + pause1 + "," + pickUp.posX + "," + pickUp.posY + "," + player2.score;
				dos.writeUTF(msg);
				receivedMsg = dis.readUTF();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		if(gc.getInput().isKeyDown(Input.KEY_UP) && pause1 == 0 && pause2 == 0) {
			if(grid[player1.posX][player1.posY-1].passable) {
				grid[player1.posX][player1.posY].passable = true;
				player1.posY--;
				grid[player1.posX][player1.posY].passable = false;
			}
		}
		if(gc.getInput().isKeyDown(Input.KEY_DOWN) && pause1 == 0 && pause2 == 0) {
			if(grid[player1.posX][player1.posY+1].passable) {
				grid[player1.posX][player1.posY].passable = true;
				player1.posY++;
				grid[player1.posX][player1.posY].passable = false;
			}
		}
		if(gc.getInput().isKeyDown(Input.KEY_LEFT) && pause1 == 0 && pause2 == 0) {
			if(grid[player1.posX-1][player1.posY].passable) {
				grid[player1.posX][player1.posY].passable = true;
				player1.posX--;
				grid[player1.posX][player1.posY].passable = false;
			}
		}
		if(gc.getInput().isKeyDown(Input.KEY_RIGHT) && pause1 == 0 && pause2 == 0) {
			if(grid[player1.posX+1][player1.posY].passable) {
				grid[player1.posX][player1.posY].passable = true;
				player1.posX++;
				grid[player1.posX][player1.posY].passable = false;
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE) && pause2 == 0) {
			if(pause1 == 0) {
				pause1 = 1;
			}
			else if(pause1 == 1) {
				pause1 = 0;
			}
		}
	}
	
	public int getID() {
		return 3;
	}
}