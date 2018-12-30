package fxml.controller;

import java.awt.*;
import java.awt.color.*;
import java.awt.image.*;
import javax.swing.*;

public class GrayExample {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				gui();
			}
		});
	}

	static void gui() {
		final int SIDE = 200;
		BufferedImage image1 = toImage(createCircleData(SIDE), SIDE, SIDE);
		BufferedImage image2 = toImage(lighten(getData(image1)), SIDE, SIDE);
		JPanel cp = new JPanel();
		cp.add(new JLabel(new ImageIcon(image1)));
		cp.add(new JLabel(new ImageIcon(image2)));
		JFrame f = new JFrame("Test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(cp);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	static BufferedImage toImage(byte[] d, int w, int h) {
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorModel cm = new ComponentColorModel(cs, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
		DataBuffer db = new DataBufferByte(d, d.length);
		WritableRaster raster = Raster.createInterleavedRaster(db, w, h, w, 1, new int[] { 0 }, null);
		return new BufferedImage(cm, raster, false, null);
	}

	// assume data is helf in byte[]
	static byte[] getData(BufferedImage image) {
		WritableRaster raster = image.getRaster();
		DataBufferByte db = (DataBufferByte) raster.getDataBuffer();
		return db.getData();
	}

	// example for demo
	static byte[] createCircleData(int side) {
		byte[] d = new byte[side * side];
		for (int r = 0; r < side; ++r) {
			double y = 2.0 * r / side - 1.0;
			for (int c = 0; c < side; ++c) {
				double x = 2.0 * c / side - 1.0;
				double z = Math.sqrt(1 - x * x - y * y);
				d[r * side + c] = (byte) (z * 256);
			}
		}
		return d;
	}

	// example for demo
	static byte[] lighten(byte[] d) {
		byte[] e = new byte[d.length];
		for (int i = 0; i < e.length; ++i)
			e[i] = (byte) ((0xff & d[i]) * 0.8);
		return e;
	}
}