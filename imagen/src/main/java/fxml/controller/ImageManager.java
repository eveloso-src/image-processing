package fxml.controller;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.util.jh.JHGrayFilter;

public class ImageManager {

	public static void main(String[] args) throws IOException {

		File bwfile = new File("imgs/img_20181222_12_17_15_894.jpg");
		byte[] fileContent = Files.readAllBytes(bwfile.toPath());

		System.out.println("longitud: " + fileContent.length);

		BufferedImage bi = createImageFromBytes(fileContent);

		int datatype = bi.getRaster().getSampleModel().getDataType();


		System.out.println(datatype);

		int[] pixels = bi.getData().getPixels(0, 0, bi.getWidth(), bi.getHeight(), new int[0]);

		for (int y = 0, pos = 0; y < bi.getHeight(); y++)
			for (int x = 0; x < bi.getWidth(); x++)
				bi.getRaster().setSample(x, y, 0, fileContent[pos] & 0xFF);

		DataBuffer dataBuffer;
		Point origin;
		SampleModel sampleModel;
//		Raster raster = new Raster(sampleModel, dataBuffer, origin);
//		bi.setData(raster);
//		byte[] bytes = pixels.length * 4;
//		ByteBuffer.wrap(bytes).asIntBuffer().put(pixels);

		OutputStream out = null;

		out = new BufferedOutputStream(new FileOutputStream("imgs/bytes_2.jpg"));
		ImageIO.write(bi, "jpg", out);

	}

	private static BufferedImage createImageFromBytes(byte[] imageData) {
		ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
		try {
			return ImageIO.read(bais);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static final JHGrayFilter GRAY = new JHGrayFilter();

	public BufferedImage transform(BufferedImage image) {
		return GRAY.filter(image, null);
	}

}
