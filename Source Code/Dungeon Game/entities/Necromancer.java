package entities;

import engines.*;
import toolbox.Tools;
import java.util.*;

public class Necromancer extends Boss {
	double sDir = 0;
	private int NecromancerCt = 0;

	public Necromancer(Coordinate cord, int team) {
		super(cord, 150, 150, "Necromancer", team, 200, 1000, 50, 100, 1000);
		// spawnOrbs();
	}

	public Necromancer(Coordinate cord) {
		this(cord, 0);
	}

	public void move() {
		if (Engine.getClose(this) == null) {
			return;
		}
		switch (stage) {
		case 1:
			break;
		case 2:
			//cord = cord.cordDir(spd, getAng(Engine.getClose(this)));
			break;
		case 3:
			cord = cord.cordDir(spd, getAng(Engine.getClose(this)));
			if (NecromancerCt % 40 == 0) {
				teleport();
			}
			break;
		case 4:
			if (NecromancerCt % 40 == 0) {
				teleport();
				spawnSkeletons();
			}
			break;
		}
		NecromancerCt++;
	}

	@Override
	public void shoot() {
		//System.out.println(link);
		if (Engine.getClose(this) == null) {
			return;
		}
		int num;
		switch (stage) {
		case 0:
			num = 4;
			for (int i = 0; i < num; i++) {
				Engine.addEntity(
						new Projectile(cord, team, pSpd * 4, (sDir + i * Math.PI / (num / 2.0)) % (2 * Math.PI), 100));
				sDir += .1;
			}
			break;
		case 2:
		case 3:
			for (int i = 0; i < 3; i++) {
				double wave = Tools.rand(0, 2) - 1;
				Engine.addEntity(new Projectile(cord, team, pSpd * 4, getAng(Engine.getClose(this)) + .5 * wave, 100));
			}
			break;
		case 4:
			num = 3;
			for (int i = 0; i < num; i++) {
				Engine.addEntity(
						new Projectile(cord, team, pSpd * 4, (sDir + i * Math.PI / (num / 2.0)) % (2 * Math.PI), 100));
				sDir += .1;
			}
			break;
		}
	}

	@Override
	public void setLink(Entity l) {
		super.setLink(l);
		((Boss) link).stage = stage;
		//System.out.println(link);
	}

	@Override
	public void changeStage() {
		super.changeStage();
		
		((Boss) link).stage = stage;
		invincible = 100;
		// Engine.killOrbs();
		Engine.killSkeletons();
		// spawnOrbs();
		switch (stage) {
		case 1:
			//spawnSkeletons();
			//spawnOrbs(1, 7);
			break;
		case 4:
			sSpd *= 2;
			break;
		}
	}
}