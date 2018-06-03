package main;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gui.GameFrame;

public class Main {

	public static void main(String[] args) {
		try {
			new GameFrame(ImageIO.read(new File(args[0])));
		} catch (IOException e) {
			System.out.println("\nBildpfad wurde nicht gefunden!\n");
			e.printStackTrace();
		}
		
		
	}

}
