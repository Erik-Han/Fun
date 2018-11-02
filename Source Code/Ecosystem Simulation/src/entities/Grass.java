package entities;

import java.awt.Color;

public class Grass extends Tile {
	private double growthR;
	private double grass;
	private static double max = 1500,cap=3000;
	public Grass(int c, int r, double rate) {
		super(c, r, new Color(255, 255, 255));
		// TODO Auto-generated constructor stub
		growthR = rate;
	}

	public void grow() {
		if (grass >= max) {
			return;
		}
		grass += growthR;
		updateCol();
	}

	public double eat(double amt) {
		if (grass >= amt) {
			grass -= amt;
		} else {
			amt = grass;
			grass = 0;
		}
		return amt/10;
	}
	
	public boolean edible(){
		return grass>=10;
	}
	public void updateCol(){
		int value =(int) (220 -220*grass/cap);
		if (value<0){
			value=0;
		}
		color = new Color(value, 255, value);
	}

	public void addGrass(int change) {
		grass += change;
		if (grass<0){
			grass=0;
		}
		grass=Math.min(grass, cap);
	}

}
