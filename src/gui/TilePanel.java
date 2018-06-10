package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controller.TileClickListener;
import controller.TilePushListener;
import utils.ImageScaler;

public class TilePanel extends JPanel {
	private int tileWidht, tileHeight;
	private Tile tiles[][];

	public TilePanel(BufferedImage srcPic, int widthTiles, int heightTiles) {
		tiles = new Tile[widthTiles][heightTiles];
		BufferedImage fittingPic = scaleImageIfNecassary(srcPic);

		int srcPicWidth = fittingPic.getWidth();
		int srcPicHeight = fittingPic.getHeight();
		tileWidht = roundDown(srcPicWidth, widthTiles);
		tileHeight = roundDown(srcPicHeight, heightTiles);
		setPreferredSize(new Dimension(tileWidht * widthTiles, tileHeight * heightTiles));

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				int x = i * tileWidht;
				int y = j * tileHeight;
				tiles[i][j] = new Tile(fittingPic.getSubimage(x, y, tileWidht, tileHeight), x, y);
				tiles[i][j].setMoveState(Tile.NOT_MOVEABLE);
			}
		}
		tiles[widthTiles - 1][0] = null;
		tiles[1][0].setMoveState(Tile.X_MOVEABLE);
		tiles[2][1].setMoveState(Tile.Y_MOVEABLE);


		TilePushListener tpl = new TilePushListener(this, tileWidht, tileHeight, tiles);
		addMouseListener(new TileClickListener(tpl, this, tiles));
		addMouseMotionListener(tpl);
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
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				if(tiles[i][j] != null) {
					g.drawImage(tiles[i][j].getImage(), tiles[i][j].getX(), tiles[i][j].getY(), null);
				}
			}
		}
		g.setColor(Color.RED);
		int i = 0;
		int j = 0;
		while ((i += tileWidht) < getWidth()) {
			g.drawLine(i, 0, i, getHeight());
		}
		while ((j += tileHeight) < getHeight()) {
			g.drawLine(0, j, getWidth(), j);
		}
	}
	
	public int getTileWidth() {
		return tileWidht;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
}
