package entities;

import engines.Engine;
import toolbox.Tools;

public class Skeleton extends Shooter{

		public Skeleton(Coordinate cord, int team) {
			super(cord, "skeleton", team,500, 10, 100, 400,2);
		}
		public Skeleton(Coordinate cord) {
			this(cord,0);
		}
		public void move() {
			if (Engine.getClose(this)==null){return;}
			double angle=getAng(Engine.getClose(this));
			//if (Math.random()<.5){
				angle+=(Tools.randInt(3)-1)*Math.PI/8;
		//	}
			Coordinate poss=cord.cordDir(spd, angle);
			int ct=0;
			while ((poss.getTile()==null||poss.getTile() instanceof Wall)&&ct<10){
				angle=getAng(Engine.getClose(this))+(Tools.randInt(2)-1)*Math.PI;
				//System.out.println(Engine.getCt());
				poss=cord.cordDir(spd, angle);
				ct++;
			}
			cord=poss;
		}

		@Override
		public void shoot() {
			if (Engine.getClose(this)==null){return;}
			//System.out.println(Engine.getCt());
			shoot(getAng(Engine.getClose(this)));
		}


}
