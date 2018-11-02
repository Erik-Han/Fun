package engines;

import main.*;
import entities.*;
import java.util.ArrayList;

public class GolEngine implements Runnable, Constants {
	private GolScreen screen;
	private Square[][] squaresTemp = new Square[WIDTH_SQUARES][HEIGHT_SQUARES];
	private GolSquares squares;
	private Cursor curse;
	private boolean first = true;
	private boolean go = true;
	Integer setting = 0;
	public GolEngine(GolScreen screen) {
		this.screen = screen;

		for (int i = 0; i < WIDTH_SQUARES; i++) {
			for (int a = 0; a < HEIGHT_SQUARES; a++) {
				squaresTemp[i][a] = new Square(i, a);
			}
		}

		squares = new GolSquares(squaresTemp, 1);
		// glider
		/*squares.setSquare(5, 5, true);
		squares.setSquare(6, 5, true);
		squares.setSquare(7, 5, true);
		squares.setSquare(7, 4, true);
		squares.setSquare(6, 3, true);*/
		
		for (int r = 0; r < WIDTH_SQUARES; r++) {
			for (int c = 0; c < HEIGHT_SQUARES; c++) {
				if (Math.random()<.1)
					squares.setSquare(r, c, true);
			}
		}
		// for (int i=0;i<WIDTH_SQUARES;i++){squares.setSquare(i, 30,true);}
		curse = new Cursor(WIDTH_SQUARES / 2, HEIGHT_SQUARES / 2, squares);
		//stopGo();
		Thread thread = new Thread(this);
		thread.start();
	}

	public void moveCursor(int dir) {

		if (!go) {
			curse.cursorMove(dir);
		}
	}

	public void click() {
		curse.click();
	}
	public void reset() {
		for (int r = 0; r < WIDTH_SQUARES; r++) {
			for (int c = 0; c < HEIGHT_SQUARES; c++) {
				if (Math.random()<.1)
					squares.setSquare(r, c, true);
			}
		}
	}
	public void stopGo() {
		go = !go;
		if (!go) {
			curse.getSquare().setCursor(true);
			curse.cursorMove(0);
			curse.cursorMove(2);
		}
	}
	public void setting(int set) {
		squares.setting(set);
	}

	public void run() {
		while (true) {
			if (go) {

				curse.getSquare().setCursor(false);
				first = true;
				squares.step();
			}

			screen.updateSquares(squares);
			screen.repaint();

			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}

		}
	}
}
