package engines;

import screens.*;
import entities.*;
import java.util.*;

import javax.swing.JOptionPane;

import java.awt.*;

public class Engine implements Runnable, Tools {
	protected SimScreen screen;
	protected Info info;
	protected ArrayList<Organism> orgs = new ArrayList<Organism>(1000);
	protected int zoomX = MAX_X / 2, zoomY = MAX_Y / 2;
	protected double times = .5;
	protected boolean paused = false;
	protected ArrayList<Coordinate> pts = new ArrayList<Coordinate>();
	protected ArrayList<String> text = new ArrayList<String>();
	protected ArrayList<Coordinate> lines = new ArrayList<Coordinate>();
	protected Tile[][] tiles = new Tile[COLS][ROWS];
	protected Grass[] grass;
	protected int spd = 1;
	protected boolean locked = false, looking = false, alert = false;
	protected Organism controlled = null;
	protected Color currTeam = null;
	protected boolean vis;
	protected boolean stop = false;

	public Engine(boolean visible) {
		vis = visible;
		setOrgs(1, 50);
		setEnv();
		if (vis) {
			screen = new SimScreen(this);
			screen.setThings(orgs, tiles, lines, pts, text);
		}
		info = new Info(this, vis);

	}

	public void start() {
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		while (!stop) {
			if (!paused) {
				orgs.get(0).setRCol(Color.BLACK);
				if (locked) {

					// command stuff
					if (controlled == null) {
						if (currTeam == null) {
							controlled = orgs.get(orgs.size() - 1);
							currTeam = controlled.getColor();
						} else {
							int i;
							for (i = 0; i < orgs.size(); i++) {
								if (orgs.get(i).getColor().equals(currTeam)) {
									controlled = (Organism) orgs.get(i);
									break;
								}
							}
							if (i >= orgs.size()) {
								controlled = (Organism) orgs.get(orgs.size() - 2);
								currTeam = controlled.getColor();
							}
						}
						controlled.control();
					}
					zoomX = controlled.getX();
					zoomY = controlled.getY();

				} else {
					if (controlled != null) {
						controlled.control();
						controlled.setTurnDir(1);
						controlled = null;
					}
					currTeam = null;
				}

				// see organisms
				for (int i = 0; i < orgs.size(); i++) {
					Organism o = orgs.get(i);

					if (o == controlled && !alert) {
						o.setAlert(false);
						continue;
					}
					Organism closest = null;
					for (int a = 0; a < orgs.size(); a++) {
						Organism o2 = orgs.get(a);
						if (i == a) {
							continue;
						}
						if (o.seenOrg(o2)) {
							if (closest == null) {
								closest = o2;
							} else {
								if (o.getDist(o2) < o.getDist(closest)) {
									closest = o2;
								}
							}
						}
					}
					if (closest == null) {
						o.setAlert(false);
						continue;
					}

					// int result = o.hostile(closest);
					if (o.hostile(closest)) {
						double angDiff = o.getAng(closest) - o.getDir();
						o.setAlert(true);
						if (!(o instanceof Herbivore)) {
							lines.add(o.getMouthCord());
							lines.add(closest.getCord());

							if (angDiff > 0) {
								o.upDir();
							} else {
								if (angDiff < 0) {
									o.downDir();
								}
							}
						}
					} else {
						o.setAlert(false);
					}
					if (o == controlled && alert) {
						o.setAlert(true);
						continue;
					}
				}

				for (int i = orgs.size() - 1; i >= 0; i--) {
					Organism o = orgs.get(i);
					Tile tile = getTile(o.getTile());
					if (o != controlled)
						o.changeSize(-o.getDecay());
					o.age(.01);
					Tile eat = getTile(o.getMouthTile());
					if (o != controlled && o.getEatRate() != 0 && !o.isLocked() && !o.isAlert() && eat != null
							&& eat instanceof Grass && ((Grass) eat).edible()) {

						o.changeSize(((Grass) eat).eat((o).getEatRate()));
					} else {
						// all move
						if (o.getSize() >= 3000 || o.getAge() >= 50) {
							// System.out.println("lolllllllllllllllllllllllll");
							if (o.getTurnDir() != 0) {

								o.directive(o.getAng(getNearEn(o)));
							}
						}

						double loss = 0;
						loss = -Math.pow(1.1, 3 * o.move()) / 40;
						if (o != controlled)
							o.changeSize(loss);
					}
					if (tile != null) {
						if (tile instanceof Grass) {
							o.setWater(false);
						} else {
							if (tile instanceof Water) {
								o.setWater(true);
							}
						}
					}

				}

				// check collision
				for (int i = 0; i < orgs.size(); i++) {
					Organism o = orgs.get(i);
					for (int a = 0; a < orgs.size(); a++) {
						if (i == a) {
							continue;
						}
						Organism o2 = orgs.get(a);
						if (o != null && o2 != null && o.collisionCheck(o2)) {
							// if (e instanceof Organism && e2 instanceof
							// Organism) {
							//
							double fact = (o.getRad() + o2.getRad()) / o.getDist(o2);
							o.setForce((int) (Math.max(((Organism) o2).getRepel() / (o.getSize() / 100), 5) * fact),
									o2.getAng(o));
							o2.setForce((int) (Math.max(((Organism) o).getRepel() / (o2.getSize() / 100), 5) * fact),
									o.getAng(o2));
						}
						if (o.hostile(o2) && o.getMouth().collisionCheck(o2)) {
							o2.takeDamage(o);
							if (o2.isTimid() == 2) {
								o2.setDir(o.getAng(o2));
								o2.setLocked(0);
							} else {
								if (o2.isTimid() == 0) {
									o2.setDir(o2.getAng(o));
								}
							}
						}
					}
				}
			}

			// after combat
			for (int i = orgs.size() - 1; i >= 0; i--) {
				Organism o = orgs.get(i);
				if (o.getSize() <= DEATH_SIZE) {
					if (orgs.get(i) == controlled) {
						controlled = null;
					}
					orgs.remove(i);
				}
			}

			// breed
			for (int i = 0; i < orgs.size(); i++) {
				if (orgs.get(i) != controlled) {
					Organism o = (Organism) orgs.get(i);
					if (o.getSize() > 2000) {
						Organism babe = o.birth(false);
						if (babe != null) {
							orgs.add(babe);
						}
					}
					// pts.add(o.getCord());
					// text.add(o.getWater()+" "+o.getWaterMult());
				}
			}
			// update color of grass
			for (Grass g : grass) {
				g.grow();
				g.updateCol();
			}
			info.updateSpecies();
			if (vis) {
				info.repaint();

				screen.repaint();
			}
			if (spd > 0) {
				try {
					Thread.sleep(spd);
				} catch (Exception e) {
				}
			}

			lines.clear();
			pts.clear();
			text.clear();

		}
	}

	public Organism getNearEn(Organism o) {
		Color col = o.getColor();
		for (int i = 0; i < orgs.size(); i++) {
			Organism curr = orgs.get(i);
			if (!curr.getColor().equals(col)) {
				return curr;
			}
		}
		return null;
	}

	public void power() {
		controlled.power();
	}

	public void stop() {
		stop = true;
	}

	public void setOrgs() {
		for (int i = 0; i < 300; i++) {
			Organism curr = new Organism();
			orgs.add(curr);
		}

	}

	public void setOrgs(int choice, int... para) {
		switch (choice) {
		case 0:
			for (int i = 0; i < 300; i++) {
				Organism curr = new Organism();
				orgs.add(curr);
			}
			break;
		case 1:
			for (int i = 0; i < 150; i++) {
				Organism curr = new Herbivore();
				orgs.add(curr);
			}
			for (int i = 0; i < 50; i++) {
				Organism curr = new Predator(para[0]);
				orgs.add(curr);
			}
			break;
		}

	}

	public void setEnv() {
		int numGrass = 0;
		for (int c = 0; c < COLS; c++) {
			for (int r = 0; r < ROWS; r++) {
				Tile curr;
				if (Math.abs(ROWS / 2 - r) > ROWS / 30 && Math.abs(COLS / 2 - c) > COLS / 30
				// true
				) {
					curr = new Grass(c, r, Math.random() * (1 * 300 / 1000.0));
					numGrass++;
				} else {
					curr = new Water(c, r, Color.BLACK);
				}
				tiles[c][r] = curr;
			}
		}
		grass = new Grass[numGrass];
		int ct = 0;
		for (int c = 0; c < COLS; c++) {
			for (int r = 0; r < ROWS; r++) {
				if (tiles[c][r] instanceof Grass) {
					grass[ct++] = (Grass) tiles[c][r];
				}
			}
		}
	}

	public Info getInfo() {
		return info;
	}

	public void makeSpec() {
		int amt = Integer.parseInt(JOptionPane.showInputDialog(screen, "How many?"));
		for (int i = 0; i < amt; i++) {
			Organism curr = new Organism();
			orgs.add(curr);
		}
	}

	public void kraken(Coordinate c) {
		Organism player = new Organism(c, 500000, 2, 0, Color.BLUE, .2, .1, 1000, 0, 500, 25, 1000, Math.PI / 3, .5,
				.01, .1, 1, true);
		// player.setExp(true);
		orgs.add(player);
		// entities.add(player.getMouth());
	}

	public void changePlayer() {
		if (controlled != null) {
			int startInd = orgs.indexOf(controlled);
			for (int i = (startInd + 1) % orgs.size(); i != startInd; i = (i + 1) % orgs.size()) {
				if (orgs.get(i).getColor().equals(currTeam)) {
					controlled.control();
					controlled = (Organism) orgs.get(i);
					controlled.control();
				}
			}
		}
	}

	public void changeTeam() {
		if (controlled != null) {
			int startInd = orgs.indexOf(controlled);
			for (int i = (startInd + 1) % orgs.size(); i != startInd; i = (i + 1) % orgs.size()) {
				if (!orgs.get(i).getColor().equals(currTeam)) {
					controlled.control();
					controlled = (Organism) orgs.get(i);
					controlled.control();
					currTeam = controlled.getColor();
					break;
				}
			}
		}
	}

	public Organism getControl() {
		return controlled;
	}

	public void lock() {
		locked = !locked;
	}

	public void pause() {
		paused = !paused;
	}

	public boolean isLocked() {
		return locked;
	}

	public void turnUp() {
		if (controlled != null) {
			controlled.setTurnDir(-1);
		}
	}

	public void endUp() {
		if (controlled != null) {
			if (controlled.getTurnDir() == -1) {
				controlled.setTurnDir(0);
			}
		}
	}

	public void turnDown() {
		if (controlled != null) {
			controlled.setTurnDir(1);
		}
	}

	public void endDown() {
		if (controlled != null) {
			if (controlled.getTurnDir() == 1) {
				controlled.setTurnDir(0);
			}
		}
	}

	public int getSpd() {
		return spd;
	}

	public void look() {
		looking = !looking;
		controlled.setAlert(true);
	}

	public void birth() {
		if (controlled != null && controlled.getSize() >= 1000) {
			Organism babe = controlled.birth(true);
			orgs.add(babe);

			// entities.add(babe.getMouth());
		}
	}

	public SimScreen getScreen() {
		return screen;
	}

	public InfoPane getPane() {
		return info.getPane();
	}

	public Tile getTile(Coordinate c) {
		if (c == null) {
			return null;
		}
		return tiles[c.getX()][c.getY()];
	}

	public void defZoom() {
		int min = Math.min(MAX_X, MAX_Y);
		zoomIn(min / 2, min / 2, 1);
	}

	public void zoomIn(int newX, int newY, double change) {
		zoomX = newX;
		zoomY = newY;
		times = change;
	}

	public ArrayList<Organism> getOrgs() {
		return orgs;
	}

	public void setSpd(int s) {
		spd = s;
	}

	public void zoomIn(double change) {
		times *= change;
	}

	public int getZoomX() {
		return zoomX;
	}

	public int getZoomY() {
		return zoomY;
	}

	public double getTimes() {
		return times;
	}

}
