package entities;

import engines.Engine;
import toolbox.Tools;

public class Ram extends Shooter {
	double dir = 0;
	int moveCt = 0;

	public Ram(Coordinate cord, int team) {
		super(cord, "Ram", team, 1000, 500, 50, 300, 3);
		spd=20;
	}

	public Ram(Coordinate cord) {
		this(cord, 0);
	}

	public void move() {
		Entity e = Engine.getClose(this);
		if (moveCt % 100 == 0) {
			if (e != null) {
				dir = getAng(e);
			}

		}
		// if (Math.random()<.5){
		// }
		Coordinate poss = cord.cordDir(spd, dir);
		if (!(poss.getTile() == null || poss.getTile() instanceof Wall)) {
			cord = poss;
		}else{
			if (poss.getTile() instanceof Wall){
			poss.remWall();
			}
		}
		moveCt++;

	}

	@Override
	public void shoot() {
		// System.out.println(Engine.getCt());
		int org = pSpd;
		for (int a = 1; a < 3; a++) {
			pSpd = org * a;
			for (int i = -1; i <= 1; i++) {
				shoot(dir+i*Math.PI/16);
			}
		}
		pSpd = org;

	}

}
