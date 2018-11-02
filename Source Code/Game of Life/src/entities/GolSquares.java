package entities;

public class GolSquares extends Squares {
	Integer setting;
	boolean first = true;
	public GolSquares(Square[][] squares, Integer set) {
		super(squares);
		// TODO Auto-generated constructor stub
		setting=set;
	}

	public GolSquares(GolSquares squares) {
		super(squares);
	}
	public void setting(int set) {
		setting=set;
	}
	public void step() {
		Squares tempSquares = new Squares(squares);
		for (int c = 0; c < WIDTH_SQUARES; c++) {
			for (int r = 0; r < HEIGHT_SQUARES; r++) {
				Square target = squares[c][r];
				switch (setting) {
				case 1:
					if (getNumRound(target) == 3 || (target.isOn() && getNumRound(target) == 2)) {
						tempSquares.setSquare(c, r, true);
					} else {
						tempSquares.setSquare(c, r, false);
					}
					break;
				//replicator
				case 2:
					if (getNumRound(target) == 1 || getNumRound(target) == 3 || getNumRound(target) == 5 || getNumRound(target) == 7) {
						tempSquares.setSquare(c, r, true);
					} else {
						tempSquares.setSquare(c, r, false);
					}
					break;
				// Seeds
				/*case 2:
					if (getNumRound(target) == 2) {
						tempSquares.setSquare(c, r, true);
					} else {
						tempSquares.setSquare(c, r, false);
					}
					break;*/
				//
				case 3:
					if ((getNumRound(target) == 3 && !target.isOn()) || getNumRound(target) == 5 || getNumRound(target) == 6 || getNumRound(target) == 7 || getNumRound(target) == 8) {
						tempSquares.setSquare(c, r, true);
					} else {
						tempSquares.setSquare(c, r, false);
					}
					break;
				}
			}
		}
		for (int i = 0; i < WIDTH_SQUARES; i++) {
			for (int a = 0; a < HEIGHT_SQUARES; a++) {
				squares[i][a] = new Square(tempSquares.getArray()[i][a]);
			}
		}

	}

	
}
