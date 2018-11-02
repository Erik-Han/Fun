package entities;

import java.awt.Graphics;

import engines.*;

public abstract class Auto extends Entity {
	protected int mSpd;
	protected int startCt;
	protected int modCon = 1000;


	public Auto(Coordinate cord, int l,int w,String sprite, int tm, int mSp) {
		super(cord, l,w,sprite, tm);
		startCt = Engine.getCt();
		mSpd = mSp;
	}

	public void setMSpd(int i) {
		mSpd = i;
	}

	public double getMSpd() {
		return mSpd;
	}

	public boolean canMove() {
		return (Engine.getCt() - startCt) % (modCon / mSpd) == 0;
	}

	public abstract void move();

	

}
