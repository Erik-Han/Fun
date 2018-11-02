package entities;

import toolbox.*;

public class Wall extends Tile implements Tools {
	public Wall(int c, int r) {
		super(c, r);
		img = new ImageObject(WALL_TEXTURE);
	}
}
