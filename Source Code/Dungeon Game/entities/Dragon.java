package entities;

import engines.Engine;
import toolbox.Tools;

public class Dragon extends Shooter {
	int shootCt = 0;
	boolean orbiting = false;;

	public Dragon(Coordinate cord, int team) {
		super(cord, 200, 200, "Dragon", team, 400, 400, 80, 400, 5);
	}

	public Dragon(Coordinate cord) {
		this(cord, 0);
	}

	public void move() {
		if (Math.random() < .01) {
			if (orbiting) {
				if (Math.random() < .25) {
					orbiting = false;
				}
			} else {
				orbiting = true;
			}
		}

		if (Math.random() < .05) {
			rotDir *= -1;
		}
		Entity e = Engine.getClose(this);
		/*
		 * if (e != null) { if (getDist(e) >= pRg * LENGTH * 2) { orbiting =
		 * false; } else { if (getDist(e) < pRg * LENGTH) { orbiting = true; } }
		 * }
		 */
		if (Engine.getClose(this) == null) {
			return;
		}
		if (!orbiting) {

			double angle = getAng(e);
			// if (Math.random()<.5){
			angle += (Tools.randInt(3) - 1) * Math.PI / 8;
			// }
			Coordinate poss = cord.cordDir(spd, angle);
			int ct = 0;
			while ((poss.getTile() == null || poss.getTile() instanceof Wall) && ct < 10) {
				angle = getAng(e) + (Tools.randInt(2) - 1) * Math.PI; 	
				// System.out.println(Engine.getCt());
				poss = cord.cordDir(spd, angle);
				ct++;
			}
			cord = poss;

		} else {
			orbit(e);
		}

	}

	public void changeOrb() {
		orbiting = !orbiting;
	}

	@Override
	public void shoot() {
		Entity close = Engine.getClose(this);
		if (close == null) {
			return;
		}
		// System.out.println(Engine.getCt());
		shoot(getAng(close), LENGTH * 2, LENGTH * 2, "fireball2");
		if (shootCt % 20 == 0) {
			int org = pRg;
			pRg *= 2;
			for (int i = -8; i <= 8; i+=4) {
				shoot(getAng(Engine.getClose(this)) + i * Math.PI / 48, LENGTH * 2, LENGTH * 2, "fireball");
			}
			pRg = org;
		}
		shootCt++;
	}
}
