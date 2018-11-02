package entities;

import engines.Engine;
import toolbox.Tools;

public class KamiSpawner extends Shooter{
	public KamiSpawner(Coordinate cord, int team) {
		super(cord, "pixelguy", team,100, 70, 2, 1,1);
	}
	public KamiSpawner(Coordinate cord) {
		this(cord,0);
	}
	public void move() {
		if (Engine.getClose(this)==null){return;}
		double angle=getAng(Engine.getClose(this));
		//if (Math.random()<.5){
			angle+=(Tools.randInt(3)-1)*Math.PI/5;
	//	}
		Coordinate poss=cord.cordDir(spd, angle);
		int ct=0;
		while ((poss.getTile()==null||poss.getTile() instanceof Wall)&&ct<10){
			angle=getAng(Engine.getClose(this))+(Tools.randInt(2)-1)*Math.PI;
			//System.out.println(Engine.getCt());
			poss=cord.cordDir(spd, angle);
			ct++;
			//System.out.println("lolll");
		}
		cord=poss;
	}

	@Override
	public void shoot() {
		Engine.addEntity(new Kamikaze(cord,team));
	}
	@Override
	public void die(){
		for (int i=0;i<16;i++){
			Engine.addEntity(new Kamikaze(cord,team));
		}
	}

}
