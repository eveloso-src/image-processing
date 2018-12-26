package fxml.controller;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

public class ToGray {

	public static void main(String[] args) throws IOException {
		ToGray tg = new ToGray();
		tg.convertGray(null, null);
	}

	public void convertGray(File inputPhoto, File outputPhoto) throws IOException {

		if (inputPhoto == null) {
			inputPhoto = new File("imgs/img_20181222_12_17_15_894.jpg");
		}
		BufferedImage img = ImageIO.read(inputPhoto);
		convertBufferedImage(img);
	}

	public BufferedImage convertBufferedImage(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		int[][] pixel = new int[width][height];
		Raster raster = img.getData();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixel[i][j] = raster.getSample(i, j, 0);
			}
		}

		BufferedImage theImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int value;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				value = pixel[i][j] << 16 | pixel[i][j] << 8 | pixel[i][j];

				if (j < 50) {
					value = j << 16 | j << 8 | j;

				}
				theImage.setRGB(i, j, value);
			}
		}

		// if (outputPhoto == null) {
		String fileOutput = "imgs/out" + System.currentTimeMillis() + ".jpg";
		File outputPhoto = new File(fileOutput);
		// }
		try {
			ImageIO.write(theImage, "jpg", outputPhoto);
		} catch (IOException e1) {

		}

		return theImage;
		// TODO Auto-generated method stub

	}
}
