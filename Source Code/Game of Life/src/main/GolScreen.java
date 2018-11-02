package main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Color;
import engines.*;
import entities.*;
import listeners.*;

public class GolScreen extends JPanel implements Constants {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GolEngine engine;
	Square[][] squaresTemp = new Square[WIDTH_SQUARES][HEIGHT_SQUARES];
	GolSquares squares;
	MyKeyListener keyListener;

	public GolScreen() {
		engine = new GolEngine(this);
		keyListener = new MyKeyListener(engine);
		JFrame frame = new JFrame("Game");
		frame.setDefaultCloseOperation(3);
		frame.setPreferredSize(new Dimension(WINDOW_WIDTH + 20, WINDOW_HEIGHT + 40));
		frame.pack();
		frame.addKeyListener(keyListener);
		this.addPanel(frame.getContentPane());
		frame.setVisible(true);
	}

	void addPanel(Container frame) {
		frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
		frame.add(this);
	}

	public void updateSquares(GolSquares squares) {
		 this.squares = new GolSquares(squares);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.setColor(Color.GREEN);
		 squares.draw(g);
	}

	public static void main(String[] args) {
		new GolScreen();
	}

}
