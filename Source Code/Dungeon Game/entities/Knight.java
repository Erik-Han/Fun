package entities;

import engines.Engine;
import toolbox.Tools;

public class Knight extends Shooter {
	public Knight(Coordinate cord, int team) {
		super(cord, "knight", team, 100, 200, 50, 300, 3);
		spd=75;
	}

	public Knight(Coordinate cord) {
		this(cord, 0);
	}

	public void move() {
		if (Engine.getClose(this) == null) {
			return;
		}
		double angle = getAng(Engine.getClose(this));
		// if (Math.random()<.5){
		angle += (Tools.randInt(3) - 1) * Math.PI / 16;
		// }
		Coordinate poss = cord.cordDir(spd, angle);
		int ct = 0;
		while ((poss.getTile() == null || poss.getTile() instanceof Wall) && ct < 10) {
			angle = getAng(Engine.getClose(this)) + (Tools.randInt(2) - 1) * Math.PI;
			// System.out.println(Engine.getCt());
			poss = cord.cordDir(spd, angle);
			ct++;
		}
		cord = poss;
	}

	@Override
	public void shoot() {
		if (Engine.getClose(this) == null) {
			return;
		}
		// System.out.println(Engine.getCt());
		int org=pSpd;
		for (int a=1;a<3;a++){
			pSpd=org*a;
			for (int i = -1; i <= 1; i++) {
				shoot(getAng(Engine.getClose(this))+Math.PI/8*i);
			}
		}
		pSpd=org;
		
	}

}
