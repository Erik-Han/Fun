package entities;

import main.*;

public class Cursor implements Constants {
	Square square;
	Squares squares;

	public Cursor(int x, int y, Squares squares) {

		this.squares = squares;
		square = squares.getArray()[x][y];
		square.setCursor(true);
	}

	public void cursorMove(int dir) {

		 square.setCursor(false);

		if (dir == 0) {
			square = squares.getArray()[square.getXCord()][(square.getYCord() - 1 + HEIGHT_SQUARES) % HEIGHT_SQUARES];
		}
		if (dir == 1) {
			square = squares.getArray()[(square.getXCord() + 1) % WIDTH_SQUARES][square.getYCord()];
		}
		if (dir == 2) {
			square = squares.getArray()[square.getXCord()][(square.getYCord() + 1) % HEIGHT_SQUARES];
		}
		if (dir == 3) {
			square = squares.getArray()[(square.getXCord() - 1 + WIDTH_SQUARES) % WIDTH_SQUARES][square.getYCord()];
		}

		square.setCursor(true);
	}

	public void click() {
		square.setCursor(false);
		square.setStat(true);
	}

	public Square getSquare() {
		return square;
	}

}
