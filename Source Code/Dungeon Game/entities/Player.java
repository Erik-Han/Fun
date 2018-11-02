package entities;

import engines.Engine;
import java.util.*;

public class Player extends Shooter {

	private int moveT = -1000, shootT = -10000;
	//private int lastDmg = 0;

	public Player(Coordinate cord) {
		super(cord, "player", 1, 100, 200, 10, 1000, 8);
		spd=10;
	}

	public void move() {

	}

	public void move(int i) {
		if (Engine.getCt() - moveT >= (100 / mSpd)) {
			Coordinate poss = cord.cordDir(spd, i * (Math.PI / 2));
			;

			if (poss.getTile() != null) {
				Tile t = poss.getTile();
				if (t != null && !(t instanceof Wall)) {
					moveT = Engine.getCt();
					cord = cord.cordDir(spd, i * (Math.PI / 2));
				}
			}
		}
	}
	// public void

	@Override
	public void shoot(int i) {
		if (Engine.getCt() - shootT >= (100 / sSpd)) {
			shoot(i, 100,100,"Shuriken");
			shootT = Engine.getCt();
		}
	}

	public void sees() {
		int num = 180;
		cord.getTile().setVis(true);
		for (int i = 0; i < num; i++) {
			boolean inWall = false;
			double angle = i * 2 * Math.PI / num;
			Coordinate curr = cord;
			while (true) {
				curr = curr.cordDir(LENGTH, angle);
				Tile t = curr.getTile();
				if (!inWall) {
					if (t != null && !(t instanceof Wall)) {
						t.setVis(true);
					} else {
						if (curr.getTile() != null) {
							curr.getTile().setVis(true);
							if (curr.getTile() instanceof Wall) {
								inWall = true;
								continue;
							}
						}
						break;
					}
				} else {
					if (t != null && t instanceof Wall) {
						t.setVis(true);
					} else {
						break;
					}
				}
			}
		}
	}

	@Override
	public void takeDmg(int dmg) {
		super.takeDmg(dmg);
		/*if (dmg >= 0) {
			lastDmg = Engine.getCt();
		}*/
	}

	public void shoot() {

	}

	public int getHeal() {
		return 8;
	}
}
