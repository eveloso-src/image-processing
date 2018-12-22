package fxml.controller;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.imageio.ImageIO;

public class ImageManager {

	public static void main(String[] args) throws IOException {

		File bwfile = new File("imgs/img_20181218_11_34_47_407.jpg");
		byte[] fileContent = Files.readAllBytes(bwfile.toPath());

		BufferedImage img = ImageIO.read(new ByteArrayInputStream(fileContent));

		OutputStream out = null;

		try {

			final byte[] ib  = ((DataBufferByte)img.getRaster().getDataBuffer()).getData() ;
			
//			for (int i = 0; i< ib.length; i++ ) {
				for (int i = 0; i< 640; i++ ) {
				System.out.println(ib[i]);
				
				
				
				int p   = ib[i] ;
                ib[i]   = (byte)((p & 0x00000000) >> 16) ;
                
                
			}

			BufferedImage img1 = ImageIO.read(new ByteArrayInputStream(ib));

//			ImageIO.write(img1, "jpg", new File("imgs/bytes_"  + ".jpg"));

			out = new BufferedOutputStream(new FileOutputStream("imgs/bytes_2.jpg"));
			out.write(fileContent);
		} finally {
			if (out != null)
				out.close();
		}

	}

}
