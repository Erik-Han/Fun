package entities;

import java.awt.Color;
import java.awt.Graphics;
import main.*;

public class Square implements Constants {
	protected int xCord;
	protected int yCord;

	private boolean status;
	private boolean isHead;
	private boolean cursor=false;

	public Square(int x, int y, boolean stat,boolean cursor) {
		xCord = x;
		yCord = y;
		status = stat;
		this.cursor=cursor;

	}

	public Square(int x, int y) {
		this(x, y, false,false);
	}

	public Square(Square square) {
		this(square.xCord, square.yCord, square.status,square.cursor);
	}

	public Square() {
		this(WIDTH_SQUARES / 2, HEIGHT_SQUARES / 2, false,false);
	}

	public void setCursor(boolean curs) {
		cursor = curs;
		
	}

	public boolean equals(Square square) {
		if (square.getXCord() == xCord && square.getYCord() == yCord) {
			return true;
		}
		return false;
	}

	public int getXCord() {
		return xCord;
	}

	public int getYCord() {
		return yCord;
	}

	public void setStat(boolean stat) {
		status = stat;
	}

	public boolean isOn() {
		return status;
	}

	public void setHead(boolean head) {
		isHead = head;
	}

	public boolean getHead() {
		return isHead;
	};

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(SQUARE_SIZE * xCord, SQUARE_SIZE * yCord, SQUARE_SIZE, SQUARE_SIZE);
		if (status||cursor) {
			
			if (cursor) {
				
				//System.out.println("XD");
				g.setColor(Color.GREEN);
			}
			g.fillRect(SQUARE_SIZE * xCord, SQUARE_SIZE * yCord, SQUARE_SIZE, SQUARE_SIZE);
		}
	}
}
