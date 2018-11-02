package entities;

public class Satellite extends Orbiter {
	public Satellite(Coordinate cord, int team, Entity prim,boolean out) {
		super(cord, "orbiter", team, 500,100,10,100,3,prim);
		movingOut=out;
		// TODO Auto-generated constructor stub
	}
	public Satellite(Coordinate cord, int team, Entity prim) {
		this(cord,team,prim,false);
		// TODO Auto-generated constructor stub
	}

	public Satellite(Coordinate cord, Entity prim,boolean out) {
		this(cord, 0, prim,out);
		// TODO Auto-generated constructor stub
	}
	public Satellite(Coordinate cord, Entity prim) {
		this(cord, 0, prim,false);
		// TODO Auto-generated constructor stub
	}
	public void shoot() {
		for (int i=0;i<4;i++){
			shoot(i);
		}
	}
}
