package entities;

import engines.*;
import toolbox.Tools;
import java.util.*;

public class Death extends Boss {
	protected int deathCt = 0;

	public Death(Coordinate cord, int team) {
		super(cord, 300, 300, "Death", team, 500, 100000, 100, 1000, 8);
		projPic = "Shuriken";
	}

	public Death(Coordinate cord) {
		this(cord, 0);
	}

	public void move() {
		if (Engine.getClose(this) == null) {
			return;
		}
		switch (stage) {
		case 0:
			orbit(tiles[Engine.ROWS / 2][Engine.COLS / 2].getCenter());
			break;
		case 1:
		case 2:
		case 3:
			cord = cord.cordDir(spd, getAng(Engine.getClose(this)) + (Math.random() - .5) * Math.PI / 4);
			break;
		case 4:
			cord = cord.cordDir(spd, getAng(Engine.getClose(this)));
			break;
		}
	}

	@Override
	public void shoot() {
		Entity close = Engine.getClose(this);
		if (close == null) {
			return;
		}
		double ang = getAng(close);
		switch (stage) {
		case 0:
			for (int i = -1; i <= 1; i++) {
				shootB(ang + i * Math.PI / 6, 100, 100);
			}
			break;
		case 4:
			if (deathCt % 50 == 0)
				shootB(ang, 500, 500,30);
		case 3:
		case 2:
			if (deathCt % 20 == 0) {
				int orgR = pRg;
				pRg = 20;
				for (int i = 0; i < 10; i++) {
					shootB(ang+Math.PI*2/10*i, 100, 100);
				}
				pRg = orgR;
			}
		case 1:

			shoot(ang, 110, 110);
			break;
		}
		deathCt++;
	}
}