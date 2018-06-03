package controller;

import java.awt.FileDialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gui.GameFrame;

public class ImagePreviewListener implements MouseListener {
	private JLabel preview;
	private GameFrame gf;
	
	
	
	public ImagePreviewListener(JLabel preview, GameFrame gf) {
		this.preview = preview;
		this.gf = gf;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		FileDialog fd = new FileDialog(gf.getFrame(), "Load Picture" ,FileDialog.LOAD);
		fd.setDirectory("C:\\");
		fd.setVisible(true);
		String filename = fd.getFile();
		String filePath = fd.getDirectory();
		System.out.println(filePath + filename);
		preview.setIcon(new ImageIcon(filePath + filename));
		preview.setText(null);
		preview.setPreferredSize(null);
		gf.getFrame().pack();
		gf.getFrame().setLocationRelativeTo(null);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
