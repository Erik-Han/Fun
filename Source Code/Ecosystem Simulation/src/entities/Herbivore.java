package entities;

import java.awt.Color;

public class Herbivore extends Organism {
	public Herbivore() {
		super();
		spd = 15;
		attackP=0;
		sightDist=200;
		sightAng=Math.PI;
		eatRate=50;
		col = Color.GREEN;
		multRate=0;
	}

	public double move() {
		if (alert) {
			dir = (dir + Math.PI) % (2 * Math.PI);
			locked = 500;
		}
		if (locked!=0){
			return super.move();
		}
		return super.move(.5);
	}

	public Herbivore(Organism o, Coordinate c, double mult) {
		super(o, c, mult);
	}

	public Organism birth() {
		double direction = (dir + Math.PI) % (Math.PI * 2);// Math.random() * 2									// * Math.PI;
		return new Herbivore(this, cord.cordDir(getRad() * 1.5, direction), multRate);
	}
}
