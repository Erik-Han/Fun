package entities;

import java.awt.*;
import java.awt.image.*;
import toolbox.*;

public class Sprite implements Tools {
	private ImageObject image;
	//private int width = LENGTH*10, height = LENGTH;

	public Sprite(String file) {
		image = new ImageObject(file);
	}

	public Image getImage() {
		return image.getImage();
	}

}
