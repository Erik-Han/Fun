package engines;

import screens.*;
import toolbox.Tools;
import entities.*;
import java.util.*;

import javax.swing.JOptionPane;

import java.awt.*;
public class Engine implements Runnable, Tools {
	public static int MAX_X = 5000;
	public static int MAX_Y = 2500;
	public static int COLS = MAX_X / LENGTH, ROWS = MAX_Y / LENGTH;
	protected static Screen screen;
	protected static ArrayList<Entity> ents = new ArrayList<Entity>(1000);
	protected static int zoomX = MAX_X / 2, zoomY = MAX_Y / 2;
	protected static double times = .7;
	protected static boolean paused = false;
	protected static ArrayList<Coordinate> pts = new ArrayList<Coordinate>();
	protected static ArrayList<String> text = new ArrayList<String>();
	protected static ArrayList<Coordinate> lines = new ArrayList<Coordinate>();
	protected static Tile[][] tiles = new Tile[COLS][ROWS];
	protected static Player player;
	protected static int delay = 10;
	private static int ct = 0;
	protected static boolean stop = false;
	protected static boolean spawning = false;
	protected static ArrayList<Entity> obj = new ArrayList<Entity>();
	protected static int lvl = 1;
	protected static boolean[] in = new boolean[4], in2 = new boolean[4];
	protected static Stack<Integer> dirs = new Stack<Integer>(), dirs2 = new Stack<Integer>();

	public static void setLevel(int lvl) {
		ct = 0;
		ents.clear();
		obj.clear();
		pts.clear();
		text.clear();
		lines.clear();

		switch (lvl) {
		case 1:
			spawning = false;
			setDimension(5000, 1000);
			setPlayer();
			for (int c = 20; c < COLS; c++) {
				for (int r = 0; r < ROWS; r++) {
					if (Math.random() < .1) {
						addEntity(new Skeleton(tiles[c][r].getCenter()));
					}
				}
			}
			addObj(new Knight(tiles[COLS - 1][ROWS - 1].getCenter()));
			addObj(new Ram(tiles[COLS - 2][ROWS - 1].getCenter()));
			addObj(new Ram(tiles[COLS - 2][ROWS - 2].getCenter()));
			addObj(new Ram(tiles[COLS - 2][ROWS - 3].getCenter()));
			break;
		case 2:
			spawning = true;
			setDimension(5000, 2500);
			setPlayer(tiles[COLS - 1][0].getCenter());
			summonDrag();
			summonDrag();
			summonDrag();
			for (int c = 0; c < COLS; c++) {
				for (int r = 0; r < ROWS; r++) {
					if (((r + 1) % 5 == 0)) {
						if ((r + 1) % 10 == 0) {
							if (c <= COLS - 5) {
								tiles[c][r] = new Wall(c, r);
							}
						} else {
							if (c >= 5) {

								tiles[c][r] = new Wall(c, r);
							}
						}
					} else {
						if (Math.random() < .01) {
							addEntity(new KamiSpawner(tiles[c][r].getCenter()));
						} else if (Math.random() < .01) {
							addEntity(new Sniper(tiles[c][r].getCenter()));
						} else if (Math.random() < .005) {
							addObj(new Knight(tiles[c][r].getCenter()));
						} else if (Math.random() < .005) {
							addEntity(new Ram(tiles[c][r].getCenter()));
						}
					}
				}
			}
			for (int r = 0; r < 3; r++) {
				tiles[COLS - 5][r] = new Wall(COLS - 5, r);
			}
			// addObj(new Knight(tiles[COLS - 1][ROWS - 1].getCenter()));
			break;
		case 3:

			spawning = false;
			setDimension(3000, 3000);
			setPlayer(tiles[COLS / 2][ROWS / 2].getCenter());
			addObj(new Dragon(tiles[0][0].getCenter()));
			addObj(new Dragon(tiles[COLS - 1][0].getCenter()));
			addObj(new Dragon(tiles[0][ROWS - 1].getCenter()));
			addObj(new Dragon(tiles[COLS - 1][ROWS - 1].getCenter()));
			addObj(new Ram(tiles[COLS/4][ROWS / 4].getCenter()));
			addObj(new Ram(tiles[3*COLS/4][ROWS / 4].getCenter()));
			addObj(new Ram(tiles[COLS / 4][3*ROWS/4].getCenter()));
			addObj(new Ram(tiles[3*COLS / 4][3*ROWS/4].getCenter()));
			break;
		case 4:
			spawning = false;
			setDimension(2500, 2500);
			setPlayer();
			Entity necro = new Necromancer(tiles[COLS / 2][ROWS / 2].getCenter());
			Entity death = new Death(tiles[COLS / 2 - 2][ROWS / 2].getCenter());
			necro.setLink(death);
			addObj(necro);

			addEntity(death);
			// addEntity(new Boss(tiles[COLS / 2-1][ROWS / 2].getCenter(),2));
			break;
		default:
			win();
		}
	}
	/*public void song(int i) {
		String song="gears.mp3"
		switch (i) {
		case 1: 
		}
		Media hit = new Media(new File(bip).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
	}*/
	public void run() {
		while (!stop) {
			if (!paused) {
				if (obj.size() == 0) {
					setLevel(lvl++);
					continue;
				}
				if (spawning && ct % 100 == 0)
					for (int i = 0; i < 1; i++) {
						addEntity(new Skeleton(tiles[Tools.randInt(COLS)][Tools.randInt(ROWS)].getCenter()));
					}
				if (player.getHeal() != 0 && Engine.getCt() % player.getHeal() == 0) {
					player.takeDmg(-1);
				}
				if (player.isDead()) {
					setLevel(lvl - 1);
				}

				for (int i = 0; i < ents.size(); i++) {
					ents.get(i).setVis(false);
					if (player.canSee(ents.get(i))) {
						ents.get(i).setVis(true);
					}
				}
				player.setVis(true);
				// all move
				if (!dirs.isEmpty()) {
					player.move(dirs.peek());
					/*
					 * for (Tile[] arr : tiles) { for (Tile t : arr) { if
					 * (t.isVis()) { // t.draw(g, zoomX, zoomY, times);
					 * //t.setVis(false); } } } player.sees();
					 */
					zoomX = player.getCord().getX();
					zoomY = player.getCord().getY();
				}
				for (int i = 0; i < ents.size(); i++) {
					Entity curr = ents.get(i);
					if (curr instanceof Auto) {
						Auto auto = (Auto) curr;
						if (auto.canMove()) {
							auto.move();
						}
					}
				}
				// all shoot
				if (!dirs2.isEmpty()) {
					player.shoot(dirs2.peek());
				}
				for (int i = 0; i < ents.size(); i++) {
					Entity curr = ents.get(i);
					if (curr instanceof Shooter) {
						Shooter shoot = (Shooter) curr;
						if (shoot.canShoot()) {
							((Shooter) curr).shoot();
						}
					}
				}
				for (int i = 0; i < ents.size(); i++) {
					for (int a = 0; a < ents.size(); a++) {
						if (ents.get(i) instanceof Creature && ents.get(a) instanceof Projectile) {
							if (ents.get(i).getTeam() != ents.get(a).getTeam()
									&& ents.get(i).collisionCheck(ents.get(a))) {
								((Creature) ents.get(i))
										.takeDmg(//(int) (ents.get(a).getLength() / ((double) LENGTH) * 1)
												10);
								ents.get(a).kill();
							}
						}
					}
				}
				// after combat
				for (int i = ents.size() - 1; i >= 0; i--) {
					Entity curr = ents.get(i);
					if (curr.isDead()) {
						curr.die();
						removeEntity(i);
					}

				}
				if (delay > 0) {
					try {
						Thread.sleep(delay);
					} catch (Exception e) {
					}
				}
				Collections.sort(ents);
				screen.repaint();

				lines.clear();
				// pts.clear();
				// text.clear();

			}
			ct++;

		}
	}

	public static Entity getClose(Entity e) {
		Entity min = null;
		for (int i = 0; i < ents.size(); i++) {
			if ((ents.get(i) instanceof Creature && e.getTeam() != ents.get(i).getTeam() && ents.get(i).canSee(e))
					&& (min == null || e.getDist(ents.get(i)) < e.getDist(min))) {
				min = ents.get(i);
			}
		}
		return min;
	}

	public static void setPlayer() {
		setPlayer(new Coordinate(0, 0));
	}

	public void start() {
		Thread t = new Thread(this);
		t.start();
	}

	public void stop() {
		stop = true;
	}

	public static void addEntity(Entity ent) {
		ents.add(ent);

	}

	public static void removeEntity(int i) {
		Entity e = ents.remove(i);

		int ind = obj.indexOf(e);
		if (ind != -1) {
			obj.remove(ind);
		}
	}

	public static void addObj(Entity e) {
		obj.add(e);
		addEntity(e);
	}

	public static void summonDrag() {
		addEntity(new Dragon(player.getCord(), 1));
	}

	public static void setDimension(int maxX, int maxY) {
		MAX_X = maxX;
		MAX_Y = maxY;
		COLS = MAX_X / LENGTH;
		ROWS = MAX_Y / LENGTH;
		tiles = new Tile[COLS][ROWS];
		for (int c = 0; c < COLS; c++) {
			for (int r = 0; r < ROWS; r++) {
				tiles[c][r] = new Tile(c, r);
			}
		}
		screen.setThings(ents, tiles, lines, pts, text);
		Entity.setBoard(tiles);
	}

	public static void setPlayer(Coordinate c) {
		player = new Player(c);
		zoomX = player.getCord().getX();
		zoomY = player.getCord().getY();
		addEntity(player);
		// player.sees();
	}

	public static Player getPlayer() {
		return player;
	}

	public static void win() {
		screen.win();
		System.exit(0);
	}

	public static void killTrolls() {
		for (int i = 0; i < ents.size(); i++) {
			if (ents.get(i) instanceof Skeleton) {
				ents.get(i).kill();
			}
		}
	}

	public static void killSkeletons() {
		for (int i = 0; i < ents.size(); i++) {
			if (ents.get(i) instanceof Skeleton) {
				ents.get(i).kill();
			}
		}
	}

	public static void killOrbs() {
		for (int i = 0; i < ents.size(); i++) {
			if (ents.get(i) instanceof Satellite) {
				ents.get(i).kill();
			}
		}
	}

	public static Screen getScreen() {
		return screen;
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

	public ArrayList<Entity> getEnts() {
		return ents;
	}

	public static void setSpd(int d) {
		delay = d;
	}

	public static void zoomIn(double change) {
		times *= change;
	}

	public static int getZoomX() {
		return zoomX;
	}

	public static int getZoomY() {
		return zoomY;
	}

	public static double getTimes() {
		return times;
	}

	public static int getDelay() {
		return delay;
	}

	public static int getCt() {
		return ct;
	}

	public static void move(int i) {
		// dir = i;
		if (!in[i]) {
			dirs.add(i);
			in[i] = true;
		}
	}

	public static void relMove(int i) {
		dirs.remove(new Integer(i));
		in[i] = false;
	}

	public static void shoot(int i) {
		if (!in2[i]) {
			dirs2.add(i);
			in2[i] = true;
		}
	}

	public static void relShoot(int i) {
		dirs2.remove(new Integer(i));
		in2[i] = false;
	}

	public static Tile[][] getTiles() {
		return tiles;
	}

	public Engine() {
		screen = new Screen();
		setLevel(lvl++);
		start();
	}
}
