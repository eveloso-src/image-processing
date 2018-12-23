package fxml.controller;

import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class HistoRGB {

	public static void main(String args[]) {
		try {
			BufferedImage img = new BufferedImage(500, 500, BufferedImage.TYPE_BYTE_INDEXED);
			File f = new File("imgs/bytes_.jpg");
			File f1 = new File("imgs/bytes1.jpg");

			BufferedImage in = ImageIO.read(f);
			

			InputStream inStream = new FileInputStream(f);

			ByteArrayInputStream bin = new ByteArrayInputStream(data);
			byte[] buff = new byte[800000];

			int bytesRead = 0;

			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			bytesRead = inStream.read(buff);
			System.out.println("bytes: " + bytesRead);
//			while (bytesRead != -1) {
			for (int i=0; i < bytesRead; i++) {
				bao.write(buff, 0, bytesRead);
				bytesRead = inStream.read(buff);
			}

			inStream.close();
			byte[] data = bao.toByteArray();

			ByteArrayInputStream bin = new ByteArrayInputStream(data);
			System.out.println(bin.available());

//			ByteArrayInputStream bis = new ByteArrayInputStream(bits.toByteArray());

//			BufferedInputStream bis = new BufferedInputStream(f);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			int r = 5;
			int g = 5;
			int b = 5;
			int col = (r << 16) | (g << 8) | b;

			for (int x = 0; x < 500; x++) {
				for (int y = 20; y < 300; y++) {
					img.setRGB(x, y, col);
				}
			}

			ImageIO.write(img, "jpg", f1);

//			BufferedImage in = ImageIO.read(f);

			BufferedImage bi = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
			int raster;
			int rgb;
			for (int i = 0; i < 320; i++) {
				for (int j = 0; j < 240; j++) {
					rgb = in.getRGB(i, j);
					raster = in.getRaster().getSample(i, j, 2);
//					System.out.println("raster " + raster);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}