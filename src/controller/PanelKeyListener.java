package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import gui.MainPanel;
import gui.TilePanel;

public class PanelKeyListener implements KeyListener {
	private TilePanel tp;
	private JFrame frame;
	
	public PanelKeyListener(TilePanel tp, JFrame frame) {
		this.tp = tp;
		this.frame = frame;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			tp.setShowingOriginalPicture(true);
			tp.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE && !tp.isSolved()) {
			tp.setShowingOriginalPicture(false);
			tp.repaint();
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.out.println("ESCAPE JUNGE");
			frame.getContentPane().removeAll();
			frame.add(new MainPanel(frame));
			frame.revalidate();
			frame.pack();
			frame.setLocationRelativeTo(null);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
