package main;

import javax.swing.JFrame;

import controller.PanelKeyListener;
import gui.TilePanel;

public class Game {
	private boolean running;
	
	public Game(TilePanel tp, JFrame frame) {
		this.running = true;
		frame.getContentPane().removeAll();
		tp.addKeyListener(new PanelKeyListener(tp, frame));
		frame.add(tp);
		tp.requestFocus();
		frame.revalidate();
		frame.repaint();
		frame.pack();
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
}
