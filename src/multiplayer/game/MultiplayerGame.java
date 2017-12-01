package multiplayer.game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class MultiplayerGame extends StateBasedGame {
	
	public static final String gameName = "Multiplayer Game";
	public static final int startMenu = 0;
	public static final int world = 1;
	public static final int info = 2;
	
	public MultiplayerGame(String gameName) {
		super(gameName);
		this.addState(new MainMenu(startMenu));
		this.addState(new World(world));
		this.addState(new InfoScreen(info));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(startMenu).init(gc, this);
		this.getState(world).init(gc, this);
		this.getState(info).init(gc, this);
		this.enterState(startMenu);
	}
	
	public static void main(String[] args) {
		AppGameContainer agc;
		try {
			agc = new AppGameContainer(new MultiplayerGame(gameName));
			agc.setDisplayMode(1000, 700, false);
			agc.setVSync(true);
			agc.start();
		}catch(SlickException e) {
			e.printStackTrace();
		}
	}
}