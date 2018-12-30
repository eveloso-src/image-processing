package fxml.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
		byte[][] pixelBW = new byte[width][height];

		byte[] arrayBW = new byte[width * height];

		Raster raster = img.getData();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixel[i][j] = raster.getSample(i, j, 0);
				pixelBW[i][j] = new Double(raster.getSample(i, j, 0) * 0.21 + raster.getSample(i, j, 1) * 0.71
						+ raster.getSample(i, j, 2) * 0.07).byteValue();
//				arrayBW[i * width + j] = pixelBW[i][j];
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

		try {
			System.out.println("array: " + new ByteArrayInputStream(arrayBW));
//			Image image = ImageIO.read(new ByteArrayInputStream(arrayBW));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hh_mm_ss_SSS");

			// if (outputPhoto == null) {
			String fileOutput = "imgs/out" + sdf.format(new Date()) + ".jpg";
			File outputPhoto = new File(fileOutput);
			// }
			 ImageIO.write(theImage, "jpg", outputPhoto);
//			ImageIO.write(toBufferedImage(image), "jpg", outputPhoto);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return theImage;
		// TODO Auto-generated method stub

	}

	public BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}
}
