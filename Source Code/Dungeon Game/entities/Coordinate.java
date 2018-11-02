package entities;

import java.awt.*;

import engines.Engine;
import toolbox.Tools;

public class Coordinate implements Tools {
	private int x, y;

	public Coordinate(int xC, int yC) {
		x = xC;
		y = yC;
		
	}

	public Tile getTile() {
		
		if (x< 0 || x >= Engine.MAX_X || y < 0 || y >= Engine.MAX_Y) {
			return null;
		}
		return Engine.getTiles()[x/LENGTH][y/LENGTH];
	}
	public void remWall(){
		if (x< 0 || x >= Engine.MAX_X || y < 0 || y >= Engine.MAX_Y) {
			return;
		}
		Engine.getTiles()[x/LENGTH][y/LENGTH]=new Tile(x/LENGTH,y/LENGTH);
	}
	public Coordinate(Coordinate other) {
		this(other.getX(), other.getY());
	}

	public Coordinate() {
		this(Tools.randInt(Engine.MAX_X), Tools.randInt(Engine.MAX_Y));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
public boolean inBounds(){
	return x>=0&&x<Engine.MAX_X&&y>=0&&y<Engine.MAX_Y;
}
	public double getDist(Coordinate other) {
		return Math.sqrt(Math.pow((other.getX() - x), 2) + Math.pow(other.getY() - y, 2));
	}

	public Coordinate cordDir(double dist, double ang) {
		if (Double.isNaN(ang)){
			return this;
		}
		return new Coordinate((int) Math.round((x + dist * Math.cos(ang))),
				(int) Math.round((y - dist * Math.sin(ang))));
	}

	public void line(Graphics g, Coordinate other) {
		g.setColor(Color.RED);
		g.drawLine(x, y, other.getX(), other.getY());
	}

	public void text(Graphics g, String s) {
		g.setColor(Color.DARK_GRAY);
		g.drawString(s, x, y);
	}

	public double getAng(Coordinate other) {
		double xDiff = other.getX() - x;
		double yDiff = y - other.getY();
		double ang = Math.atan(yDiff / xDiff);
		if (ang <= 0) {
			ang += Math.PI * 2;
		}
		int rQuad = 0;
		if (xDiff >= 0 && yDiff > 0) {
			rQuad = 1;
		} else {
			if (xDiff < 0 && yDiff >= 0) {
				rQuad = 2;
			} else {
				if (xDiff <= 0 && yDiff < 0) {
					rQuad = 3;
				} else {
					if (xDiff > 0 && yDiff <= 0) {
						rQuad = 4;
					}
				}
			}
		}
		int quad = Tools.quad(ang);
		if (quad != rQuad) {
			return (ang += Math.PI) % (2 * Math.PI);
		}
		return ang;

	}

	public Coordinate zoomCord(int zoomX, int zoomY, double times) {
		//zoomX*=2;
		//zoomY*=2;
		double window_X = SCREEN_LENGTH / times;
		double window_Y=SCREEN_HEIGHT/times;
		double xCor = zoomX - window_X / 2;
		double yCor = zoomY - window_Y / 2;
		double adjX = x - xCor;
		double adjY = y - yCor;
		adjX *= times;
		adjY *= times;
		return new Coordinate((int) Math.round(adjX), (int) Math.round(adjY));
	}
	public Coordinate zoomMouse(int zoomX, int zoomY, double times) {
		double window_X = SCREEN_LENGTH / times;
		double window_Y=SCREEN_HEIGHT/times;
		double xCor = zoomX - window_X / 2;
		double yCor = zoomY - window_Y / 2;
		double adjX = x/times + xCor;
		double adjY = y/times + yCor;
		//adjX *= times;
		//adjY *= times;
		return new Coordinate((int) Math.round(adjX), (int) Math.round(adjY));
	}

	public void move(double dist, double ang) {
		double xDiff = dist * Math.cos(ang);
		double yDiff = dist * Math.sin(ang);
		x += Math.round(xDiff);
		y -= Math.round(yDiff);
	}

	public String toString() {
		return ("(" + x + ", " + y + ")");
	}

}
