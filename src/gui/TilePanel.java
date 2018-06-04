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

import utils.ImageScaler;

public class TilePanel extends JPanel {
	private int tileWidht, tileHeight;
	private BufferedImage tiles[][];

	public TilePanel(BufferedImage srcPic, int widthTiles, int heightTiles) {
		tiles = new BufferedImage[widthTiles][heightTiles];
		BufferedImage fittingPic = scaleImageIfNecassary(srcPic);


		int srcPicWidth = fittingPic.getWidth();
		int srcPicHeight = fittingPic.getHeight();
		tileWidht = roundDown(srcPicWidth, widthTiles);
		tileHeight = roundDown(srcPicHeight, heightTiles);
		setPreferredSize(new Dimension(tileWidht * widthTiles, tileHeight * heightTiles));

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				tiles[i][j] = fittingPic.getSubimage(i * tileWidht, j * tileHeight, tileWidht, tileHeight);
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

	private BufferedImage scaleImageIfNecassary(BufferedImage srcPic) {
		ImageScaler is = new ImageScaler();
		if(!is.isDimensionFittingInScreen(srcPic.getWidth(), srcPic.getHeight())) {
			Dimension fitScreen = is.getDimensionFitOnScreen(srcPic.getWidth(), srcPic.getHeight());
			return is.scaleImageToDimension(srcPic, fitScreen);
		}else {
			return srcPic;
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
