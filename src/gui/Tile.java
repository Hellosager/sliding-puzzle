package gui;

import java.awt.image.BufferedImage;

public class Tile {
	public static final int NOT_MOVEABLE = 0;
	public static final int X_MOVEABLE = 1;
	public static final int Y_MOVEABLE = 2;
	
	private BufferedImage tilePic;
	private int x, y;
	private int moveState;
	private boolean moveable;
	private int id;
	
	public Tile(BufferedImage tilePic, int x, int y, int id) {
		this.tilePic = tilePic;
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public Tile(Tile t) {
		this.tilePic = t.tilePic;
		this.x = t.x;
		this.y = t.y;
		this.id = t.id;
	}
	
	public BufferedImage getImage() {
		return tilePic;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isMoveable() {
		return moveable;
	}
	
	public void setMoveState(int moveState) {
		this.moveState = moveState;
	}
	
	public int getMoveState() {
		return moveState;
	}
	
	public int getID() {
		return id;
	}
}
