package screens;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import entities.*;
import engines.*;
import listeners.*;

public class SimScreen extends JPanel implements Tools {
	private Engine engine;
	private int zoomX = MAX_X / 2, zoomY = MAX_Y / 2;
	private Double times = 1.0;
	private ArrayList<Organism> orgs = new ArrayList<Organism>();
	private ArrayList<Coordinate> lines = new ArrayList<Coordinate>();
	private ArrayList<Coordinate> pts = new ArrayList<Coordinate>();
	private ArrayList<String> text = new ArrayList<String>();
	private Tile[][] tiles;

	// private boolean cont=false;
	public SimScreen(Engine eng) {
		///super("Sim", java.awt.Toolkit.getDefaultToolkit().getScreenSize());
		engine = eng;
	
	}
	/*
	 * public void cont(){ cont=true; }
	 */

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateZoom();
		for (Tile[] arr : tiles) {
			for (Tile t : arr) {
				t.draw(g, zoomX, zoomY, times);
			}
		}
		//double ct = 0, ct2 = 0;
		for (int i = 0; i < orgs.size(); i++) {
			//try {
			Organism o=orgs.get(i);
				o.draw(g, zoomX, zoomY, times);
				/*if (entities.get(i) instanceof Organism) {
					ct += ((Organism) entities.get(i)).getSpd();
					ct2 += ((Organism) entities.get(i)).getAge();
				}*/
			//} catch (IndexOutOfBoundsException a) {
			//}
		}
		for (int i = 0; i < lines.size(); i++) {
			try {
				try {
					Coordinate c = lines.get(i).zoomCord(zoomX, zoomY, times);

					i++;
					if (i < lines.size()) {
						Coordinate c2 = lines.get(i).zoomCord(zoomX, zoomY, times);
						if (c != null && c2 != null) {
							c.line(g, c2);
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			} catch (NullPointerException e) {
			}
		}
		for (

		int i = 0; i < pts.size(); i++)

		{
			pts.get(i).zoomCord(zoomX, zoomY, times).text(g, text.get(i));
		}
		// g.drawLine(1, 1, 1000, 1000);
	}

	public void updateZoom() {
		zoomX = engine.getZoomX();
		zoomY = engine.getZoomY();
		times = engine.getTimes();
	}

	public void setThings(ArrayList<Organism> oList, Tile[][] grid, ArrayList<Coordinate> line,
			ArrayList<Coordinate> points, ArrayList<String> texts) {
		orgs = oList;
		tiles = grid;
		lines = line;
		pts = points;
		text = texts;
	}
}