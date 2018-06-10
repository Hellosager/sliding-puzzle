package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gui.Tile;
import gui.TilePanel;

public class TileClickListener implements MouseListener{
	private TilePushListener tpl;
	private TilePanel tp;
	private int tileWidth, tileHeight;
	private Tile[][] tiles;
	private int oldXIndex, oldYIndex;
	
	public TileClickListener(TilePushListener tpl, TilePanel tp, Tile[][] tiles) {
		this.tpl = tpl;
		this.tp = tp;
		this.tileWidth = tp.getTileWidth();
		this.tileHeight = tp.getTileHeight();
		this.tiles = tiles;
		oldXIndex = -1;
		oldYIndex = -1;
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
//		if(oldYIndex == -1 && oldYIndex == -1) {
			System.out.println("alte coords gespeichert");
			oldXIndex = x; 
			oldYIndex = y;
			System.out.println("oldX :" + oldXIndex + " oldy: " + oldYIndex);
//		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Tile currentMovingTile = tpl.getCurrentMovingTile();
		if(currentMovingTile != null) {
			int x = (currentMovingTile.getX() + tileWidth/2) / tileWidth;
			int y = (currentMovingTile.getY() + tileHeight/2) / tileHeight;
			currentMovingTile.setX(x*tileWidth);
			currentMovingTile.setY(y*tileHeight);
			System.out.println("tilevergleich: " + (currentMovingTile == tiles[x][y]) + " tiles["+x+"]["+y+"]");
			if(currentMovingTile != tiles[x][y]) {
				tiles[x][y] = currentMovingTile;
				tiles[oldXIndex][oldYIndex] = null;
				updateMoveablesFromTile(oldXIndex, oldYIndex, x, y); // altes alle not null moveable, neues alle not null not moveable
			}
			oldXIndex = -1;
			oldYIndex = -1;
			tpl.setCurrentMovingTile(null);	
			tp.repaint();
		}
	}

	private void updateMoveablesFromTile(int oldXIndex, int oldYIndex, int newXIndex, int newYIndex) {
		if((oldXIndex-1 >= 0) && (tiles[oldXIndex-1][oldYIndex] != null)) { // links daneben
			tiles[oldXIndex-1][oldYIndex].setMoveState(Tile.X_MOVEABLE);
		}
		if((oldXIndex+1 <= tiles.length-1) && (tiles[oldXIndex+1][oldYIndex] != null)) { // rechts daneben
			tiles[oldXIndex+1][oldYIndex].setMoveState(Tile.X_MOVEABLE);
		}
		if((oldYIndex-1 >= 0) && (tiles[oldXIndex][oldYIndex-1] != null)) { // drüber
			tiles[oldXIndex][oldYIndex-1].setMoveState(Tile.Y_MOVEABLE);
		}
		if((oldYIndex+1 <= tiles[0].length-1) && (tiles[oldXIndex][oldYIndex+1] != null)) { // drunter
			tiles[oldXIndex][oldYIndex+1].setMoveState(Tile.Y_MOVEABLE);
		}
		
		if((newXIndex-1 >= 0) && (tiles[newXIndex-1][newYIndex] != null)) { // links daneben
			tiles[newXIndex-1][newYIndex].setMoveState(Tile.NOT_MOVEABLE);
		}
		if((newXIndex+1 <= tiles.length-1) && (tiles[newXIndex+1][newYIndex] != null)) { // rechts daneben
			tiles[newXIndex+1][newYIndex].setMoveState(Tile.NOT_MOVEABLE);
		}
		if((newYIndex-1 >= 0) && (tiles[newXIndex][newYIndex-1] != null)) { // drüber
			tiles[newXIndex][newYIndex-1].setMoveState(Tile.NOT_MOVEABLE);
		}
		if((newYIndex+1 <= tiles[0].length-1) && (tiles[newXIndex][newYIndex+1] != null)) { // drunter
			tiles[newXIndex][newYIndex+1].setMoveState(Tile.NOT_MOVEABLE);
		}
	}
	
}
