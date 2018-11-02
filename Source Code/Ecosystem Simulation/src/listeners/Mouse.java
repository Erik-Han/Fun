package listeners;

import java.awt.event.*;
import engines.*;
import entities.*;

public class Mouse implements MouseListener {
	private Engine engine;
	private boolean clicked=false;

	public Mouse(Engine eng) {
		engine = eng;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Coordinate adj = (new Coordinate(e.getX(),e.getY()).zoomMouse(engine.getZoomX(), engine.getZoomY(), engine.getTimes()));
		//engine.kraken(adj);
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
