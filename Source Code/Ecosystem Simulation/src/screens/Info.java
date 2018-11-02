package screens;

import engines.*;
import entities.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

public class Info {
	private Engine engine;
	private ArrayList<Organism> orgs = new ArrayList<Organism>();
	private ArrayList<Color> species = new ArrayList<Color>();
	private ArrayList<Integer> specCt = new ArrayList<Integer>();
	// private ArrayList<Organism> specimen = new ArrayList<Organism>();
	private ArrayList<Double> attackP = new ArrayList<Double>();
	private ArrayList<Double> mult = new ArrayList<Double>();
	private ArrayList<Double> birth = new ArrayList<Double>();
	private ArrayList<Double> gen = new ArrayList<Double>();
	private ArrayList<ArrayList<Integer>> hist = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> startCt = new ArrayList<Integer>();
	private int ct = 0;
	// private Organism controlled;
	private InfoPane pane;
	private int interval = 100;

	public Info(Engine eng, boolean hasPane) {

		// super("Info", new Dimension(400, 850));

		engine = eng;
		// controlled = engine.getControl();
		orgs = engine.getOrgs();
		if (hasPane) {
			pane = new InfoPane(this);
			setLists(pane);
		}
		/*
		 * for (int i = 0; i < entities.size(); i++) { Entity curr =
		 * entities.get(i); if (curr instanceof Organism) { Organism org =
		 * (Organism) curr; Color col = org.getColor(); int ind = contains(col);
		 * if (ind == -1) { species.add(org.getColor()); specCt.add(1); } else {
		 * specCt.set(ind, specCt.get(ind) + 1); } } }
		 */
	}

	public int getInterval() {
		return interval;
	}

	public InfoPane getPane() {
		return pane;
	}

	public int getSpecCt() {
		return species.size();
	}

	public void repaint() {
		pane.repaint();
	}

	public Organism getControl() {
		return null;// engine.getControl();
	}

	public void setLists(InfoPane pane) {
		pane.setLists(orgs, species, specCt, attackP, mult, birth, gen, hist, startCt);
	}

	public void updateSpecies() {
		ct++;
		for (int i = 0; i < specCt.size(); i++) {
			specCt.set(i, 0);
			// species.set(i,0);
			attackP.set(i, 0.0);
			mult.set(i, 0.0);
			birth.set(i, 0.0);
			gen.set(i, 0.0);
		}
		for (int i = 0; i < orgs.size(); i++) {
			Organism org = orgs.get(i);
			Color col = org.getColor();
			int ind = contains(col);
			if (ind == -1) {
				// specimen.add(new Organism(org));
				species.add(org.getColor());
				specCt.add(1);
				attackP.add(org.getAttack());
				mult.add(org.getMultRate());
				birth.add(org.getBirthRate());
				gen.add((double) org.getGen());
				hist.add(new ArrayList<Integer>());
				startCt.add(ct);
			} else {
				specCt.set(ind, specCt.get(ind).intValue() + 1);
				attackP.set(ind, attackP.get(ind).doubleValue() + org.getAttack());
				mult.set(ind, mult.get(ind).doubleValue() + org.getMultRate());
				birth.set(ind, birth.get(ind).doubleValue() + org.getBirthRate());
				gen.set(ind, gen.get(ind).doubleValue() + org.getGen());
			}
		}

		sort();
		for (int i = 0; i < specCt.size(); i++) {
			if (specCt.get(i) == 0) {
				remove(i);
			}
		}

		if (ct % interval == 0) {
			for (int i = 0; i < specCt.size(); i++) {
				int perc = (int) ((double) specCt.get(i) / orgs.size() * 100);
				hist.get(i).add(perc);
			}
		}
	}

	public double round(double d, int place) {
		double mult = Math.pow(10, place);
		return Math.round(d * mult) / mult;
	}

	public int contains(Color c) {
		for (int i = 0; i < species.size(); i++) {
			if (species.get(i).equals(c)) {
				return i;
			}
		}
		return -1;
	}

	public void sort() {
		sort(0, species.size() - 1);
	}

	public void sort(int left, int right) {
		int l = left;
		int r = right;
		int mid = (l + r) / 2;
		int pivot = specCt.get(mid);
		Color col = species.get(mid);
		while (l <= r) {
			while (specCt.get(l).intValue() >= pivot) {
				if (specCt.get(l).intValue() == pivot) {

					if (compare(species.get(l), col) <= 0) {
						break;
					}
				}
				l++;
			}
			while (specCt.get(r).intValue() <= pivot) {
				if (specCt.get(r).intValue() == pivot) {
					if (compare(species.get(r), col) >= 0) {
						break;
					}
				}
				r--;
			}
			if (l <= r) {
				// if (specCt.get(l) != specCt.get(r)) {
				swap(l, r);
				// }
				l++;
				r--;
			}
		}
		if (l < right) {
			sort(l, right);
		}
		if (r > left) {
			sort(left, r);
		}
	}

	public int compare(Color c1, Color c2) {
		if (c1.getRed() > c2.getRed()) {
			return 1;
		} else {
			if (c1.getRed() == c2.getRed()) {
				if (c1.getBlue() > c2.getBlue()) {
					return 1;
				} else {
					if (c1.getBlue() == c2.getBlue()) {
						if (c1.getGreen() > c2.getGreen()) {
							return 1;
						} else {
							if (c1.getGreen() == c2.getGreen()) {
								return 0;
							}
							return -1;
						}
					} else {
						return -1;
					}
				}
			} else {
				return -1;
			}
		}
	}

	public ArrayList<Integer> getSpecies() {
		return specCt;
	}

	public void swap(int i, int i2) {
		int tmp = specCt.get(i);
		specCt.set(i, specCt.get(i2));
		specCt.set(i2, tmp);
		Color col = species.get(i);
		species.set(i, species.get(i2));
		species.set(i2, col);
		double d = attackP.get(i);
		attackP.set(i, attackP.get(i2));
		attackP.set(i2, d);
		d = mult.get(i);
		mult.set(i, mult.get(i2));
		mult.set(i2, d);
		d = birth.get(i);
		birth.set(i, birth.get(i2));
		birth.set(i2, d);
		d = gen.get(i);
		gen.set(i, gen.get(i2));
		gen.set(i2, d);
		ArrayList<Integer> temp = hist.get(i);
		hist.set(i, hist.get(i2));
		hist.set(i2, temp);
		tmp = startCt.get(i);
		startCt.set(i, startCt.get(i2));
		startCt.set(i2, tmp);
	}

	public void remove(int i) {
		specCt.remove(i);
		// species.add(
		species.remove(i);
		attackP.remove(i);
		mult.remove(i);
		birth.remove(i);
		gen.remove(i);
		// hist.add(hist.remove(i));
	}
}
