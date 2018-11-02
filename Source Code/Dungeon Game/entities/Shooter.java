package entities;

import engines.Engine;
import toolbox.Tools;

public abstract class Shooter extends Creature {
	// shoot delay
	protected int sSpd;
	protected int pSpd, pRg;
	protected String projPic = "poisonshot";

	public Shooter(Coordinate cord, int l, int w, String sprite, int team, int mSpd, int mxHp, int sSp, int pSp,
			int rg) {
		super(cord, l, w, sprite, team, mSpd, mxHp);
		sSpd = sSp;
		pSpd = pSp;
		pRg = rg;
	}

	public Shooter(Coordinate cord, String sprite, int team, int mSpd, int mxHp, int sSp, int pSp, int rg) {
		super(cord, LENGTH, LENGTH, sprite, team, mSpd, mxHp);
		sSpd = sSp;
		pSpd = pSp;
		pRg = rg;
	}

	public boolean canShoot() {
		return (Engine.getCt() - startCt) % (modCon / sSpd) == 0;
	}

	// Tile t, String sprite, int start, int del, int dir, int rg, int team

	public void shoot(double d, int l, int w, String sprite) {
		// Coordinate c = cordDir(d);
		Engine.addEntity(new Projectile(cord, l, w, sprite, team, pSpd, d, pRg));
	}

	public void shoot(int d, String sprite) {
		shoot(d, LENGTH / 2, LENGTH / 2, sprite);
	}

	public void shoot(int d, int l, int w, String sprite) {
		shoot(d * Math.PI / 2, l, w, sprite);
	}
	public void shoot(double d, int l, int w) {
		// Coordinate c = cordDir(d);
		shoot(d,l,w,projPic);
	}
	public void shoot(int d) {
		shoot(d, projPic);
	}

	public void shoot(double d, String sp) {
		shoot(d, LENGTH / 2, LENGTH / 2, sp);
	}

	public void shoot(double d) {
		shoot(d, projPic);
	}

	public void shootB(double d, int l, int w, String sprite) {
		// Coordinate c = cordDir(d);
		Engine.addEntity(new Boomerang(cord, l, w, sprite, team, pSpd, d, pRg));
	}
	public void shootB(double d, int l, int w) {
		// Coordinate c = cordDir(d);
		shootB(d,l,w,projPic);
	}
	public void shootB(int d, String sprite) {
		shootB(d, LENGTH / 2, LENGTH / 2, sprite);
	}

	public void shootB(int d, int l, int w, String sprite) {
		shootB(d * Math.PI / 2, l, w, sprite);
	}

	public void shootB(int d) {
		shootB(d, projPic);
	}
	public void shootB(double d, int l, int w, int rg) {
		Engine.addEntity(new Boomerang(cord, l, w, projPic, team, pSpd, d, rg));
	}
	public void shootB(double d, String sp) {
		shootB(d, LENGTH / 2, LENGTH / 2, sp);
	}

	public void shootB(double d) {
		shootB(d, projPic);
	}

	public abstract void shoot();

}
