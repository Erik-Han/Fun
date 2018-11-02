package entities;

import java.awt.*;

public abstract class Entity implements Tools {
	protected double size, spd, dir;
	protected Coordinate cord;
	protected Color col,realCol=null;

	protected int force = 0;
	protected double fDir = 0;

	public Entity(Coordinate c, double sz, double sp, double d, Color color) {
		cord = c;
		size = sz;
		spd = sp;
		dir = d;
		col = color;
	}

	public Entity() {
		this(new Coordinate(), BIRTH_SIZE, 5, Tools.rand(Math.PI * 2), Tools.randCol());
	}

	public Color getColor() {
		return col;
	}
	public void setSpd(int i){
		spd=i;
	}
	public void turnUp(double change) {
		if (dir > Math.PI * 2) {
			System.out.println("ENTITY TURN UP: ANGLE OUT OF BOUNDS :"+dir);
			System.exit(0);
		}
		dir += change;
		dir = (dir + Math.PI * 2) % (Math.PI * 2);
	}

	public double getAng(Entity ent) {
		return cord.getAng(ent.getCord());
	}
public double getSpd(){
	return spd;
}
public void setCord(Coordinate c){
	cord=c;
}
	public void setDir(double d) {
		dir = (d + Math.PI * 2) % (Math.PI * 2);
	}

	public double getDir() {
		return dir;
	}

	public double move() {
		if (force != 0) {
			cord.move(force, fDir);
			force /= 1.3;
			return force*1.3;
		} else {
			cord.move(spd, dir);
			return spd;
		}
	}

	public int outBounds(Coordinate c) {
		if (c.getY() - getRad() <= 0) {
			return 1;
		}
		if (c.getX() + getRad() >= MAX_X) {
			return 2;
		}
		if (c.getY() + getRad() >= MAX_Y) {
			return 3;
		}
		if (c.getX() - getRad() <= 0) {
			return 4;
		}
		return 0;
	}

	public Coordinate getTile() {
		return cord.getTile();
	}

	public void changeSize(double change) {
		size += change;
	}

	public void draw(Graphics g, int zoomX, int zoomY, double times) {
		Color dCol=col;
		if (realCol!=null){
			dCol=realCol;
		}
		Tools.drawCir(cord, getRad(), dCol, g, zoomX, zoomY, times);
		//if (this instanceof Organism)
		//Tools.drawString(cord,getRad(), g, zoomX, zoomY, times, (int)size+"");
	}

	public boolean collisionCheck(Entity ent) {
		return getDist(ent) <= getRad() + ent.getRad();
	}

	public double getDist(Entity ent) {
		return cord.getDist(ent.getCord());
	}

	public double getRad() {
		return Math.sqrt(size / Math.PI);
	}

	public double getSize() {
		return size;
	}

	public Coordinate getCorner() {
		return cord.cordDir(getRad() * Math.sqrt(2), 3 * Math.PI / 4);
	}

	public Coordinate getCord() {
		return cord;
	}

	public void setForce(int f, double d) {
		force = f;
		fDir = d;
	}

	public int getX() {
		return cord.getX();
	}

	public int getY() {
		return cord.getY();
	}

}
