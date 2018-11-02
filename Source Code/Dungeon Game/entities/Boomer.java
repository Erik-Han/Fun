package entities;

import engines.*;

public class Boomer extends Shooter {

	public Boomer(Coordinate cord, int team) {
		super(cord, 200, 200, "Boomer", team, 700, 500, 10, 500, 5);
	}

	public Boomer(Coordinate cord) {
		this(cord, 0);
	}

	@Override
	public void shoot() {
		int num = 20;
		for (int i = 0; i < num; i++) {
			shootB(i * (2 * Math.PI / num), 100,100,"Shuriken");
		}
	}

	@Override
	public void move() {
		orbit(new Coordinate(Engine.MAX_X / 2, Engine.MAX_Y / 2));
	}

}
