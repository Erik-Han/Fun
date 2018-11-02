package entities;

import java.awt.Color;

public class Predator extends Organism {

	public Predator(int att) {
		super();
		spd = 5;
		// turnR=.1;	 
		// spasChance=.5;
		attackP=att;
		sightDist=200;
		sightAng=Math.PI;
		eatRate=0;
		col = Color.RED;
		multRate=0;
	}

	public Predator() {
		this(50);
	}
	public Predator(Organism o, Coordinate c, double mult) {
		super(o, c, mult);
	}

	public Organism birth() {
		// changeSize(-500);
		double direction = (dir + Math.PI) % (Math.PI * 2);// Math.random() * 2
															// * Math.PI;
		return new Predator(this, cord.cordDir(getRad() * 1.5, direction), multRate);
	}

	public double move() {
		if (alert) {
			return super.move(2);

		}
		return super.move(.2);
	}
	
}
