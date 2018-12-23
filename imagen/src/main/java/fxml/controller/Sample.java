package fxml.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.BitSet;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.util.jh.JHGrayFilter;
public class Sample {
   public static void main(String args[]) throws Exception {
      BufferedImage bImage = ImageIO.read(new File("imgs/bytes_.jpg"));
      BufferedImage nImag = transform(bImage);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ImageIO.write(nImag, "jpg", bos );
      byte [] data = bos.toByteArray();
      for (int i =0 ; i < 20 ; i++ ) {
    	  System.out.println(data[i]);
      }
      
      BitSet bits = BitSet.valueOf(data);
      
      for(int bitIndex = 0; bitIndex < data.length/2; bitIndex++) {
    	  
    	  bits.set(bitIndex);
      }
      
      
      ByteArrayInputStream bis = new ByteArrayInputStream(bits.toByteArray());
      File out = new File("imgs/output.jpg");
      
      BufferedImage bImage2 = ImageIO.read(bis);
      ImageIO.write(bImage2, "jpg", out );
      System.out.println("image created");
   }

	private static final JHGrayFilter GRAY = new JHGrayFilter();

	public static BufferedImage transform(BufferedImage image) {
		return GRAY.filter(image, null);
	}
}