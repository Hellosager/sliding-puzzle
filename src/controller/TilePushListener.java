package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import gui.Tile;
import gui.TilePanel;

public class TilePushListener implements MouseMotionListener {
	private Tile currentMovingTile;
	private TilePanel tp;
	
	public TilePushListener(TilePanel tp) {
		this.tp = tp;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// Nur, wenn möglich ist soll gzogen werden können
		currentMovingTile.setX(e.getX());
		tp.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}


	public void setCurrentMovingTile(Tile tile) {
		this.currentMovingTile = tile;  
	}

}
