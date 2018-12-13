package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

import controller.TileClickListener;
import controller.TilePushListener;
import utils.ImageScaler;

public class TilePanel extends JPanel {
	private static final String ESCAPE = "ESC";

	private GameFrame gameFrame;
	private int tileWidth, tileHeight;
	private Tile originalTiles[][];
	private Tile mixedTiles[][];
	private boolean showingOriginalPicture;
	private boolean solved;
	
	private Font escapeFont;
	private int baseXEmptyTile, baseYEmptyTile;
	private Color currentEndForeground;

	public TilePanel(GameFrame gameFrame, BufferedImage srcPic, int widthTiles, int heightTiles) {
		this.gameFrame = gameFrame;
		this.originalTiles = new Tile[widthTiles][heightTiles];
		BufferedImage fittingPic = scaleImageIfNecassary(srcPic);
		this.showingOriginalPicture = false;
		this.solved = false;

		int srcPicWidth = fittingPic.getWidth();
		int srcPicHeight = fittingPic.getHeight();
		tileWidth = roundDown(srcPicWidth, widthTiles);
		tileHeight = roundDown(srcPicHeight, heightTiles);
		setPreferredSize(new Dimension(tileWidth * widthTiles, tileHeight * heightTiles));
		escapeFont = new Font(Font.SANS_SERIF, Font.BOLD, tileHeight / 4 ); 
		baseXEmptyTile = (widthTiles-1)*tileWidth;
		baseYEmptyTile = 1*tileHeight;

				
		for (int i = 0; i < originalTiles.length; i++) {
			for (int j = 0; j < originalTiles[i].length; j++) {
				int x = i * tileWidth;
				int y = j * tileHeight;
				originalTiles[i][j] = new Tile(fittingPic.getSubimage(x, y, tileWidth, tileHeight), x, y,
						(i + 1) * (j + 1));
				originalTiles[i][j].setMoveState(Tile.NOT_MOVEABLE);
			}
		}
		originalTiles[widthTiles - 1][0] = null; // dynamic moveable sets around empty tile
		this.mixedTiles = mixTiles(originalTiles);
		TilePushListener tpl = new TilePushListener(this, tileWidth, tileHeight, mixedTiles);
		addMouseListener(new TileClickListener(tpl, this, mixedTiles, originalTiles));
		addMouseMotionListener(tpl);
	}

	private Tile[][] mixTiles(Tile[][] originalTiles) {
		Random r = new Random(); // für die richtung
		Tile[][] workingMixedTiles = new Tile[originalTiles.length][originalTiles[0].length];
		for (int x = 0; x < originalTiles.length; x++) { // Copy array
			for (int y = 0; y < originalTiles[x].length; y++) {
				Tile t = originalTiles[x][y];
				workingMixedTiles[x][y] =  t == null ? null : new Tile(originalTiles[x][y]);
			}
		}
		int nullTileX = originalTiles.length - 1;
		int nullTileY = 0;
		int newNullTileX = nullTileX;
		int newNullTileY = nullTileY;
		int moves = 0;
		int previousMove = 88;
		int maxMoves = workingMixedTiles.length * 100;
		while (tilesSetEqually(originalTiles, workingMixedTiles) > 0 && moves < maxMoves) { // solange austauschen bis
																							// weniger als 2 gleiche
			nullTileX = newNullTileX;
			nullTileY = newNullTileY;
			while (newNullTileX == nullTileX && newNullTileY == nullTileY) { // solange kein move gefunden wurde
				int move = r.nextInt(4);
				switch (move) {
				case 0: // links
					if (previousMove != 1 && nullTileX - 1 >= 0) {
						newNullTileX = nullTileX - 1; // neue Koordinate von nullTile
					}
					break;
				case 1: // rechts
					if (previousMove != 0 && nullTileX + 1 <= workingMixedTiles.length - 1) {
						newNullTileX = nullTileX + 1; // neue Koordinate von nullTile
					}
					break;
				case 2: // oben
					if (previousMove != 3 && nullTileY - 1 >= 0) {
						newNullTileY = nullTileY - 1; // neue Koordinate von nullTile
					}
					break;
				case 3: // unten
					if (previousMove != 2 && nullTileY + 1 <= workingMixedTiles[nullTileX].length - 1)
						newNullTileY = nullTileY + 1; // neue Koordinate von nullTile
					break;
				}
				if (nullTileX != newNullTileX || nullTileY != newNullTileY) {
					moves++;
					previousMove = move;
					workingMixedTiles[nullTileX][nullTileY] = workingMixedTiles[newNullTileX][newNullTileY];
					workingMixedTiles[newNullTileX][newNullTileY] = null;
					workingMixedTiles[nullTileX][nullTileY].setX(nullTileX * tileWidth);
					workingMixedTiles[nullTileX][nullTileY].setY(nullTileY * tileHeight);
				}
			}
		}

		if (newNullTileX - 1 >= 0)
			workingMixedTiles[newNullTileX - 1][newNullTileY].setMoveState(Tile.X_MOVEABLE);
		if (newNullTileX + 1 <= workingMixedTiles.length - 1)
			workingMixedTiles[newNullTileX + 1][newNullTileY].setMoveState(Tile.X_MOVEABLE);
		if (newNullTileY - 1 >= 0)
			workingMixedTiles[newNullTileX][newNullTileY - 1].setMoveState(Tile.Y_MOVEABLE);
		if (newNullTileY + 1 <= workingMixedTiles[newNullTileX].length - 1)
			workingMixedTiles[newNullTileX][newNullTileY + 1].setMoveState(Tile.Y_MOVEABLE);

		return workingMixedTiles;
	}

	private int tilesSetEqually(Tile[][] originalTiles, Tile[][] mixedTiles) {
		int equalTiles = 0;
		for (int x = 0; x < originalTiles.length; x++) {
			for (int y = 0; y < originalTiles[x].length; y++) {
				Tile originalTile = originalTiles[x][y];
				Tile mixedTile = mixedTiles[x][y];
				if (originalTile != null && mixedTile != null
						&& originalTiles[x][y].getID() == mixedTiles[x][y].getID())
					equalTiles++;
			}
		}
		return equalTiles;
	}

	private int roundDown(int srcPicEdgeLength, int count) {
		if (srcPicEdgeLength % count == 0) {
			return srcPicEdgeLength / count;
		} else {
			return (srcPicEdgeLength - (srcPicEdgeLength % count)) / count;
		}
	}

	private BufferedImage scaleImageIfNecassary(BufferedImage srcPic) {
		ImageScaler is = new ImageScaler();
		if (!is.isDimensionFittingInScreen(srcPic.getWidth(), srcPic.getHeight())) {
			Dimension fitScreen = is.getDimensionFitOnScreen(srcPic.getWidth(), srcPic.getHeight());
			return is.scaleImageToDimension(srcPic, fitScreen);
		} else {
			return srcPic;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!solved && !showingOriginalPicture) {
			renderTiles(g, mixedTiles);
			g.setColor(Color.RED);
			int i = 0;
			int j = 0;
			while ((i += tileWidth) < getWidth()) {
				g.drawLine(i, 0, i, getHeight());
			}
			while ((j += tileHeight) < getHeight()) {
				g.drawLine(0, j, getWidth(), j);
			}
		}else if(!solved && showingOriginalPicture){
			renderTiles(g, originalTiles);
		}else if(solved) {
			g.setFont(escapeFont);
			g.setColor(currentEndForeground);
			int stringWidht = g.getFontMetrics().stringWidth(ESCAPE);
			int stringHeight = g.getFontMetrics().getHeight();
			g.drawString(ESCAPE, ((mixedTiles.length-1) * tileWidth) + tileWidth/2 - stringWidht/2, tileHeight/2 + stringHeight/4);
			renderTiles(g, mixedTiles);
		}
	}

	private void renderTiles(Graphics g, Tile[][] tilesToPaint) {
		for(int i = 0; i < tilesToPaint.length; i++) {
			for(int j = 0; j < tilesToPaint[i].length; j++) {
				if(tilesToPaint[i][j] != null) {
					Tile tile = tilesToPaint[i][j];
					g.drawImage(tile.getImage(), tile.getX(), tile.getY(), null);
				}
			}
		}
	}

	public boolean isPointOnEsc(int x, int y) {
		return ((x >= baseXEmptyTile) && (x <= baseXEmptyTile + tileWidth)) && (((y >= 0) && (y <= baseYEmptyTile)));
	}
	
	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}
	
	public boolean isShowingOriginalPicture() {
		return showingOriginalPicture;
	}
	
	public void setShowingOriginalPicture(boolean b) {
		this.showingOriginalPicture = b;
	}
	
	public boolean isSolved() {
		return solved;
	}
	
	public void setSolved(boolean b) {
		this.solved = b;
	}
	
	public void setCurrentEndForeground(Color c) {
		this.currentEndForeground = c;
	}
	
	public void setCurrentEndBeckground(Color c) {
		this.currentEndForeground = c;
	}

	public int getBaseXEmptyTile() {
		return baseXEmptyTile;
	}

	public int getBaseYEmptyTile() {
		return baseYEmptyTile;
	}
	
	public GameFrame getGameFrame() {
		return gameFrame;
	}
}
