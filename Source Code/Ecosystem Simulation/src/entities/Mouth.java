package entities;

import java.awt.*;

public class Mouth extends Entity {
	private Organism org;

	public Mouth(Organism organ, Color color) {
		org = organ;
		col = color;
	}
	public void setCord(Coordinate c){
		cord=c;
	}
	public double move(){
		return 0;
	}
	public void setSize(double sz){
		size=sz;
	}
	public Organism getOrg(){
		return org;
	}
}
