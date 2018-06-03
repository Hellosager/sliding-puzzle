package gui;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class GameFrame {
	private JFrame frame;
	private TilePanel tilePanel;
	private int widht;
	private int height;
	
	
	
	public GameFrame(BufferedImage originalPic) {
//		tilePanel = new TilePanel(originalPic, 9, 9); // TODO HARDCODED
		frame = new JFrame("Schieberätsel");
		frame.setLayout(new BorderLayout());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.add(tilePanel);
		frame.add(new MainPanel(this));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	
	private void startGame() {
		
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
}
