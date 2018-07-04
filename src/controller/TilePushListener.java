package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import gui.Tile;
import gui.TilePanel;

public class TilePushListener implements MouseMotionListener {
	private Tile currentMovingTile;
	private TilePanel tp;
	private int tileWidth, tileHeight;
	private Tile[][] tiles;
	private int maxWidthIndex, maxHeightIndex;
	private int baseXEmptyTile, baseYEmptyTile;
	private boolean mouseCampingEsc;

	public TilePushListener(TilePanel tp, int tileWidth, int tileHeight, Tile[][] tiles) {
		this.tp = tp;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tiles = tiles;
		this.maxWidthIndex = tiles.length;
		this.maxHeightIndex = tiles[0].length;
		mouseCampingEsc = false;
		baseXEmptyTile = (maxWidthIndex-1)*tileWidth;
		baseYEmptyTile = 1*tileHeight;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Nur, wenn möglich ist soll gzogen werden können
		if(!tp.isSolved() && !tp.isShowingOriginalPicture()) {
			if (currentMovingTile != null) {
				if (currentMovingTile.getMoveState() == Tile.X_MOVEABLE) {
					int mouseX = e.getX();
					int staticTileYIndex = currentMovingTile.getY() / tileHeight;
					int offsetX = mouseX > currentMovingTile.getX() ? tileWidth : -1; // TODO das hier ist noch komisch
					int xCheckTile = (mouseX + offsetX) / tileWidth;
					if ((xCheckTile < maxWidthIndex) && (mouseX >= 0) && (tiles[xCheckTile][staticTileYIndex] == null
							|| tiles[xCheckTile][staticTileYIndex] == currentMovingTile)) {
						currentMovingTile.setX(e.getX());
						tp.repaint();
					}
				}
				if (currentMovingTile.getMoveState() == Tile.Y_MOVEABLE) {
					int mouseY = e.getY();
					int staticTileXIndex = currentMovingTile.getX() / tileWidth;
					int offsetY = mouseY < currentMovingTile.getY() ? -1 : tileHeight; // TODO das hier ist noch komisch
					int yCheckTile = (mouseY + offsetY) / tileHeight;
					if ((yCheckTile < maxHeightIndex) && (mouseY >= 0) && (tiles[staticTileXIndex][yCheckTile] == null
							|| tiles[staticTileXIndex][yCheckTile] == currentMovingTile)) {
						currentMovingTile.setY(e.getY());
						tp.repaint();
					}
				}
				tp.repaint();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(tp.isSolved()) {
			int x = e.getX();
			int y = e.getY();
			if(((x >= baseXEmptyTile) && (x <= baseXEmptyTile + tileWidth)) && (((y >= 0) && (y <= baseYEmptyTile)))) {	// hover on ESC tile
				if(!mouseCampingEsc) {
					tp.setCurrentEndForeground(Color.RED);
					mouseCampingEsc = true;
					tp.repaint();
				}
			}else{
				if(mouseCampingEsc) {
					tp.setCurrentEndForeground(Color.BLACK);
					mouseCampingEsc = false;
					tp.repaint();
				}
			}
		}
	}

	public void setCurrentMovingTile(Tile tile) {
		this.currentMovingTile = tile;
	}

	public Tile getCurrentMovingTile() {
		return currentMovingTile;
	}

}
