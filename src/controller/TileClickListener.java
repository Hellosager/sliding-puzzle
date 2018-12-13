package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gui.GameFrame;
import gui.Tile;
import gui.TilePanel;

public class TileClickListener implements MouseListener {
	private GameFrame gameFrame;
	private TilePushListener tpl;
	private TilePanel tp;
	private int tileWidth, tileHeight;
	private Tile[][] mixedTiles;
	private Tile[][] originalTiles;
	private int oldXIndex, oldYIndex;

	public TileClickListener(TilePushListener tpl, TilePanel tp, Tile[][] mixedTiles, Tile[][] originalTiles) {
		this.tpl = tpl;
		this.tp = tp;
		this.gameFrame = tp.getGameFrame();
		this.tileWidth = tp.getTileWidth();
		this.tileHeight = tp.getTileHeight();
		this.mixedTiles = mixedTiles;
		this.originalTiles = originalTiles;
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
		if(!tp.isSolved() && !tp.isShowingOriginalPicture()) {
			int x = e.getX() / tileWidth;
			int y = e.getY() / tileHeight;
			tpl.setCurrentMovingTile(mixedTiles[x][y]);
			oldXIndex = x;
			oldYIndex = y;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!tp.isSolved() && !tp.isShowingOriginalPicture()) {
			Tile currentMovingTile = tpl.getCurrentMovingTile();
			if (currentMovingTile != null) {
				int x = (currentMovingTile.getX() + tileWidth / 2) / tileWidth;
				int y = (currentMovingTile.getY() + tileHeight / 2) / tileHeight;
				currentMovingTile.setX(x * tileWidth);
				currentMovingTile.setY(y * tileHeight);
				if (currentMovingTile != mixedTiles[x][y]) {
					mixedTiles[x][y] = currentMovingTile;
					mixedTiles[oldXIndex][oldYIndex] = null;
					updateMoveablesFromTile(oldXIndex, oldYIndex, x, y); // altes alle not null moveable, neues alle not
					// null not moveable
					if (finished()) {
						tp.setSolved(true);
						tp.repaint();
					}
				}
				oldXIndex = -1;
				oldYIndex = -1;
				tpl.setCurrentMovingTile(null);
				tp.repaint();
			}
		}else if(tp.isSolved() && tp.isPointOnEsc(e.getX(), e.getY())) {
			gameFrame.backToMenu();
		}
	}

	private boolean finished() {
		for (int x = 0; x < mixedTiles.length; x++) {
			for (int y = 0; y < mixedTiles[x].length; y++) {
				Tile mixedTile = mixedTiles[x][y];
				Tile originalTile = originalTiles[x][y];
				if (mixedTile != null && originalTile != null) {
					if (mixedTile.getID() != originalTile.getID())
						return false;
				} else if (mixedTile == null ^ originalTile == null) { // if only one of them is null FIRST USAGE OF XOR
																		// :D
					return false;
				}
			}
		}
		return true;
	}

	private void updateMoveablesFromTile(int oldXIndex, int oldYIndex, int newXIndex, int newYIndex) {
		if ((oldXIndex - 1 >= 0) && (mixedTiles[oldXIndex - 1][oldYIndex] != null)) { // links daneben
			mixedTiles[oldXIndex - 1][oldYIndex].setMoveState(Tile.X_MOVEABLE);
		}
		if ((oldXIndex + 1 <= mixedTiles.length - 1) && (mixedTiles[oldXIndex + 1][oldYIndex] != null)) { // rechts
																											// daneben
			mixedTiles[oldXIndex + 1][oldYIndex].setMoveState(Tile.X_MOVEABLE);
		}
		if ((oldYIndex - 1 >= 0) && (mixedTiles[oldXIndex][oldYIndex - 1] != null)) { // drüber
			mixedTiles[oldXIndex][oldYIndex - 1].setMoveState(Tile.Y_MOVEABLE);
		}
		if ((oldYIndex + 1 <= mixedTiles[0].length - 1) && (mixedTiles[oldXIndex][oldYIndex + 1] != null)) { // drunter
			mixedTiles[oldXIndex][oldYIndex + 1].setMoveState(Tile.Y_MOVEABLE);
		}

		if ((newXIndex - 1 >= 0) && (mixedTiles[newXIndex - 1][newYIndex] != null)) { // links daneben
			mixedTiles[newXIndex - 1][newYIndex].setMoveState(Tile.NOT_MOVEABLE);
		}
		if ((newXIndex + 1 <= mixedTiles.length - 1) && (mixedTiles[newXIndex + 1][newYIndex] != null)) { // rechts
																											// daneben
			mixedTiles[newXIndex + 1][newYIndex].setMoveState(Tile.NOT_MOVEABLE);
		}
		if ((newYIndex - 1 >= 0) && (mixedTiles[newXIndex][newYIndex - 1] != null)) { // drüber
			mixedTiles[newXIndex][newYIndex - 1].setMoveState(Tile.NOT_MOVEABLE);
		}
		if ((newYIndex + 1 <= mixedTiles[0].length - 1) && (mixedTiles[newXIndex][newYIndex + 1] != null)) { // drunter
			mixedTiles[newXIndex][newYIndex + 1].setMoveState(Tile.NOT_MOVEABLE);
		}
	}

}
