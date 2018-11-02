package entities;

import java.awt.*;
import java.awt.image.*;
import toolbox.*;
import java.util.*;

public class Tile implements Tools {
	protected static int length = Tools.LENGTH;
	Coordinate center;
	protected int col, row;
	//protected ArrayList<Creature> creatures = new ArrayList<Creature>();
	//protected ArrayList<Projectile> projs = new ArrayList<Projectile>();
	protected ImageObject img = new ImageObject(FLOOR_TEXTURE);
protected boolean visible=true;;
	public Tile(int c, int r) {
		if (Math.random()>.5){
			img=new ImageObject("dungeontile2");
		}
		col = c;
		row = r;
		center = new Coordinate(c * length + length / 2, r * length + length / 2);
	}

	public void draw(Graphics g, int zoomX, int zoomY, double times) {
		Coordinate adjCord = center.zoomCord(zoomX, zoomY, times);
		double adjLength = length * times;
		Tools.drawSquare(adjCord, adjLength, Color.WHITE, g, times);
		Tools.drawImage(img.getImage(), center, length, length, g, zoomX, zoomY, times);
		/*
		 * for (Entity ent : occupants) { ent.draw(g, zoomX, zoomY, times); }
		 */
	}

	/*public void addEntity(Entity e) {
		if (e instanceof Creature){
			creatures.add((Creature)e);
			return;
		}
		if (e instanceof Projectile){
			projs.add((Projectile)e);
		}
	}

	public void removeEntity(Entity e) {
		int ind = creatures.indexOf(e);
		if (ind != -1) {
			creatures.remove(ind);
			return;
		}

		ind = projs.indexOf(e);
		if (ind != -1) {
			projs.remove(ind);
		}
	}

	public ArrayList<Creature> getCreatures() {
		return creatures;
	}
	public ArrayList<Projectile> getProjectiles() {
		return projs;
	}*/
public void setVis(boolean b){
	visible=b;
}
public boolean isVis(){
	return visible;
}
	public Coordinate getCorner() {
		return center.cordDir((length / 2) * Math.sqrt(2), 3 * Math.PI / 4);
	}

	public Coordinate getCenter() {
		return center;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}
