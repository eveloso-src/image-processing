package fxml.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

public class Converter {
	public static void main(String[] args) throws Exception {
	    BufferedImage src = convertRGBAToIndexed(ImageIO.read(new File("C:\\Users\\emiliano.veloso\\datos\\clases\\image\\imagen\\imgs\\mypic_new.jpg")));
	    ImageIO.write(src, "gif", new File("C:\\Users\\emiliano.veloso\\datos\\clases\\image\\imagen\\imgs\\dest.gif"));
	}

	public static BufferedImage convertRGBAToIndexed(BufferedImage src) {
	    BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
	    Graphics g = dest.getGraphics();
	    g.setColor(new Color(231, 20, 189));

	    // fill with a hideous color and make it transparent
	    g.fillRect(0, 0, dest.getWidth(), dest.getHeight());
	    dest = makeTransparent(dest, 0, 0);

	    dest.createGraphics().drawImage(src, 0, 0, null);
	    return dest;
	}

	public static BufferedImage makeTransparent(BufferedImage image, int x, int y) {
	    ColorModel cm = image.getColorModel();
	    if (!(cm instanceof IndexColorModel))
	        return image; // sorry...
	    IndexColorModel icm = (IndexColorModel) cm;
	    WritableRaster raster = image.getRaster();
	    byte pixel = (byte)raster.getSample(x, y, 0); // pixel is offset in ICM's palette
	    int size = icm.getMapSize();
	    byte[] reds = new byte[size];
	    byte[] greens = new byte[size];
	    byte[] blues = new byte[size];
	    icm.getReds(reds);
	    icm.getGreens(greens);
	    icm.getBlues(blues);
	    IndexColorModel icm2 = new IndexColorModel(8, x*y, reds, 0, false);
//	    IndexColorModel icm2 = new IndexColorModel(1, size, reds, greens, blues, pixel);
	    return new BufferedImage(icm2, raster, image.isAlphaPremultiplied(), null);
	}

}
