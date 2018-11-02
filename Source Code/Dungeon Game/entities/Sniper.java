package entities;

import engines.*;
import toolbox.Tools;
import java.util.*;

public class Sniper extends Shooter {

	public Sniper(Coordinate cord, int team) {
		super(cord, "Sniper", team, 200, 50, 20, 500, 100);
		// TODO Auto-generated constructor stub
	}

	public Sniper(Coordinate cord) {
		this(cord, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		Entity close = Engine.getClose(this);
		if (close == null) {

			return;
		}
		Coordinate poss = cord.cordDir(spd, getAng(close) + rotDir * Math.PI / 2);
		if (poss.getTile() == null || poss.getTile() instanceof Wall) {
			rotDir *= -1;
		} else {
			cord = poss;
		}
	}

	@Override
	public void shoot() {

		if (Engine.getClose(this) == null) {
			return;
		}
		Engine.addEntity(new Projectile(cord, team, pSpd, getAng(Engine.getClose(this)), pRg));
	}
}
