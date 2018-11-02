package entities;

import main.*;

public class Snake implements Constants {
	private Square[] snake;
	private int direction;
	private Squares squares;
	private boolean alive = true;

	public Snake(Square[][] squares, int dir, int length) {

		this.squares = new Squares(squares);
		snake = new Square[length];
		for (int i = 0; i < snake.length; i++) {
			snake[i] = squares[5 + i][5];
		}

		direction = dir;
	}

	public Square getIndex(int i) {
		return snake[i];
	}

	public int getLength() {
		return snake.length;
	}

	public int getX() {
		return snake[0].getXCord();
	}

	public int getY() {
		return snake[0].getYCord();
	}

	public Snake(Squares squares, int dir, int col) {
		snake = new Square[8];
		this.squares = new Squares(squares);
		for (int i = 0; i < 8; i++) {
			snake[i] = squares.getArray()[col][(1 - i + HEIGHT_SQUARES) % HEIGHT_SQUARES];
		}

		direction = dir;
	}

	public void setHead(int dir) {
		if (direction % 2 == dir % 2) {
		} else {
			direction = dir;
		}
	}

	public Square[][] getGrid() {
		return squares.getArray();
	}

	public void step() {
		if (alive){
			snake[snake.length - 1].setStat(false);

			for (int i = snake.length - 1; i > 0; i--) {
				snake[i] = snake[i - 1];
			}
			// UP
			if (direction == 0) {
				snake[0] = squares.getArray()[snake[0].getXCord()][(snake[0].getYCord() - 1 + HEIGHT_SQUARES)
						% HEIGHT_SQUARES];

			}
			// RIGHT
			if (direction == 1) {
				snake[0] = squares.getArray()[(snake[0].getXCord() + 1) % WIDTH_SQUARES][snake[0].getYCord()];

			}
			// DOWN
			if (direction == 2) {
				snake[0] = squares.getArray()[snake[0].getXCord()][(snake[0].getYCord() + 1) % HEIGHT_SQUARES];

			}
			// LEFT
			if (direction == 3) {
				snake[0] = squares.getArray()[(snake[0].getXCord() - 1 + WIDTH_SQUARES) % WIDTH_SQUARES][snake[0]
						.getYCord()];

			}
			for (int i = 1; i < snake.length; i++) {
				if (snake[0] == snake[i]) {
					System.exit(0);
				}
			}
			for (int i = 0; i < snake.length; i++) {
				snake[i].setStat(true);
			}
		}

	}

	public void detectCollision(Snake two) {
		
			for (int i = 0; i < two.getLength(); i++) {
				if (getIndex(0).equals(two.getIndex(i))) {
					alive = false;
				}
			
		}
	}

}
