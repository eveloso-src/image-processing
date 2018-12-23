package fxml.controller;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ToGray {

    public static void main(String[] args) throws IOException {
        File file = new File("imgs/img_20181222_12_17_15_894.jpg");
        BufferedImage img = ImageIO.read(file);
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] pixel = new int[width][height];
        Raster raster = img.getData();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixel[i][j] = raster.getSample(i, j, 0);
            }
        }

        BufferedImage theImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        int value ;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                value = pixel[i][j] << 16 | pixel[i][j] << 8
                        | pixel[i][j];
                
                if (j < 150) {
                	value = j << 16 | j << 8
                            | j;
                    	
                }
                theImage.setRGB(i, j, value);
            }
        }

        File outputfile = new File("imgs/outputfile.jpg");
        try {
            ImageIO.write(theImage, "jpg", outputfile);
        } catch (IOException e1) {

        }
}
}