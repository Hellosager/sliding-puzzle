package utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageScaler {

	public boolean isDimensionFittingInScreen(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();

		return (height < screenHeight) && (width < screenWidth);
	}
	
	public Dimension getDimensionFitOnScreen(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();

		if (height > screenHeight || width > screenWidth) {
			while (height > screenHeight || width > screenWidth) {
				height = height / 2;
				width = width / 2;
			}
		}
		return new Dimension(width, height);
	}

	
	public BufferedImage scaleImageToDimension(BufferedImage src, Dimension fitScreen) {
		double widthFactor = fitScreen.getWidth() / (double) src.getWidth() ;
		double heightFactor =  fitScreen.getHeight() / (double) src.getHeight();
		AffineTransform tx = new AffineTransform();
		tx.scale(widthFactor, heightFactor);
		AffineTransformOp op  = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(src, null);
	}
}
