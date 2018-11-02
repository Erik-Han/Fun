package entities;

import java.awt.*;


public class Tile {
	protected static int length=Tools.LENGTH;
	Coordinate center;
	protected int col, row;
	protected Color color;
private Entity entity;
	public Tile(int c, int r, Color colo) {
		col = c;
		row = r;
		center = new Coordinate(c * length + length / 2, r * length + length / 2);
		color = colo;
	}

	public void draw(Graphics g, int zoomX, int zoomY, double times) {
		Coordinate adjCord = center.zoomCord(zoomX, zoomY, times);
		double adjLength = length * times;
		Tools.drawSquare(adjCord, adjLength, color, g,times);
	}

	public Coordinate getCorner() {
		return center.cordDir((length / 2) * Math.sqrt(2), 3 * Math.PI / 4);
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

}
