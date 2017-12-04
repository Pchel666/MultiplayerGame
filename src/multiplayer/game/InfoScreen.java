package multiplayer.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class InfoScreen extends BasicGameState {
	
	private int frameCount;

	public InfoScreen(int info) {
		
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		frameCount = 0;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("Host is player 1 (blue circle with a number 1 in it)", 240, 200);
		g.drawString("Joining player is player 2 (blue circle with a number 2 in it)", 240, 215);
		g.drawString("Collect red orbs to win, first to 5 wins", 240, 230);
		g.drawString("Written by Alexander Popov", 240, 300);
		g.drawString("Final project", 240, 315);
		g.drawString("Class: Network Programming, COMP2100-01/02", 240, 330);
		g.drawString("Engine: Slick2D", 240, 345);
		g.drawString("Press ESC to go back", 240, 360);
		if(frameCount >= 60 && frameCount < 120) {
			g.drawString(".", 420, 360);
		}
		else if(frameCount >= 120 && frameCount < 180) {
			g.drawString("..", 420, 360);
		}
		else if(frameCount >= 180 && frameCount < 240) {
			g.drawString("...", 420, 360);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException {
		frameCount++;
		if(frameCount>240) {
			frameCount = 0;
		}
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			sbg.enterState(0);
		}
	}

	public int getID() {
		return 2;
	}

}
