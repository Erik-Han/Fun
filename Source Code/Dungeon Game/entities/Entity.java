package entities;

import java.awt.*;
import toolbox.*;
import java.util.*;
import engines.*;

public abstract class Entity implements Tools, Comparable<Entity> {

	// protected boolean dead = false;
	protected Coordinate cord;
	// protected Tile tile;
	protected Sprite sp;
	protected static Tile[][] tiles;
	protected boolean dead;
	protected int length, width;
	protected int team = 0;
	protected int blink = 0;
	protected int drawPri = 0;
	protected int spd = 10;
	protected boolean vis;
	protected int rotDir = (int) (Math.random() * 2) * 2 - 1;
	public Entity link;

	public Entity(Coordinate cor, int l, int w, String sprite, int tm) {
		cord = cor;
		/*
		 * if (cord.getTile() == null) { tile = tiles[0][0]; } else { tile =
		 * tiles[cord.getTile().getX()][cord.getTile().getY()]; }
		 */
		length = l;
		width = w;
		sp = new Sprite(sprite);
		team = tm;
	}

	public void setLink(Entity l) {
		link = l;
		System.out.println(link);
	}

	public void setVis(boolean b) {
		vis = b;
	}

	public boolean isVis() {
		return vis;
	}

	public void setTeam(int i) {
		team = i;
	}

	public int getTeam() {
		return team;
	}

	public double getAng(Entity ent) {
		if (ent == null) {
			return -1;
		}
		return cord.getAng(ent.cord);
	}

	public static void setBoard(Tile[][] ts) {
		tiles = ts;
	}

	public boolean collisionCheck(Entity other) {
		return getDist(other) < length / 2 + other.length / 2;
	}

	public Coordinate cordDir(int dir) {
		int x = -1, y = -1;
		switch (dir) {
		case 0:
			x = getX() + LENGTH;
			y = getY();
			break;
		case 1:
			x = getX();
			y = getY() - LENGTH;
			break;
		case 2:
			x = getX() - LENGTH;
			y = getY();
			break;
		case 3:
			x = getX();
			y = getY() + LENGTH;
			break;
		}
		if (inBounds(x, y)) {
			return new Coordinate(x, y);
		}
		return null;
	}

	public boolean canSee(Entity e) {
		double angle = getAng(e);
		Coordinate curr = cord;
		int times = (int) (getDist(e) / LENGTH);
		for (int i = 0; i < times; i++) {
			curr = curr.cordDir(LENGTH, angle);
			if (!inBounds(curr) || curr.getTile() instanceof Wall) {
				return false;
			}
		}
		return true;
	}

	public void kill() {
		dead = true;
	}

	public int getLength() {
		return length;
	}

	public boolean inBounds(int x, int y) {
		return x >= 0 && x <= Engine.MAX_X && y >= 0 && y <= Engine.MAX_Y;
	}

	public boolean inBounds(Coordinate c) {
		return inBounds(c.getX(), c.getY());
	}

	public void draw(Graphics g, int zoomX, int zoomY, double times) {
		if (blink == 0) {
			Tools.drawImage(sp.getImage(), cord, width, length, g, zoomX, zoomY, times);
		} else {
			blink--;
		}
		// if (this instanceof Organism)
		// Tools.drawString(cord,getRad(), g, zoomX, zoomY, times,
		// (int)size+"");
	}

	public void die() {
	}

	public void teleport() {
		cord = tiles[Tools.randInt(Engine.COLS)][Tools.randInt(Engine.ROWS)].getCenter();
	}

	public void orbit() {
		Entity close = Engine.getClose(this);
		if (close != null) {
			orbit(close.cord);
		}
	}

	public void orbit(Entity ent) {
		orbit(ent.cord);
	}

	public void orbit(Coordinate c) {
		Coordinate poss = cord.cordDir(spd, cord.getAng(c) + rotDir * Math.PI / 2);
		if (poss.getTile() == null || poss.getTile() instanceof Wall) {
			rotDir *= -1;
		} else {
			cord = poss;
		}
	}

	public void spawnOrbs() {
		spawnOrbs(10);
	}

	public void spawnOrbs(int dist) {
		spawnOrbs(10, 3);
	}

	public void spawnOrbs(int dist, int num) {
		for (int i = 0; i < num; i++) {
			Coordinate orbC = cord.cordDir(Tools.LENGTH * dist, Math.PI * 2 * i / num);
			Engine.addEntity(new Satellite(orbC, team, this));
		}
	}

	public void spawnOrbs(int dist, int num, boolean out) {
		for (int i = 0; i < num; i++) {
			Coordinate orbC = cord.cordDir(Tools.LENGTH * dist, Math.PI * 2 * i / num);
			Engine.addEntity(new Satellite(orbC, team, this, out));
		}
	}

	public void spawnSkeletons() {
		for (int i = 0; i < 4; i++) {
			Coordinate c = cordDir(i);
			if (c != null && !(c.getTile() instanceof Wall))
				Engine.addEntity(new Skeleton(c, team));
		}
	}

	public double getDist(Entity ent) {
		return cord.getDist(ent.getCord());
	}

	public Coordinate getCord() {
		return cord;
	}

	public int getX() {
		return cord.getX();
	}

	public int compareTo(Entity other) {
		return this.drawPri - other.drawPri;
	}

	public int getY() {
		return cord.getY();
	}

	public boolean isDead() {
		return dead;
	}

}
