package controller;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gui.MainPanel;

public class ImagePreviewListener extends MouseAdapter {
	private JLabel preview;
	private JFrame frame;
	private MainPanel mp;
	private double screenWidth, screenHeight;
	
	public ImagePreviewListener(JLabel preview, JFrame frame, MainPanel mp) {
		this.preview = preview;
		this.frame = frame;
		this.mp = mp;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.getWidth();
		screenHeight = screenSize.getHeight();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		FileDialog fd = new FileDialog(frame, "Load Picture" ,FileDialog.LOAD);
		fd.setDirectory("C:\\");
		fd.setVisible(true);
		if(fd.getFile() != null) {
			String file = fd.getDirectory() + fd.getFile();
			mp.setCurrentPicturePath(file);
			ImageIcon icon = new ImageIcon(file);
			int iconHeight = icon.getIconHeight();
			int iconWidth = icon.getIconWidth();
			if(iconHeight > screenHeight || iconWidth > screenWidth) {
				while(iconHeight > screenHeight || iconWidth > screenWidth) {
					iconHeight = iconHeight/2;
					iconWidth = iconWidth/2;
				}
				Image image = icon.getImage();
				icon = new ImageIcon(image.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH));
			}
			preview.setIcon(icon);
			preview.setText(null);
			preview.setPreferredSize(null);
			frame.pack();
			frame.setLocationRelativeTo(null);
		}
	}
}
