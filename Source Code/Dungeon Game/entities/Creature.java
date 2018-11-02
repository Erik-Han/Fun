package entities;

import java.awt.*;

import toolbox.Tools;

public abstract class Creature extends Auto {
	protected int hp;
	protected int maxHp;
	protected int invincible = 0;

	public Creature(Coordinate cord, int l,int w,String sprite, int team, int mSpd, int mxHp) {
		super(cord, l,w,sprite, team, mSpd);
		drawPri = 1;
		maxHp = mxHp;
		hp = maxHp;
		// TODO Auto-generated constructor stub
	}

	public void takeDmg(int dmg) {
		if (invincible == 0) {
			hp -= dmg;
			if (dmg <= 0) {
				hp = Math.min(hp, maxHp);
			} else {
				if (hp <= 0) {
					kill();
				}
				blink = 2;
			}
		}
	}

	public boolean isInv() {
		return invincible != 0;
	}

	@Override
	public void draw(Graphics g, int zoomX, int zoomY, double times) {
		super.draw(g, zoomX, zoomY, times);
		g.setColor(Color.RED);
		String str = hp + "";
		if (invincible != 0) {
			//System.out.println(invincible);
			str = "INVINCIBLE";
			g.setColor(Color.GREEN);
			invincible--;
		}
		Tools.drawString(cord, 50, g, zoomX, zoomY, times, str);

	}

	public int getHp() {
		return hp;
	}
}
