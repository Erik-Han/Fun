package entities;

import engines.Engine;
import toolbox.Tools;

public class Kamikaze extends Shooter {

	public Kamikaze(Coordinate cord, int team) {
		super(cord, "kamikaze", team, 500, 50, 10, 1000, 3);
	}

	public Kamikaze(Coordinate cord) {
		this(cord, 0);
	}

	public void move() {
		if (Engine.getClose(this) == null) {
			return;
		}
		if (Engine.getPlayer().getDist(this)<100) {
			shoot();
		}
		
		double angle = getAng(Engine.getClose(this));
		// if (Math.random()<.5){
		angle += (Tools.randInt(3) - 1) * Math.PI / 8;
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
		Entity e=Engine.getClose(this);
		if (e!=null&&getDist(e)<=20){
			kill();
		}
		takeDmg(10);
	}

	@Override
	public void die() {
		for (int i = 0; i < 16; i++) {
			shoot(i * (2 * Math.PI) / 16);
		}
	}
}
