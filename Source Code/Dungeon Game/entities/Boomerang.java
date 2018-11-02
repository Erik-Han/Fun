package entities;

public class Boomerang extends Projectile {
	protected boolean back = false;

	public Boomerang(Coordinate cord, int l, int w, String sprite, int team, int mSpd, double dir, int rg) {
		super(cord, l, w, sprite, team, mSpd, dir, rg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		super.move();
		if (!back && dead) {
			back = true;
			dead = false;
			direction = (direction + Math.PI) % (2 * Math.PI);
			age = 0;
		}
	}

}
