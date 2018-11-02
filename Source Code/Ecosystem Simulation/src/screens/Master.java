package screens;

import engines.*;
import java.awt.*;
import javax.swing.*;
import listeners.Keyboard;
import listeners.Mouse;
import java.util.*;

public class Master {
	private static Engine engine;
	private static ArrayList<Engine> sims = new ArrayList<Engine>();
	private static SimScreen screen;
	private static InfoPane pane;
	private static Keyboard board;
	private static Mouse mouse;
	private static JFrame frame;

	public static void main(String[] args) {
		boolean sim = false;
		if (!sim) {
			// setEngine(new Engine(true));
			setEngine(new Engine(true));
			engine.start();

		} else {
		}
	}

	// 200 810 230 750 930 490
	// 870 950 730 370
	public static void setEngine(Engine eng) {
		frame = new JFrame("MASTER");

		engine = eng;
		board = new Keyboard(engine);
		mouse = new Mouse(engine);
		screen = engine.getScreen();
		// screen.addKeyboard(board);
		// screen.addMouse(mouse);
		pane = engine.getPane();
		// pane.addKeyboard(board);
		setUpFrame(frame, java.awt.Toolkit.getDefaultToolkit().getScreenSize());
		addPanel(frame.getContentPane(), screen);
		addPanel(frame.getContentPane(), pane);
		addKeyboard(board);
		addMouse(mouse);
	}

	public static void setUpFrame(JFrame s, Dimension sz) {

		s.setPreferredSize(sz);
		s.setDefaultCloseOperation(3);
		s.setVisible(true);
		s.pack();

		// addPanel(s.getContentPane());
	}

	public static void addPanel(Container cont, JPanel pane) {
		cont.setLayout(new GridLayout(1, 2));
		cont.add(pane);
		// JPanel p = new JPanel();
		// cont.add(p);
		// p.setLayout(arg0);
		// cont.add(new JPanel());

	}

	public static void addKeyboard(Keyboard key) {
		frame.addKeyListener(key);
	}

	public static void addMouse(Mouse mouse) {
		frame.addMouseListener(mouse);
	}
}
