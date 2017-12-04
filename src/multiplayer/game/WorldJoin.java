package multiplayer.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

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
	private boolean needRelocation;
	private int maxScore;
	//Client variables
	public boolean stopClient;
	private static Socket socket;
	private static DataOutputStream dos;
	private static DataInputStream dis;
	private String msg;
	private String receivedMsg;
	private String[] receivedMsgSplit;
	private String hostName;
	private Scanner scanner;

	public WorldJoin(int worldjoin) {
		
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		maxScore = 5;
		hostName = "localhost";
		scanner = new Scanner(System.in);
		receivedMsgSplit = new String[6];
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
		pause1 = 0;
		pause2 = 0;
		p1img = new Image("res/Player1.png");
		p2img = new Image("res/Player2.png");
		player1 = new Player(1, p1img, 1, 1);
		grid[player1.posX][player1.posY].passable = false;
		player2 = new Player(2, p2img, gridWidth-2, gridHeight-2);
		grid[player2.posX][player2.posY].passable = false;
		score1 = 0;
		score2 = 0;
		pickUp = new PickUp(24,24,1);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		receivedMsgSplit = receivedMsg.split(",");
		grid[player1.posX][player1.posY].passable = true;
		player1.posX = Integer.parseInt(receivedMsgSplit[0]);
		player1.posY = Integer.parseInt(receivedMsgSplit[1]);
		player1.score = Integer.parseInt(receivedMsgSplit[2]);
		grid[player1.posX][player1.posY].passable = false;
		pause1 = Integer.parseInt(receivedMsgSplit[3]);
		pickUp.posX = Integer.parseInt(receivedMsgSplit[4]);
		pickUp.posY = Integer.parseInt(receivedMsgSplit[5]);
		if(player2.posX == pickUp.posX && player2.posY == pickUp.posY) {
			player2.score++;
			needRelocation = true;
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
		if(pause2 == 1) {
			g.drawString("Pause, press ESC to unpause", 370, 50);
		}
		if(pause1 == 1) {
			g.drawString("Player 1 paused, wait for them to unpause", 370, 50);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException {
		if (player1.score >= maxScore) {
			try {
				dos.close();
				dis.close();
				socket.close();
				sbg.enterState(5);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (player2.score >= maxScore) {
			try {
				msg = player2.posX + "," + player2.posY + "," + player2.score + "," + pause2 + "," + ((needRelocation) ? 1 : 0);
				dos.writeUTF(msg);
				needRelocation = false;
				dos.close();
				dis.close();
				socket.close();
				sbg.enterState(6);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (stopClient) {
			try {
				dos.close();
				dis.close();
				socket.close();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (firstRun) {
			firstRun = false;
			try {
				System.out.println("Enter host name: ");
				hostName = scanner.nextLine();
				socket = new Socket(hostName, 15000);
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());
				msg = player2.posX + "," + player2.posY + "," + player2.score + "," + pause2 + "," + ((needRelocation) ? 1 : 0);
				receivedMsg = player1.posX + "," + player1.posY + "," + player1.score + "," + pause1;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!firstRun) {
			try {
				msg = player2.posX + "," + player2.posY + "," + player2.score + "," + pause2 + "," + ((needRelocation) ? 1 : 0);
				dos.writeUTF(msg);
				needRelocation = false;
				receivedMsg = dis.readUTF();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		if(gc.getInput().isKeyDown(Input.KEY_UP) && pause1 == 0 && pause2 == 0) {
			if(grid[player2.posX][player2.posY-1].passable) {
				grid[player2.posX][player2.posY].passable = true;
				player2.posY--;
				grid[player2.posX][player2.posY].passable = false;
			}
		}
		if(gc.getInput().isKeyDown(Input.KEY_DOWN) && pause1 == 0 && pause2 == 0) {
			if(grid[player2.posX][player2.posY+1].passable) {
				grid[player2.posX][player2.posY].passable = true;
				player2.posY++;
				grid[player2.posX][player2.posY].passable = false;
			}
		}
		if(gc.getInput().isKeyDown(Input.KEY_LEFT) && pause1 == 0 && pause2 == 0) {
			if(grid[player2.posX-1][player2.posY].passable) {
				grid[player2.posX][player2.posY].passable = true;
				player2.posX--;
				grid[player2.posX][player2.posY].passable = false;
			}
		}
		if(gc.getInput().isKeyDown(Input.KEY_RIGHT) && pause1 == 0 && pause2 == 0) {
			if(grid[player2.posX+1][player2.posY].passable) {
				grid[player2.posX][player2.posY].passable = true;
				player2.posX++;
				grid[player2.posX][player2.posY].passable = false;
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE) && pause1 == 0) {
			if(pause2 == 0) {
				pause2 = 1;
			}
			else if(pause2 == 1) {
				pause2 = 0;
			}
		}
	}

	public int getID() {
		return 4;
	}
}