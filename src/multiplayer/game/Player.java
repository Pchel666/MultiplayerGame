package multiplayer.game;

public class Player {
	public int playerNumber;
	public int score;
	public Player(int pn) {
		playerNumber = pn;
		score = 0;
	}
	
	public void addPoints(int p) {
		score+=p;
	}
}