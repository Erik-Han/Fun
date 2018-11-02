package entities;

import java.awt.Graphics;

import toolbox.*;

public class Projectile extends Auto implements Tools {
	protected double direction;
	protected int range;
	protected int age = 0;
	// int dmg;

	public Projectile(Coordinate cord, int l,int w,String sprite, int team, int mSpd, double dir, int rg) {
		super(cord,l,w, sprite, team,mSpd);
		// TODO Auto-generated constructor stub
	//	sp = new Sprite(sprite);
		direction = dir;
		range = rg;
		this.mSpd = mSpd;
	}
	
	public Projectile(Coordinate cord, String sprite, int team, int mSpd, double dir, int rg) {
		this(cord,LENGTH/2,LENGTH/2, sprite, team,mSpd,dir,rg);
	}

	public Projectile(Coordinate cord, int team, int mSpd, double dir, int rg) {
		this(cord, "poisonshot", team, mSpd, dir, rg);
	}

	@Override
	public void move() {
		cord = cord.cordDir(20, direction);
		age += 20;
		Tile t = cord.getTile();

		if (t == null || t instanceof Wall || age >= range * LENGTH) {
			dead = true;
		}
	}
}
