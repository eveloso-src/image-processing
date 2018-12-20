package fxml.controller;

import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class HistoRGB {

	public static void main(String args[]) {
		try {
			BufferedImage img = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
			File f = new File("imgs/bytes_1.jpg");

			int r = 5;
			int g = 25;
			int b = 255;
			int col = (r << 16) | (g << 8) | b;

			for (int x = 0; x < 500; x++) {
				for (int y = 20; y < 300; y++) {
					img.setRGB(x, y, col);
				}
			}

			ImageIO.write(img, "jpg", f);

			BufferedImage in = ImageIO.read(new File("imgs/bytes_.jpg"));

			BufferedImage bi = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
			int raster;
			int rgb;
			for (int i = 0; i < 320; i++) {
				for (int j = 0; j < 240; j++) {
					rgb = in.getRGB(i, j);
					raster = in.getRaster().getSample(i, j, 2);
//					System.out.println("rgb " + i + " " + j + " " + rgb);
					System.out.println("raster " + raster);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}