package gui;

import java.awt.image.BufferedImage;

public class Tile {
	private BufferedImage tilePic;
	private int x, y;
	
	public Tile(BufferedImage tilePic, int x, int y) {
		this.tilePic = tilePic;
		this.x = x;
		this.y = y;
	}
	
	public BufferedImage getImage() {
		return tilePic;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
