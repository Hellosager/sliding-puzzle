package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class TilePanel extends JPanel {
	private int tileWidht, tileHeight;
	private BufferedImage tiles[][];

	public TilePanel(final BufferedImage srcPic, int widthTiles, int heightTiles) {
		tiles = new BufferedImage[widthTiles][heightTiles];
		int srcPicWidth = srcPic.getWidth();
		int srcPicHeight = srcPic.getHeight();
		tileWidht = roundDown(srcPicWidth, widthTiles);
		tileHeight = roundDown(srcPicHeight, heightTiles);
		setPreferredSize(new Dimension(tileWidht * widthTiles, tileHeight * heightTiles));

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				tiles[i][j] = srcPic.getSubimage(i * tileWidht, j * tileHeight, tileWidht, tileHeight);
			}
		}
	}

	private int roundDown(int srcPicEdgeLength, int count) {
		if (srcPicEdgeLength % count == 0) {
			return srcPicEdgeLength / count;
		} else {
			return (srcPicEdgeLength - (srcPicEdgeLength % count)) / count;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				g.drawImage(tiles[i][j], i * tileWidht, j * tileHeight, null);
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
}
