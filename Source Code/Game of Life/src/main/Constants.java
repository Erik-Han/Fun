package main;

public interface Constants {
	final double SCALE = 1;
	final int SQUARE_SIZE = scaled(10);
	final int HEIGHT_SQUARES = 140;
	final int WIDTH_SQUARES = 140;
	final int WINDOW_WIDTH = SQUARE_SIZE * WIDTH_SQUARES;
	final int WINDOW_HEIGHT = SQUARE_SIZE * HEIGHT_SQUARES;

	static int scaled(int target) {
		return (int) (target * SCALE);
	}

}
