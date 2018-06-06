package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ImagePreviewListener;

public class MainPanel extends JPanel{
	private GameFrame gf;
	private JLabel imagePreview;
	private String currentPicturePath;
	private JTextField tileCountField;
	private JButton startGame;
	
	public MainPanel(GameFrame gf) {
		this.gf = gf;
		setLayout(new BorderLayout());
		
		imagePreview = new JLabel("Click", JLabel.CENTER);
		imagePreview.addMouseListener(new ImagePreviewListener(imagePreview, gf, this));
		imagePreview.setPreferredSize(new Dimension(350, 350));
		add(imagePreview);
		
		JPanel lowerPanel = new JPanel(new GridLayout(2, 1));
		JPanel tileCountPanel = new JPanel(new GridLayout(1, 2));
		tileCountField = new JTextField("3");
		tileCountPanel.add(new JLabel("Tilecount: "));
		tileCountPanel.add(tileCountField);
		lowerPanel.add(tileCountPanel);
		startGame = new JButton("Starg Game");
		lowerPanel.add(startGame);
		add(lowerPanel, BorderLayout.SOUTH);
		
		startGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tileCount = new Integer(tileCountField.getText());
				BufferedImage picture;
				try {
					picture = ImageIO.read(new File(currentPicturePath));
					TilePanel tp = new TilePanel(picture, tileCount, tileCount);
					JFrame frame = gf.getFrame();
					frame.getContentPane().removeAll();
					frame.add(tp);
					frame.revalidate();
					frame.repaint();
					frame.pack();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}
	
	public void setCurrentPicturePath(String path) {
		currentPicturePath = path;
	}
	
}
