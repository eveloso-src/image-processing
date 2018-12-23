package fxml.controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;
import java.io.File;

public class MakeImage {
	public static void main(String[] args) {
		BufferedImage im = new BufferedImage(400, 400, BufferedImage.TYPE_BYTE_INDEXED);
		

		int r = 5;
		int g = 5;
		int b = 5;
		int col = (r << 16) | (g << 8) | b;


		for (int i = 0; i < 300; i++) {

			r = r+i & 255;
			g = g+i & 255;
			b = b+i & 255;
			col = (r << 16) | (g << 8) | b;
			im.setRGB(5+i, 10, col);
		}

		try {
			ImageIO.write(im, "bmp", new File("imgs/image.bmp"));
		} catch (IOException e) {
			System.out.println("Some exception occured " + e);
		}
	}
}