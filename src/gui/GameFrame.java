package gui;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class GameFrame {
	private JFrame frame;
	
	public GameFrame() {
		frame = new JFrame("Schieberätsel");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(new MainPanel(this));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public void backToMenu() {
		frame.getContentPane().removeAll();
		frame.add(new MainPanel(this));
		frame.revalidate();
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
}
