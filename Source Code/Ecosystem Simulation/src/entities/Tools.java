
package entities;

import java.awt.*;

import entities.Coordinate;

public interface Tools {
	final int MAX_X = 10000;
	final int MAX_Y = 10000;
	final int LENGTH = 100;
	final int COLS = MAX_X / LENGTH, ROWS = MAX_Y / LENGTH;
	final int BIRTH_SIZE = 500, DEATH_SIZE = 300;
	final int SCREEN_LENGTH = 1500 / 2, SCREEN_HEIGHT = 850;
	final int BAD = 0;
	final int BAD_Y = 50;
	final Coordinate center=new Coordinate(MAX_X/2,MAX_Y/2);

	static int quad(double ang) {
		if (ang <= Math.PI / 2) {
			return 1;
		}
		if (ang <= Math.PI) {
			return 2;
		}
		if (ang <= Math.PI * 3 / 2) {
			return 3;
		}
		return 4;

	}

	static int randInt(int top) {
		return (int) (Math.random() * top);
	}

	static int randInt(int bot, int top) {
		return (int) (Math.random() * top) + bot;
	}

	static double rand(double top) {
		return Math.random() * top;
	}

	static double rand(double bot, double top) {
		return (Math.random() * top) + bot;
	}

	static Color randCol() {
		return new Color(randInt(256), randInt(256), randInt(256));
	}

	static void drawCir(Coordinate c, double radius, Color col, Graphics g, int zoomX, int zoomY, double times) {

		double adjRad = radius * times;
		Coordinate corner = c.zoomCord(zoomX, zoomY, times).cordDir(adjRad * Math.sqrt(2), 3 * Math.PI / 4);
		// Coordinate winCorner=new Coordinate(zoomX,zoomY).cord
		((Graphics2D) g).setStroke(new BasicStroke(1));
		g.setColor(Color.BLACK);
		// g.drawString(corner.getX()+" "+corner.getY(), corner.getX(),
		// corner.getY());
		g.drawOval(corner.getX(), corner.getY(), (int) (radius * times * 2), (int) (radius * times * 2));
		g.setColor(col);
		g.fillOval(corner.getX(), corner.getY(), (int) (radius * times * 2), (int) (radius * times * 2));
	}

	static void drawSquare(Coordinate c, double length, Color col, Graphics g, double times) {
		Coordinate corner = c.cordDir(length / 2 * Math.sqrt(2), 3 * Math.PI / 4);
		((Graphics2D) g).setStroke(new BasicStroke(2));

		g.setColor(Color.BLACK);

		// Coordinate ce=c;
		// g.drawString(ce.toString(), ce.getX(), ce.getY());
		g.drawRect(corner.getX(), corner.getY(), (int) length, (int) length);
		g.setColor(col);
		g.fillRect(corner.getX(), corner.getY(), (int) length, (int) length);
	}

	static void drawRect(Coordinate c, double length, Color col, Graphics g, int zoomX, int zoomY, double times) {

		double adjL = length * times;
		Coordinate corner = c.zoomCord(zoomX, zoomY, times).cordDir((adjL) * Math.sqrt(2), 3 * Math.PI / 4);
		// Coordinate winCorner=new Coordinate(zoomX,zoomY).cord
		((Graphics2D) g).setStroke(new BasicStroke(2));
		g.setColor(Color.BLACK);
		// g.drawString(corner.getX()+" "+corner.getY(), corner.getX(),
		// corner.getY());
		g.drawRect(corner.getX(), corner.getY(), (int) (length * times * 2), (int) (length * times * 2));
		g.setColor(col);
		g.fillRect(corner.getX(), corner.getY(), (int) (length * times * 2), (int) (length * times * 2));
	}

	static void drawString(Coordinate c, double radius, Graphics g, int zoomX, int zoomY, double times, String s) {

		double adjRad = radius * times;
		Coordinate corner = c.zoomCord(zoomX, zoomY, times).cordDir(adjRad * Math.sqrt(2), 3 * Math.PI / 4);
		// Coordinate winCorner=new Coordinate(zoomX,zoomY).cord
		((Graphics2D) g).setStroke(new BasicStroke(2));
		g.setColor(Color.BLACK);
		// g.drawString(corner.getX()+" "+corner.getY(), corner.getX(),
		// corner.getY());
		g.drawString(s, corner.getX(), corner.getY());
	}

	static double sig(double d) {
		return 1 / (1 + Math.pow(Math.E, -d));
	}

}
