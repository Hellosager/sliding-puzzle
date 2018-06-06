package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gui.Tile;

public class TileClickListener implements MouseListener{
	private TilePushListener tpl;
	private int tileWidth, tileHeight;
	private Tile[][] tiles;
	
	public TileClickListener(TilePushListener tpl, int tileWidth, int tileHeight, Tile[][] tiles) {
		this.tpl = tpl;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tiles = tiles;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX()/tileWidth;
		int y = e.getY()/tileHeight;
		tpl.setCurrentMovingTile(tiles[x][y]);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		tpl.setCurrentMovingTile(null);		
	}

}
