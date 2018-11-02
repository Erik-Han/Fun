package listeners;

import java.awt.event.KeyEvent;
import java.awt.event.*;
import engines.*;
//import entities.Tools;

public class Keyboard implements KeyListener {
	private Engine engine;

	public Keyboard(Engine eng) {
		engine = eng;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (!engine.isLocked()) {
			switch (key) {
			case KeyEvent.VK_RIGHT:
				engine.zoomIn(engine.getZoomX() + 500, engine.getZoomY(), engine.getTimes());
				break;
			case KeyEvent.VK_DOWN:
				engine.zoomIn(engine.getZoomX(), engine.getZoomY() + 500, engine.getTimes());
				break;
			case KeyEvent.VK_LEFT:
				engine.zoomIn(engine.getZoomX() - 500, engine.getZoomY(), engine.getTimes());
				break;
			case KeyEvent.VK_UP:
				engine.zoomIn(engine.getZoomX(), engine.getZoomY() - 500, engine.getTimes());
				break;
			case KeyEvent.VK_N:
				engine.zoomIn(2);
				break;
			case KeyEvent.VK_M:
				engine.zoomIn(.5);
				break;
			case KeyEvent.VK_S:
				engine.setSpd(Math.max(0, engine.getSpd() - 10));
				break;
			case KeyEvent.VK_D:
				engine.setSpd(engine.getSpd() + 10);
				break;
			case KeyEvent.VK_O:
				engine.lock();
				break;
			case KeyEvent.VK_B:
				engine.makeSpec();
				break;
			}
		} else {
			switch (key) {
			case KeyEvent.VK_RIGHT:
				engine.turnUp();
				break;
			case KeyEvent.VK_DOWN:
				engine.birth();
				break;
			case KeyEvent.VK_LEFT:
				engine.turnDown();
				;
				break;
			case KeyEvent.VK_UP:
				engine.look();
				break;
			case KeyEvent.VK_N:
				engine.zoomIn(2);
				break;
			case KeyEvent.VK_M:
				engine.zoomIn(.5);
				break;
			case KeyEvent.VK_S:
				engine.setSpd(Math.max(0, engine.getSpd() - 10));
				break;
			case KeyEvent.VK_D:
				engine.setSpd(engine.getSpd() + 10);
				break;
			case KeyEvent.VK_O:
				engine.lock();
				break;
			case KeyEvent.VK_T:
				engine.changeTeam();
				break;
			case KeyEvent.VK_C:
				engine.changePlayer();
				break;
			case KeyEvent.VK_B:
				engine.makeSpec();
				break;
			case KeyEvent.VK_P:
				engine.power();
				break;
			
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_RIGHT:
			engine.endUp();
			break;
		case KeyEvent.VK_LEFT:
			engine.endDown();
			break;
		}
		// engine.setSpd(10);
	}

	public void keyTyped(KeyEvent e) {

	}

}
