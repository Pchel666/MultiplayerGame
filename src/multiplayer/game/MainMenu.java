package multiplayer.game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class MainMenu extends BasicGameState {
	private Image play;
	private Image playHover;
	private Image playPressed;
	private Image info;
	private Image infoHover;
	private Image infoPressed;
	private Image quit;
	private Image quitHover;
	private Image quitPressed;
	private boolean playOver;
	private boolean infoOver;
	private boolean quitOver;
	private boolean playPress;
	private boolean infoPress;
	private boolean quitPress;
	private boolean p;
	private boolean h;

	public MainMenu(int startmenu) {
		
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		play = new Image("res/Play.png");
		playHover = new Image("res/PlayHover.png");
		playPressed = new Image("res/PlayPressed.png");
		info = new Image("res/Info.png");
		infoHover = new Image("res/InfoHover.png");
		infoPressed = new Image("res/InfoPressed.png");
		quit = new Image("res/Quit.png");
		quitHover = new Image("res/QuitHover.png");
		quitPressed = new Image("res/QuitPressed.png");
		playOver = false;
		infoOver = false;
		quitOver = false;
		playPress = false;
		infoPress = false;
		quitPress = false;
		p = false;
		h = false;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if(playOver) {
			playHover.draw(300, 75);
			info.draw(300, 275);
			quit.draw(300, 475);
		}
		else if(infoOver) {
			play.draw(300, 75);
			infoHover.draw(300, 275);
			quit.draw(300, 475);
		}
		else if(quitOver) {
			play.draw(300, 75);
			info.draw(300, 275);
			quitHover.draw(300, 475);
		}
		else if(playPress) {
			playPressed.draw(300, 75);
			info.draw(300, 275);
			quit.draw(300, 475);
		}
		else if(infoPress) {
			play.draw(300, 75);
			infoPressed.draw(300, 275);
			quit.draw(300, 475);
		}
		else if(quitPress) {
			play.draw(300, 75);
			info.draw(300, 275);
			quitPressed.draw(300, 475);
		}
		else {
			play.draw(300, 75);
			info.draw(300, 275);
			quit.draw(300, 475);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException {
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		if((posX > 300 && posX < 700) && (posY > 475 && posY < 625)) {
			//play button
			playOver = true;
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				playOver = false;
				playPress = true;
				p = true;
				h = true;
			}
			else if(h && !p) {
				sbg.enterState(1);
			}
			else if(h) {
				p = false;
			}
		}
		else if((posX > 300 && posX < 700) && (posY > 275 && posY < 425)) {
			//info button
			infoOver = true;
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				infoOver = false;
				infoPress = true;
				p = true;
				h = true;
			}
			else if(h && !p) {
				sbg.enterState(2);
			}
			else if(h) {
				p = false;
			}
		}
		else if((posX > 300 && posX < 700) && (posY > 75 && posY < 225)) {
			//quit button
			quitOver = true;
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				quitOver = false;
				quitPress = true;
				p = true;
				h = true;
			}
			else if(h && !p) {
				System.exit(0);
			}
			else if(h) {
				p = false;
			}
		}
		else {
			playOver = false;
			infoOver = false;
			quitOver = false;
			playPress = false;
			infoPress = false;
			quitPress = false;
			p = false;
			h = false;
		}
	}

	public int getID() {
		return 0;
	}
}