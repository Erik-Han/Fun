package entities;

import java.awt.*;
import java.util.*;

public class Organism extends Entity {
	protected double turnR, spasChance, birthRate;
	protected int repel;
	protected Mouth mouth;
	protected boolean onWater = false;
	protected double attackP, eatRate, sightDist, sightAng, waterMult, multRate;// ,
	// stamina;
	protected int timidity = 1;
	protected int gen;
	protected double age = 0;
	protected static double statAvg = 100;
	// power,dex,spd,sight
	protected int locked = 1;
	protected boolean controlled = false;
	protected int turnDir = 1;
	protected boolean expDecay;
	protected boolean splits = false;
	protected boolean alert = false;

	public Organism(Coordinate cord, double sz, double sp, double d, Color c, double turnRate, double spasC, int rpl,
			int timid, double attck, double eatR, double sightD, double sightA, double waterM, double birth,
			double mult, int generation, boolean splt) {
		super(cord, sz, sp, d, c);
		turnR = turnRate;
		spasChance = spasC;
		attackP = attck;
		repel = rpl;
		eatRate = eatR;
		sightDist = sightD;
		sightAng = sightA;
		waterMult = waterM;
		multRate = mult;
		birthRate = birth;
		mouth = new Mouth(this, getMouthCol());
		gen = generation;
		timidity = timid;
		splits = splt;

		if (Math.random() < .25) {
			timidity = (timidity + 1) % 3;
		}
		if (Math.random() < .25) {
			timidity = (timidity + 2) % 3;
		}

		/*
		 * if (timidity == 0) { attackP.set(attackP.get() / 2); } if (timidity
		 * == 2) { //spd *= 1.5; }
		 */
	}

	public Organism(Organism o, Coordinate c, double mult) {
		this(c, BIRTH_SIZE, mult(o.spd, mult), Math.random() * Math.PI * 2, o.col, mult(o.turnR, mult),
				mult(o.spasChance, mult), Math.max((int) mult(o.repel, mult), 5), o.timidity, mult(o.attackP, mult),
				mult(o.eatRate, mult), mult(o.sightDist, mult), mult(o.sightAng, mult), mult(o.waterMult, mult),
				mult(o.birthRate, mult), Math.min(mult(o.multRate, mult), .95), o.gen + 1, o.splits);
	}

	public Organism() {
		super();
		turnR = .2;
		spasChance = .25;
		repel = 25;
		attackP = 50;
		eatRate = 50;
		sightAng = Math.PI / 2;
		sightDist = 500;
		waterMult = .2;
		birthRate = .5;
		multRate = .1;
		gen = 1;
		timidity = 1;
		mouth = new Mouth(this, getMouthCol());
		mouth.setCord(getMouthCord());
	}

	public void directive(double d) {
		dir=d;
		turnDir = 0;
		timidity=0;
	}
	
	// MOVEMENT
	public double move(double mult) {
		double rSpd = spd * mult;
		double rTurn = turnR * mult;
		int result = outBounds(cord);

		for (int i = 1; i <= 4; i++) {
			if (result == i) {
				force = 30;
				fDir = (2 * Math.PI - i * (Math.PI / 2) + Math.PI * 2) % (Math.PI * 2);
				turnUp(Math.PI);
				break;
			}
		}
		cord.move(force, fDir);
		force /= 1.3;
		double realrSpd = rSpd;
		if (onWater) {
			realrSpd *= waterMult;
		}
		cord.move(Math.max(realrSpd, 2), dir);
		if (locked != 0) {
			locked--;
		} else {
			if (!controlled) {
				if (Math.random() < spasChance) {

					spas();
				}
				turnUp(rTurn * turnDir);
			} else {
				if (turnDir == 1) {
					turnDown();
				} else {
					if (turnDir == -1) {
						turnUp();
					}
				}
			}
		}
		mouth.setCord(getMouthCord());
		mouth.setSize(size / 4);
		return realrSpd;

	}

	public void spas() {
		turnDir *= -1;
	}

	public void turnUp() {
		super.turnUp(-turnR);
	}

	public void turnDown() {
		super.turnUp(turnR);
	}

	// BIRTH
	public Organism birth(boolean always) {
		if (size > birthRate * 2000 && !always
				&& ((!splits && Math.random() > birthRate * size / 300000) || (splits && Math.random() > birthRate))) {
			return null;
		}
		if (!splits) {
			changeSize(-500);
			return birth();
		} else {
			size /= 2;
			Organism babe = splitBirth();
			babe.size = size;
			return babe;
		}
	}

	public Organism birth() {
		double direction = (dir + Math.PI) % (Math.PI * 2);
		return new Organism(this, cord.cordDir(getRad() * 1.5, direction), multRate);
	}

	public Organism splitBirth() {
		double direction = (dir + Math.PI) % (Math.PI * 2);
		return new Organism(this, cord.cordDir(getRad() * 1.01, direction), multRate);
	}

	// INTERACTIONS

	public boolean hostile(Organism org) {
		if (locked != 0 || org.col.equals(this.col)) {
			return false;
		}
		return true;
	}

	public boolean seenOrg(Organism org) {
		return getMouth().getDist(org) <= sightDist + 2 * getRad()
				&& Math.abs(getMouth().getAng(org) - dir) <= sightAng / 2;
	}

	public void takeDamage(Organism attacker) {
		changeSize(-attacker.getAttack());
		attacker.changeSize(attacker.getAttack() / 10);
	}

	// COMMAND
	public void control() {
		controlled = !controlled;
		turnR = Math.abs(turnR);
	}

	// MUTATORS

	public double move() {
		return move(1);
	}

	public void age(double a) {
		age += a;
	}

	public void setWater(boolean b) {
		onWater = b;
	}

	public void setRCol(Color col){
		realCol=col;
	}
	public void upDir() {
		locked = 1;
		turnUp(Math.abs(turnR));
	}

	public void downDir() {
		locked = 1;
		turnUp(-Math.abs(turnR));
	}

	public void setLocked(int lock) {
		locked = lock;
	}

	public void setAlert(boolean b) {
		alert = b;
	}

	public void setExp(boolean b) {
		expDecay = b;
	}

	public void setSplit(boolean b) {
		splits = b;
	}

	public void setTurnDir(int i) {
		turnDir = i;
	}

	// ACCESSORS

	public boolean getWater() {
		return onWater;
	}

	public double getWaterMult() {
		return waterMult;
	}

	public int isTimid() {
		return timidity;
	}

	public double getDecay() {
		if (expDecay) {
			return 2 * Math.pow(1.5, age);
		}
		return 2 * log(age + .2, 5000) - 1;// Math.pow(age/10-3,3);
	}

	public Mouth getMouth() {
		return mouth;
	}

	public Coordinate getMouthCord() {
		return cord.cordDir(getRad() * (4 / 3), dir);
	}

	public Coordinate getMouthTile() {
		return getMouth().getTile();
	}

	public Color getMouthCol() {
		return new Color((col.getRed() + 128) % 256, (col.getGreen() + 128) % 256, (col.getBlue() + 128) % 256);
	}

	public double getAge() {
		return age;
	}

	public int getRepel() {
		return repel;
	}

	public int getGen() {
		return gen;
	}

	public double getEatRate() {
		return eatRate;
	}

	public double getAttack() {
		return attackP;
	}

	public double getMultRate() {
		return multRate;
	}

	public double getBirthRate() {
		return birthRate;
	}

	public int getTurnDir() {
		return turnDir;
	}

	public boolean isLocked() {
		return locked != 0;
	}

	public boolean isAlert() {
		return alert;
	}

	public Coordinate getNxtTile() {
		return cord.cordDir(Tools.LENGTH, dir).getTile();
	}

	// MISC

	public void draw(Graphics g, int zoomX, int zoomY, double times) {
		super.draw(g, zoomX, zoomY, times);
		mouth.draw(g, zoomX, zoomY, times);

	}

	public static double log(double num, int b) {
		return Math.log10(num) / Math.log10(b);
	}

	public void power() {
		attackP += 100;
		sightDist = 1000;
		sightAng = Math.PI;
		spd = 8;
		turnR = .2;
		eatRate = 100;
	}

	public static double mult(double d, double multRate) {
		double base = 1 - multRate;
		double mult = (multRate * Math.random() * 2 + base);
		return d * mult;
	}
}
