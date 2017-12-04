package multiplayer.game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class MultiplayerGame extends StateBasedGame {
	
	public static final String gameName = "Multiplayer Game";
	public static final int startMenu = 0;
	public static final int world = 3;
	public static final int info = 2;
	public static final int secondMenu = 1;
	public static final int worldJoin = 4;
	
	public MultiplayerGame(String gameName) {
		super(gameName);
		this.addState(new MainMenu(startMenu));
		this.addState(new World(world));
		this.addState(new InfoScreen(info));
		this.addState(new JoinHostMenu(secondMenu));
		this.addState(new WorldJoin(worldJoin));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(startMenu).init(gc, this);
		this.getState(world).init(gc, this);
		this.getState(info).init(gc, this);
		this.getState(secondMenu).init(gc, this);
		this.getState(worldJoin).init(gc, this);
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