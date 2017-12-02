package multiplayer.game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class JoinHostMenu extends BasicGameState {

	private Image host;
	private Image hostHover;
	private Image hostPressed;
	private Image join;
	private Image joinHover;
	private Image joinPressed;
	private boolean hostOver;
	private boolean joinOver;
	private boolean hostPress;
	private boolean joinPress;
	private boolean p;
	private boolean h;
	
	public JoinHostMenu(int secondmenu) {
		
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		host = new Image("res/Host.png");
		hostHover = new Image("res/HostHover.png");
		hostPressed = new Image("res/HostPressed.png");
		join = new Image("res/Join.png");
		joinHover = new Image("res/JoinHover.png");
		joinPressed = new Image("res/JoinPressed.png");
		hostOver = false;
		joinOver = false;
		hostPress = false;
		joinPress = false;
		p = false;
		h = false;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if(hostOver) {
			hostHover.draw(300, 175);
			join.draw(300, 375);
		}
		else if(joinOver) {
			host.draw(300, 175);
			joinHover.draw(300, 375);
		}
		else if(hostPress) {
			hostPressed.draw(300, 175);
			join.draw(300, 375);
		}
		else if(joinPress) {
			host.draw(300, 175);
			joinPressed.draw(300, 375);
		}
		else {
			host.draw(300, 175);
			join.draw(300, 375);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException {
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		if((posX > 300 && posX < 700) && (posY > 375 && posY < 525)) {
			//host button
			hostOver = true;
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				hostOver = false;
				hostPress = true;
				p = true;
				h = true;
			}
			else if(h && !p) {
				sbg.enterState(3);
			}
			else if(h) {
				p = false;
			}
		}
		else if((posX > 300 && posX < 700) && (posY > 175 && posY < 325)) {
			//join button
			joinOver = true;
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				joinOver = false;
				joinPress = true;
				p = true;
				h = true;
			}
			else if(h && !p) {
				sbg.enterState(3);
			}
			else if(h) {
				p = false;
			}
		}
		else {
			hostOver = false;
			joinOver = false;
			hostPress = false;
			joinPress = false;
			p = false;
			h = false;
		}
	}

	public int getID() {
		return 1;
	}

}
