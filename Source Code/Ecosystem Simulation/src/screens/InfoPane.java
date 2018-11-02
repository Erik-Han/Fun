package screens;

import engines.*;
import entities.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

public class InfoPane extends JPanel {
	private Info info;
	private Organism controlled;
	private ArrayList<Organism> orgs;
	private ArrayList<Color> species;
	private ArrayList<Integer> specCt;
	// private ArrayList<Organism> specimen;
	private ArrayList<Double> attackP;
	private ArrayList<Double> mult;
	private ArrayList<Double> birth;
	private ArrayList<Double> gen;
	private ArrayList<ArrayList<Integer>> hist = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> startCt;
	int start=0;

	// private int[][] points=new int[2][1000000];
	public InfoPane(Info inf) {
		info = inf;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double total = orgs.size();
		start=Math.max(start, species.size());
		for (int i = 0; i < 10 && i < specCt.size(); i++) {
			g.setColor(species.get(i));
			g.fillOval(50, i * 50 + 25, 30, 30);

			g.setColor(Color.BLACK);
			double perc = (int) (round(specCt.get(i) / total, 4) * 10000) / 100.0;
			g.drawString(perc + "", 100, i * 50 + 50);
			g.drawString("ATK: " + round(attackP.get(i) / specCt.get(i), 2) + "", 200, i * 50 + 50);
			g.drawString("MLT: " + round(mult.get(i) / specCt.get(i), 3) + "", 300, i * 50 + 50);
			g.drawString("BRTH: " + round(birth.get(i) / specCt.get(i), 2) + "", 400, i * 50 + 50);
			g.drawString("GEN: " + round(gen.get(i) / specCt.get(i), 2) + "", 500, i * 50 + 50);

		}
		for (int i = 0; i < start; i++) {
			try{
			g.setColor(species.get(i));}
			catch (IndexOutOfBoundsException e){
				g.setColor(Color.BLACK);
			}
			for (int a = 1; a < hist.get(i).size(); a++) {
				
				g.drawLine(startCt.get(i)/info.getInterval()+a - 1, 800 - hist.get(i).get(a - 1) * 5,startCt.get(i)/info.getInterval()+ a, 800 - hist.get(i).get(a) * 5);
			}
		}
		controlled = info.getControl();
		if (controlled != null) {
			g.setColor(controlled.getColor());
			g.fillOval(100, 600, 50, 50);
			g.drawString("ATK: " + round(controlled.getAttack(), 3), 200, 600);
			g.drawString("GEN: " + controlled.getGen(), 300, 600);
		}
		g.drawString("CONTROLS", 600, 50);
		g.drawString("ARROW KEYS - CHANGE VIEW", 600, 65);
		g.drawString("M - ZOOM OUT", 600, 80);
		g.drawString("N - ZOOM IN", 600, 95);
		g.drawString("S - SPEED UP", 600, 110);
		g.drawString("D - SLOW DOWN", 600, 125);
	}

	public void setLists(ArrayList<Organism> orgs, ArrayList<Color> species, ArrayList<Integer> specCt,
			ArrayList<Double> attackP, ArrayList<Double> mult, ArrayList<Double> birth, ArrayList<Double> gen,
			ArrayList<ArrayList<Integer>> pts,ArrayList<Integer> starts) {
		this.orgs = orgs;
		this.species = species;
		this.specCt = specCt;
		// this.specimen = specimen;
		this.attackP = attackP;
		this.mult = mult;
		this.birth = birth;
		this.gen = gen;
		hist = pts;
		startCt=starts;
		
	}

	public double round(double d, int place) {
		double mult = Math.pow(10, place);
		return Math.round(d * mult) / mult;
	}

}
