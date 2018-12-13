package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import gui.MainPanel;
import gui.TilePanel;

public class PanelKeyListener implements KeyListener {
	private TilePanel tp;
	
	public PanelKeyListener(TilePanel tp) {
		this.tp = tp;
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
			tp.getGameFrame().backToMenu();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
