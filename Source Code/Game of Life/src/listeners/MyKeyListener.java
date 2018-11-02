package listeners;

import java.awt.event.KeyEvent;
import java.awt.event.*;
import engines.*;

public class MyKeyListener implements KeyListener {
	GolEngine golEngine;
	
	public MyKeyListener(GolEngine engine) {
		this.golEngine = engine;
	}

	public void keyPressed(KeyEvent e) {
		int location = e.getKeyCode();
		if (location == KeyEvent.VK_W) {
			golEngine.moveCursor(0);
		}
		if (location == KeyEvent.VK_D) {
			golEngine.moveCursor(1);
		}
		if (location == KeyEvent.VK_S) {
			golEngine.moveCursor(2);
		}
		if (location == KeyEvent.VK_A) {
			golEngine.moveCursor(3);
		}
		if (location == KeyEvent.VK_P) {
			golEngine.stopGo();
		}
		if (location == KeyEvent.VK_E) {
			golEngine.click();
		}
		if (location == KeyEvent.VK_R) {
			golEngine.reset();
			;
		}
		if (location == KeyEvent.VK_1) {
			golEngine.setting(1);
		}
		if (location == KeyEvent.VK_2) {
			golEngine.setting(2);
		}
		if (location == KeyEvent.VK_3) {
			golEngine.setting(3);
		}
		/*if (location == KeyEvent.VK_4) {
			golEngine.setting(4);
		}*/

	}
	// engine.setHead(2);
	// System.out.println("lol");
	// TODO Auto-generated method stub

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
