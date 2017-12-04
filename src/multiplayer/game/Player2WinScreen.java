package multiplayer.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Player2WinScreen extends BasicGameState {

	public Player2WinScreen(int player2winscreen) {
		
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("Player 2 won", 450, 340);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException {
		
	}

	public int getID() {
		return 6;
	}
}