package entities;

import engines.*;
import toolbox.Tools;
import java.util.*;

public abstract class Boss extends Shooter {
	protected int numStages = 5;
	protected int stage = 0;

	public Boss(Coordinate cord, int l, int w, String sprite, int team, int mSpd, int mxHp, int sSp, int pSp, int rg) {
		super(cord, l, w, sprite, team, mSpd, mxHp, sSp, pSp, rg);
		// TODO Auto-generated constructor stub
	}

	public void setStage(int s) {
		stage = s;
		hp = (int) (maxHp * (1 - s * (1 / numStages)));
	}

	public int getStage() {
		return stage;
	}

	@Override
	public void takeDmg(int dmg) {
		super.takeDmg(dmg);
		if (hp <= getThreshold(stage + 1)) {
			changeStage();
			invincible=100;
		}
	}

	public int getThreshold(int s) {
		// System.out.println((int) (maxHp * (1 - s * (1/numStages))));
		return (int) (maxHp * (1 - s * (1.0 / numStages)));
	}
	public void changeStage(){
		stage++;
	}
}
