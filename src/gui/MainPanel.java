package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ImagePreviewListener;

public class MainPanel extends JPanel{
	private GameFrame gf;
	private JLabel imagePreview;
	private JTextField tileCount;
	private JButton startGame;
	
	public MainPanel(GameFrame gf) {
		this.gf = gf;
		setLayout(new BorderLayout());
		
		imagePreview = new JLabel("Click", JLabel.CENTER);
		imagePreview.addMouseListener(new ImagePreviewListener(imagePreview, gf));
		imagePreview.setPreferredSize(new Dimension(350, 350));
		add(imagePreview);
		
		JPanel lowerPanel = new JPanel(new GridLayout(2, 1));
		JPanel tileCountPanel = new JPanel(new GridLayout(1, 2));
		tileCount = new JTextField("3");
		tileCountPanel.add(new JLabel("Tilecount: "));
		tileCountPanel.add(tileCount);
		lowerPanel.add(tileCountPanel);
		lowerPanel.add(startGame = new JButton("Start Game"));
		add(lowerPanel, BorderLayout.SOUTH);
		
		startGame = new JButton("Start Game");
		startGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		
	}
	
}
