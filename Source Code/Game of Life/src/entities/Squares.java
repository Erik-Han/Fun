package entities;

import main.*;
import java.awt.Graphics;

public class Squares implements Constants {
	protected Square[][] squares = new Square[WIDTH_SQUARES][HEIGHT_SQUARES];
	// protected Squares squares1;
	protected Snake snake;

	public Squares(Square[][] squares) {
		for (int i = 0; i < WIDTH_SQUARES; i++) {
			for (int a = 0; a < HEIGHT_SQUARES; a++) {
				this.squares[i][a] = new Square(squares[i][a]);
			}
		}

	}

	public Squares(Squares squares) {
		this(squares.squares);
	}

	public Square[][] getArray() {
		return squares;
	}

	public void setSquare(int i, int a, boolean stat) {

		squares[i][a].setStat(stat);
	}
	public void setCursor(int i, int a, boolean curs) {

		squares[i][a].setCursor(curs);
	}

	public int getNumRound(Square center) {
		int num = 0;
		// NORTH
		if (squares[center.getXCord()][(center.getYCord() - 1 + HEIGHT_SQUARES) % HEIGHT_SQUARES].isOn()) {
			num++;
		}

		// NORTHEAST
		if (squares[(center.getXCord() + 1) % WIDTH_SQUARES][(center.getYCord() - 1 + HEIGHT_SQUARES) % HEIGHT_SQUARES]
				.isOn()) {
			num++;
		}

		// EAST
		if (squares[(center.getXCord() + 1) % WIDTH_SQUARES][center.getYCord()].isOn()) {
			num++;
		}

		// SOUTHEAST
		if (squares[(center.getXCord() + 1) % WIDTH_SQUARES][(center.getYCord() + 1) % HEIGHT_SQUARES].isOn()) {
			num++;
		}

		// SOUTH
		if (squares[center.getXCord()][(center.getYCord() + 1) % HEIGHT_SQUARES].isOn()) {
			num++;
		}

		// SOUTHWEST
		if (squares[(center.getXCord() - 1 + WIDTH_SQUARES) % WIDTH_SQUARES][(center.getYCord() + 1) % HEIGHT_SQUARES]
				.isOn()) {
			num++;
		}

		// WEST
		if (squares[(center.getXCord() - 1 + WIDTH_SQUARES) % WIDTH_SQUARES][center.getYCord()].isOn()) {
			num++;
		}

		// NORTHWEST
		if (squares[(center.getXCord() - 1 + WIDTH_SQUARES) % WIDTH_SQUARES][(center.getYCord() - 1 + HEIGHT_SQUARES)
				% HEIGHT_SQUARES].isOn()) {
			num++;
		}
		return num;
	}

	/*
	 * public void updateGrid() { squares1 = new Squares(snake.getGrid());
	 * squares = squares1.getArray(); }
	 */

	public void draw(Graphics g) {
		for (int i = 0; i < WIDTH_SQUARES; i++) {
			for (int a = 0; a < HEIGHT_SQUARES; a++) {
				squares[i][a].draw(g);
			}
		}
	}
}
