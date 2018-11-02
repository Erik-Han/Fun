package entities;

import engines.Engine;
import toolbox.Tools;

public abstract class Orbiter extends Shooter {
	protected Entity primary;
protected int rotDir=1;
protected boolean movingOut=false;
	public Orbiter(Coordinate cord, String sprite, int team, int mSpd,int mxHp, int sSp, int pSp, int pRg,Entity prim) {
		super(cord, sprite, team, mSpd,mxHp, sSp, pSp,pRg);
		primary = prim;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		
		if (primary.dead) {
			return;
		}
		if (!inBounds(cord)){
			kill();
			return;
		}
		cord = cord.cordDir(spd, getAng(primary) + rotDir*Math.PI / 2);
		/*
		 * Coordinate poss = cord.cordDir(spd, getAng(primary) + Math.PI/2); ;
		 * if (poss.getTile() != null) { Tile t = poss.getTile(); if (t != null
		 * && !(t instanceof Wall)) { cord = poss; } else { Coordinate rand
		 * =cordDir(Tools.randInt(4)); while (rand == null || rand.getTile()
		 * instanceof Wall) { rand = cordDir(Tools.randInt(4)); } cord = rand; }
		 * }
		 *
		 */
		
		if (movingOut){
			moveOut();
		}
	}
	public void moveIn(){
		cord=cord.cordDir(spd, getAng(primary));
	}
	public void moveOut(){
		cord=cord.cordDir(spd, getAng(primary)+Math.PI);
	}

}
